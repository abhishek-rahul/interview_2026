package lld.hotelbooking.policy.impl;

import lld.hotelbooking.domain.Hotel;
import lld.hotelbooking.domain.RoomInventory;
import lld.hotelbooking.policy.RoomAllocationPolicy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleRoomAllocationPolicy implements RoomAllocationPolicy {

    @Override
    public List<String> eligibleRoomTypes(Hotel hotel, LocalDate checkIn, LocalDate checkOut, int guests) {
        if (hotel == null)
            return Collections.emptyList();

        List<String> types = new ArrayList<>();

        for (var entry : hotel.getInventories().entrySet()) {
            String roomType = entry.getKey();
            RoomInventory inv = entry.getValue();

            boolean capacityOk = guests <= inv.getCapacity();
            if (!capacityOk)
                continue;

            int minAvail = inv.getCalendar().minAvailable(checkIn, checkOut); // call-driven dependency
            if (minAvail > 0)
                types.add(roomType);
        }

        types.sort(String::compareTo);
        return types;
    }

    @Override
    public boolean canReserve(Hotel hotel, String roomType,
            LocalDate checkIn, LocalDate checkOut,
            int guests, int roomsQty) {

        if (hotel == null)
            return false;

        RoomInventory inv = hotel.getInventory(roomType);
        if (inv == null)
            return false;

        if (guests > inv.getCapacity())
            return false;

        int minAvail = inv.getCalendar().minAvailable(checkIn, checkOut);
        return minAvail >= roomsQty;
    }
}