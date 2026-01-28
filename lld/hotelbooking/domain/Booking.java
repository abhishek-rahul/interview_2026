package lld.hotelbooking.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Booking {
    private final String id;
    private final String hotelId;
    private final String roomType;

    private final LocalDate checkIn;
    private final LocalDate checkOut;

    private final int guests;
    private final int roomsQty;

    private final long totalPrice;
    private BookingStatus status;

    private final LocalDateTime createdAt;

    public Booking(String id, String hotelId, String roomType,
                   LocalDate checkIn, LocalDate checkOut,
                   int guests, int roomsQty,
                   long totalPrice, LocalDateTime createdAt) {
        this.id = Objects.requireNonNull(id);
        this.hotelId = Objects.requireNonNull(hotelId);
        this.roomType = Objects.requireNonNull(roomType);
        this.checkIn = Objects.requireNonNull(checkIn);
        this.checkOut = Objects.requireNonNull(checkOut);
        this.guests = guests;
        this.roomsQty = roomsQty;
        this.totalPrice = totalPrice;
        this.createdAt = Objects.requireNonNull(createdAt);
        this.status = BookingStatus.CONFIRMED;
    }

    public String getId() { return id; }
    public String getHotelId() { return hotelId; }
    public String getRoomType() { return roomType; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public int getGuests() { return guests; }
    public int getRoomsQty() { return roomsQty; }
    public long getTotalPrice() { return totalPrice; }
    public BookingStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // NOTE: No markCancelled() yet (we’ll add when cancel() flow is implemented)
    public void setStatus(BookingStatus status) {
        this.status = Objects.requireNonNull(status);
    }
}