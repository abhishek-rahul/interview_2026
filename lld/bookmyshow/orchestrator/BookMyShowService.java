package lld.bookmyshow.orchestrator;

import lld.bookmyshow.domain.BmsCatalog;
import lld.bookmyshow.domain.LockToken;
import lld.bookmyshow.domain.SeatState;
import lld.bookmyshow.domain.Show;
import lld.bookmyshow.policy.SeatAllocationPolicy;
import lld.bookmyshow.policy.SeatLockPolicy;
import lld.bookmyshow.util.IdGenerator;

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
        throw new UnsupportedOperationException("TODO");
    }

    public boolean cancelBooking(String bookingId, String userId, Instant now) {
        throw new UnsupportedOperationException("TODO");
    }
}