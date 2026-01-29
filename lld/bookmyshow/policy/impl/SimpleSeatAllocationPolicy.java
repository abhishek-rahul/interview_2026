package lld.bookmyshow.policy.impl;

import lld.bookmyshow.domain.Show;
import lld.bookmyshow.policy.SeatAllocationPolicy;
import lld.bookmyshow.domain.SeatState;
import lld.bookmyshow.domain.enums.SeatStatus;

import java.time.Instant;
import java.util.List;

public class SimpleSeatAllocationPolicy implements SeatAllocationPolicy {

    @Override
    public void validateSeatIds(Show show, List<String> seatIds) {
        if (seatIds == null || seatIds.isEmpty()) {
            throw new IllegalArgumentException("seatIds required");
        }

        // seats are in List<Seat>, so validate by scanning list (MVP)
        for (String seatId : seatIds) {
            boolean exists = false;
            for (var seat : show.getSeats()) {
                if (seat.getId().equals(seatId)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                throw new IllegalArgumentException("Invalid seatId: " + seatId);
            }
        }
    }

    @Override
    public void allOrNothingLock(Show show, String userId, List<String> seatIds, Instant expiry, Instant now) {
        // 1) check all are AVAILABLE
        for (String seatId : seatIds) {
            SeatState st = show.getSeatState(seatId);
            if (st == null) throw new IllegalArgumentException("Seat state missing: " + seatId);

            if (st.getStatus() != SeatStatus.AVAILABLE) {
                throw new IllegalStateException("Seat not available: " + seatId + " status=" + st.getStatus());
            }
        }

        // 2) lock all (all-or-nothing)
        for (String seatId : seatIds) {
            SeatState st = show.getSeatState(seatId);
            st.setStatus(SeatStatus.LOCKED);
            st.setLockOwnerUserId(userId);
            st.setLockExpiry(expiry);
            // bookingId stays null
        }
    }

    @Override
    public boolean canConfirm(Show show, String userId, List<String> seatIds, Instant now) {
        throw new UnsupportedOperationException("TODO"); // used later in confirmBooking
    }
}
