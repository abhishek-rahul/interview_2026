package lld.hotelbooking.policy;

import lld.hotelbooking.domain.Hotel;

import java.time.LocalDate;
import java.util.List;

public interface RoomAllocationPolicy {
    List<String> eligibleRoomTypes(Hotel hotel, LocalDate checkIn, LocalDate checkOut, int guests);

    boolean canReserve(Hotel hotel, String roomType,
                       LocalDate checkIn, LocalDate checkOut,
                       int guests, int roomsQty);
}