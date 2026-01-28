package lld.hotelbooking.policy.impl;

import lld.hotelbooking.domain.Booking;
import lld.hotelbooking.policy.CancellationPolicy;

import java.time.Duration;
import java.time.LocalDateTime;

public class SimpleCancellationPolicy implements CancellationPolicy {
    @Override
    public long[] evaluate(Booking booking, LocalDateTime cancelTime) {

        LocalDateTime checkInStart = booking.getCheckIn().atStartOfDay();
        long total = booking.getTotalPrice();

        long hours = Duration.between(cancelTime, checkInStart).toHours();

        if (hours >= 24) {
            // full refund
            return new long[]{total, 0};
        }

        // 50% cancellation fee
        long fee = total / 2;
        long refund = total - fee;
        return new long[]{refund, fee};
    }
}