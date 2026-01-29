package lld.bookmyshow.domain;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import lld.bookmyshow.domain.enums.BookingStatus;

public class Booking {
    private final String id;
    private final String showId;
    private final String userId;
    private final List<String> seatIds;

    private BookingStatus status;
    private final Instant createdAt;

    public Booking(String id, String showId, String userId, List<String> seatIds, Instant createdAt) {
        this.id = Objects.requireNonNull(id);
        this.showId = Objects.requireNonNull(showId);
        this.userId = Objects.requireNonNull(userId);
        this.seatIds = Objects.requireNonNull(seatIds);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.status = BookingStatus.CONFIRMED;
    }

    public String getId() { return id; }
    public String getShowId() { return showId; }
    public String getUserId() { return userId; }
    public List<String> getSeatIds() { return seatIds; }
    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
    public Instant getCreatedAt() { return createdAt; }

    // NO logic yet: cancel() guard
}