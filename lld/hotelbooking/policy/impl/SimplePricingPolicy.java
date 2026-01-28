package lld.hotelbooking.policy.impl;

import lld.hotelbooking.domain.RoomInventory;
import lld.hotelbooking.policy.PricingPolicy;

import java.time.LocalDate;

public class SimplePricingPolicy implements PricingPolicy {
    @Override
    public long totalPrice(RoomInventory inv, LocalDate checkIn, LocalDate checkOut, int roomsQty) {
        return 0; // logic later during search()/book()
    }
}