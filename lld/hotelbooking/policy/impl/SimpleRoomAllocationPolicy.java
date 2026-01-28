package lld.hotelbooking.policy.impl;

import lld.hotelbooking.domain.Hotel;
import lld.hotelbooking.policy.RoomAllocationPolicy;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class SimpleRoomAllocationPolicy implements RoomAllocationPolicy {

    @Override
    public List<String> eligibleRoomTypes(Hotel hotel, LocalDate checkIn, LocalDate checkOut, int guests) {
        return Collections.emptyList(); // logic later during search()
    }

    @Override
    public boolean canReserve(Hotel hotel, String roomType, LocalDate checkIn, LocalDate checkOut, int guests, int roomsQty) {
        return true; // logic later during book()
    }
}