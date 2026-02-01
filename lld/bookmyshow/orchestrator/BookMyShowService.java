package lld.bookmyshow.orchestrator;

import lld.bookmyshow.domain.BmsCatalog;
import lld.bookmyshow.domain.LockToken;
import lld.bookmyshow.domain.SeatState;
import lld.bookmyshow.domain.Show;
import lld.bookmyshow.domain.enums.SeatStatus;
import lld.bookmyshow.policy.SeatAllocationPolicy;
import lld.bookmyshow.policy.SeatLockPolicy;
import lld.bookmyshow.util.IdGenerator;
import lld.bookmyshow.domain.Booking;
import lld.bookmyshow.domain.enums.BookingStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookMyShowService {

    private final BmsCatalog catalog;
    private final SeatAllocationPolicy seatAllocationPolicy;
    private final SeatLockPolicy seatLockPolicy;
    private final IdGenerator idGen;

    public BookMyShowService(BmsCatalog catalog,
            SeatAllocationPolicy seatAllocationPolicy,
            SeatLockPolicy seatLockPolicy,
            IdGenerator idGen) {
        this.catalog = Objects.requireNonNull(catalog);
        this.seatAllocationPolicy = Objects.requireNonNull(seatAllocationPolicy);
        this.seatLockPolicy = Objects.requireNonNull(seatLockPolicy);
        this.idGen = Objects.requireNonNull(idGen);
    }

    public List<String> listShows(String city, String movieId, LocalDate date) {

        if (city == null || movieId == null || date == null) {
            throw new IllegalArgumentException("Invalid search params");
        }

        List<Show> shows = catalog.findShows(city, movieId, date);

        List<String> result = new ArrayList<>();

        for (Show show : shows) {
            String line = show.getId() + " | " +
                    show.getMovie().getTitle() + " | " +
                    show.getTheatreId() + " | " +
                    show.getScreenId() + " | " +
                    show.getStartTime();

            result.add(line);
        }
        return result;
    }

    public List<String> viewSeats(String showId, Instant now) {

        if (showId == null || showId.trim().isEmpty()) {
            throw new IllegalArgumentException("showId required");
        }
        if (now == null) {
            throw new IllegalArgumentException("now required");
        }

        Show show = catalog.findShowById(showId);
        if (show == null) {
            throw new IllegalArgumentException("Show not found: " + showId);
        }

        // Ensure user sees fresh state
        seatLockPolicy.cleanupExpired(show, now);

        List<String> out = new java.util.ArrayList<>();

        // Print in seat layout order (list), not map order
        for (var seat : show.getSeats()) {
            SeatState st = show.getSeatState(seat.getId());

            String owner = (st.getLockOwnerUserId() == null) ? "-" : st.getLockOwnerUserId();
            String expiry = (st.getLockExpiry() == null) ? "-" : st.getLockExpiry().toString();
            String bookingId = (st.getBookingId() == null) ? "-" : st.getBookingId();

            out.add(seat.getId() + " | " + st.getStatus() + " | " + owner + " | " + expiry + " | " + bookingId);
        }

        return out;
    }

    public String lockSeats(String showId, String userId, List<String> seatIds, Instant now) {

        if (showId == null || showId.trim().isEmpty())
            throw new IllegalArgumentException("showId required");
        if (userId == null || userId.trim().isEmpty())
            throw new IllegalArgumentException("userId required");
        if (now == null)
            throw new IllegalArgumentException("now required");

        Show show = catalog.findShowById(showId);
        if (show == null)
            throw new IllegalArgumentException("Show not found: " + showId);

        // Always cleanup first
        seatLockPolicy.cleanupExpired(show, now);

        // validate seats exist in this show
        seatAllocationPolicy.validateSeatIds(show, seatIds);

        // compute expiry via policy
        Instant expiry = seatLockPolicy.lockExpiry(now);

        // all-or-nothing lock
        seatAllocationPolicy.allOrNothingLock(show, userId, seatIds, expiry, now);

        // create token (copy list to avoid external mutation)
        String tokenId = idGen.newId();
        LockToken token = new LockToken(tokenId, showId, userId, new ArrayList<>(seatIds), expiry);

        catalog.addLockToken(token);

        return tokenId;
    }

    public String confirmBooking(String lockTokenId, String userId, Instant now) {

        if (lockTokenId == null || lockTokenId.trim().isEmpty())
            throw new IllegalArgumentException("lockTokenId required");
        if (userId == null || userId.trim().isEmpty())
            throw new IllegalArgumentException("userId required");
        if (now == null)
            throw new IllegalArgumentException("now required");

        LockToken token = catalog.findLockTokenById(lockTokenId);
        if (token == null)
            throw new IllegalArgumentException("LockToken not found: " + lockTokenId);

        if (!token.getUserId().equals(userId)) {
            throw new IllegalStateException("LockToken does not belong to user: " + userId);
        }

        Show show = catalog.findShowById(token.getShowId());
        if (show == null)
            throw new IllegalStateException("Show not found for token: " + token.getShowId());

        // cleanup before confirm
        seatLockPolicy.cleanupExpired(show, now);

        // validate all locks still valid
        if (!seatAllocationPolicy.canConfirm(show, userId, token.getSeatIds(), now)) {
            throw new IllegalStateException("Cannot confirm: lock expired or seat not locked by user");
        }

        // mark seats BOOKED
        String bookingId = idGen.newId();

        for (String seatId : token.getSeatIds()) {
            SeatState st = show.getSeatState(seatId);

            st.setStatus(SeatStatus.BOOKED);
            st.setBookingId(bookingId);

            // clear lock fields
            st.setLockOwnerUserId(null);
            st.setLockExpiry(null);
        }

        // create booking entity
        Booking booking = new Booking(
                bookingId,
                show.getId(),
                userId,
                new java.util.ArrayList<>(token.getSeatIds()),
                now);
        booking.setStatus(BookingStatus.CONFIRMED);

        catalog.addBooking(booking);

        // remove token (one-time use)
        catalog.removeLockToken(lockTokenId);

        return bookingId;
    }

    public boolean cancelBooking(String bookingId, String userId, Instant now) {

        if (bookingId == null || bookingId.trim().isEmpty())
            throw new IllegalArgumentException("bookingId required");
        if (userId == null || userId.trim().isEmpty())
            throw new IllegalArgumentException("userId required");
        if (now == null)
            throw new IllegalArgumentException("now required");

        Booking booking = catalog.findBookingById(bookingId);
        if (booking == null)
            throw new IllegalArgumentException("Booking not found: " + bookingId);

        if (!booking.getUserId().equals(userId)) {
            throw new IllegalStateException("Booking does not belong to user: " + userId);
        }

        // entity guard
        booking.cancel();

        Show show = catalog.findShowById(booking.getShowId());
        if (show == null)
            throw new IllegalStateException("Show not found for booking: " + booking.getShowId());

        // release seats: AVAILABLE, clear booking and lock fields
        for (String seatId : booking.getSeatIds()) {
            SeatState st = show.getSeatState(seatId);
            if (st == null) {
                throw new IllegalStateException("Seat state missing: " + seatId);
            }

            st.setStatus(SeatStatus.AVAILABLE);
            st.setBookingId(null);
            st.setLockOwnerUserId(null);
            st.setLockExpiry(null);
        }

        return true;
    }
}