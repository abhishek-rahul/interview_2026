package lld.bookmyshow.policy.impl;

import lld.bookmyshow.domain.Show;
import lld.bookmyshow.domain.SeatState;
import lld.bookmyshow.policy.SeatLockPolicy;
import lld.bookmyshow.domain.enums.SeatStatus;
    

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class SimpleSeatLockPolicy implements SeatLockPolicy {

    private static final long LOCK_TTL_MINUTES = 2;

    @Override
    public Instant lockExpiry(Instant now) {
        return now.plus(LOCK_TTL_MINUTES, ChronoUnit.MINUTES);
    }

    @Override
    public void cleanupExpired(Show show, Instant now) {
        for (SeatState state : show.getSeatStateBySeatId().values()) {
            if (state.getStatus() == SeatStatus.LOCKED
                    && state.getLockExpiry() != null
                    && !state.getLockExpiry().isAfter(now)) {

                // Expired lock -> make seat AVAILABLE
                state.setStatus(SeatStatus.AVAILABLE);
                state.setLockOwnerUserId(null);
                state.setLockExpiry(null);
                // bookingId should remain null here (LOCKED seats are not booked)
            }
        }
    }

    @Override
    public boolean isLockValid(SeatState state, String userId, Instant now) {
        throw new UnsupportedOperationException("TODO"); // used in confirm later
    }
}
