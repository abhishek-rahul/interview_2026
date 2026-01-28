package lld.hotelbooking.policy.impl;

import lld.hotelbooking.domain.Booking;
import lld.hotelbooking.policy.CancellationPolicy;

import java.time.LocalDateTime;

public class SimpleCancellationPolicy implements CancellationPolicy {
    @Override
    public long[] evaluate(Booking booking, LocalDateTime cancelTime) {
        return new long[]{0, 0}; // logic later during cancel()
    }
}
