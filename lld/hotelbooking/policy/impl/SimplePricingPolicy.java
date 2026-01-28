package lld.hotelbooking.policy.impl;

import lld.hotelbooking.domain.RoomInventory;
import lld.hotelbooking.policy.PricingPolicy;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class SimplePricingPolicy implements PricingPolicy {
    @Override
    public long totalPrice(RoomInventory inv, LocalDate checkIn, LocalDate checkOut, int roomsQty) {
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        return inv.getPricePerNight() * nights * roomsQty;
    }
}