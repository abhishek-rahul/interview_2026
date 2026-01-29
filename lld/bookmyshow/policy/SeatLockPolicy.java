package lld.bookmyshow.policy;

import lld.bookmyshow.domain.Show;
import lld.bookmyshow.domain.SeatState;

import java.time.Instant;

public interface SeatLockPolicy {
    Instant lockExpiry(Instant now);

    void cleanupExpired(Show show, Instant now);

    boolean isLockValid(SeatState state, String userId, Instant now);
}
