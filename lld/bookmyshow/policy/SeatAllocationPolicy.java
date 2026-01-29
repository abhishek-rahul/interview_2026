package lld.bookmyshow.policy;

import lld.bookmyshow.domain.Show;

import java.time.Instant;
import java.util.List;

public interface SeatAllocationPolicy {
    void validateSeatIds(Show show, List<String> seatIds);

    void allOrNothingLock(Show show, String userId, List<String> seatIds, Instant expiry, Instant now);

    boolean canConfirm(Show show, String userId, List<String> seatIds, Instant now);
}