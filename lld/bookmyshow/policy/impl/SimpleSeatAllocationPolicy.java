package lld.bookmyshow.policy.impl;

import lld.bookmyshow.domain.Show;
import lld.bookmyshow.policy.SeatAllocationPolicy;

import java.time.Instant;
import java.util.List;

public class SimpleSeatAllocationPolicy implements SeatAllocationPolicy {

    @Override
    public void validateSeatIds(Show show, List<String> seatIds) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void allOrNothingLock(Show show, String userId, List<String> seatIds, Instant expiry, Instant now) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public boolean canConfirm(Show show, String userId, List<String> seatIds, Instant now) {
        throw new UnsupportedOperationException("TODO");
    }
}
