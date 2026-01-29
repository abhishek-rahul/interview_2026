package lld.bookmyshow.policy.impl;

import lld.bookmyshow.domain.Show;
import lld.bookmyshow.domain.SeatState;
import lld.bookmyshow.policy.SeatLockPolicy;

import java.time.Instant;

public class SimpleSeatLockPolicy implements SeatLockPolicy {

    @Override
    public Instant lockExpiry(Instant now) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void cleanupExpired(Show show, Instant now) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public boolean isLockValid(SeatState state, String userId, Instant now) {
        throw new UnsupportedOperationException("TODO");
    }
}
