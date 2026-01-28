package lld.hotelbooking.policy;

import lld.hotelbooking.domain.Booking;

import java.time.LocalDateTime;

public interface CancellationPolicy {
    long[] evaluate(Booking booking, LocalDateTime cancelTime); // [refund, fee]
}