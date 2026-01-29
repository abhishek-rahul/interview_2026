package lld.bookmyshow.domain;

import java.time.Instant;

import lld.bookmyshow.domain.enums.SeatStatus;

public class SeatState {
    private SeatStatus status;

    private String lockOwnerUserId; // null if not locked
    private Instant lockExpiry;     // null if not locked

    private String bookingId;       // null if not booked

    public SeatState() {
        this.status = SeatStatus.AVAILABLE;
    }

    public SeatStatus getStatus() { return status; }
    public void setStatus(SeatStatus status) { this.status = status; }

    public String getLockOwnerUserId() { return lockOwnerUserId; }
    public void setLockOwnerUserId(String lockOwnerUserId) { this.lockOwnerUserId = lockOwnerUserId; }

    public Instant getLockExpiry() { return lockExpiry; }
    public void setLockExpiry(Instant lockExpiry) { this.lockExpiry = lockExpiry; }

    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    // NO helper logic yet (expired/lock/unlock/book etc.)
}
