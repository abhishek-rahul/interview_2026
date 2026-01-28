package lld.hotelbooking.policy;

import lld.hotelbooking.domain.RoomInventory;

import java.time.LocalDate;

public interface PricingPolicy {
    long totalPrice(RoomInventory inv, LocalDate checkIn, LocalDate checkOut, int roomsQty);
}