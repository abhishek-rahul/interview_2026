package lld.hotelbooking.domain;

import java.util.Objects;

public class RoomInventory {
    private final String roomType;     // e.g. DELUXE
    private final int capacity;        // max guests per room
    private final long pricePerNight;  // simplest

    private final AvailabilityCalendar calendar;

    public RoomInventory(String roomType, int capacity, long pricePerNight, AvailabilityCalendar calendar) {
        this.roomType = Objects.requireNonNull(roomType);
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.calendar = Objects.requireNonNull(calendar);
    }

    public String getRoomType() { return roomType; }
    public int getCapacity() { return capacity; }
    public long getPricePerNight() { return pricePerNight; }
    public AvailabilityCalendar getCalendar() { return calendar; }
}