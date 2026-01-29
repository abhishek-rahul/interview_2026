package lld.bookmyshow.orchestrator;

import lld.bookmyshow.domain.BmsCatalog;
import lld.bookmyshow.policy.SeatAllocationPolicy;
import lld.bookmyshow.policy.SeatLockPolicy;
import lld.bookmyshow.util.IdGenerator;

import java.time.Instant;
import java.time.LocalDate;
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
        throw new UnsupportedOperationException("TODO");
    }

    public List<String> viewSeats(String showId, Instant now) {
        throw new UnsupportedOperationException("TODO");
    }

    public String lockSeats(String showId, String userId, List<String> seatIds, Instant now) {
        throw new UnsupportedOperationException("TODO");
    }

    public String confirmBooking(String lockTokenId, String userId, Instant now) {
        throw new UnsupportedOperationException("TODO");
    }

    public boolean cancelBooking(String bookingId, String userId, Instant now) {
        throw new UnsupportedOperationException("TODO");
    }
}