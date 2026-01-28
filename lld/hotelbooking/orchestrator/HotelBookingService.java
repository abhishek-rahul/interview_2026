package lld.hotelbooking.orchestrator;

import lld.hotelbooking.domain.Booking;
import lld.hotelbooking.domain.CancellationReceipt;
import lld.hotelbooking.domain.Hotel;
import lld.hotelbooking.policy.CancellationPolicy;
import lld.hotelbooking.policy.PricingPolicy;
import lld.hotelbooking.policy.RoomAllocationPolicy;
import lld.hotelbooking.domain.HotelCatalog;
import lld.hotelbooking.domain.RoomInventory;
import lld.hotelbooking.util.IdGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HotelBookingService {

    private final HotelCatalog catalog;
    private final PricingPolicy pricingPolicy;
    private final CancellationPolicy cancellationPolicy;
    private final RoomAllocationPolicy roomAllocationPolicy;
    private final IdGenerator idGenerator;

    public HotelBookingService(HotelCatalog catalog,
            PricingPolicy pricingPolicy,
            CancellationPolicy cancellationPolicy,
            RoomAllocationPolicy roomAllocationPolicy,
            IdGenerator idGenerator) {
        this.catalog = Objects.requireNonNull(catalog);
        this.pricingPolicy = Objects.requireNonNull(pricingPolicy);
        this.cancellationPolicy = Objects.requireNonNull(cancellationPolicy);
        this.roomAllocationPolicy = Objects.requireNonNull(roomAllocationPolicy);
        this.idGenerator = Objects.requireNonNull(idGenerator);
    }

    // Returns printable options:
    // "hotelId|hotelName|roomType|pricePerNight|total|minAvail"
    public List<String> search(String city, LocalDate checkIn, LocalDate checkOut, int guests) {

        // (A) Validate
        validateSearchInputs(city, checkIn, checkOut, guests);

        // (B) Domain query
        List<Hotel> hotels = catalog.findHotelsByCity(city);

        // (C) Allocation + (D) Pricing + (E) minAvail + (F) output format
        List<String> result = new ArrayList<>();

        for (Hotel hotel : hotels) {
            List<String> roomTypes = roomAllocationPolicy.eligibleRoomTypes(hotel, checkIn, checkOut, guests); // <--
                                                                                                               // call-driven

            for (String roomType : roomTypes) {
                RoomInventory inv = hotel.getInventory(roomType);

                long total = pricingPolicy.totalPrice(inv, checkIn, checkOut, 1);

                int minAvail = inv.getCalendar().minAvailable(checkIn, checkOut);

                result.add(formatOptionLine(hotel, inv, total, minAvail));
            }
        }

        return result;
    }

    public String book(String hotelId, String roomType,
            LocalDate checkIn, LocalDate checkOut,
            int guests, int roomsQty) {

        // 1) validate request
        validateBookInputs(hotelId, roomType, checkIn, checkOut, guests, roomsQty);

        // 2) load hotel
        Hotel hotel = catalog.getHotel(hotelId);
        if (hotel == null)
            throw new IllegalArgumentException("Hotel not found: " + hotelId);

        // 3) check availability via policy
        boolean canReserve = roomAllocationPolicy.canReserve(hotel, roomType, checkIn, checkOut, guests, roomsQty);
        if (!canReserve)
            throw new IllegalStateException("ROOM_NOT_AVAILABLE");

        // 4) reserve/decrement availability (atomic in-memory step)
        RoomInventory inv = hotel.getInventory(roomType);
        if (inv == null)
            throw new IllegalArgumentException("RoomType not found: " + roomType);

        inv.getCalendar().decrement(checkIn, checkOut, roomsQty);

        // 5) compute totalPrice
        long total = pricingPolicy.totalPrice(inv, checkIn, checkOut, roomsQty);

        // 6) create Booking entity (status CONFIRMED by default)
        String bookingId = idGenerator.newId();
        Booking booking = new Booking(
                bookingId,
                hotelId,
                roomType,
                checkIn,
                checkOut,
                guests,
                roomsQty,
                total,
                java.time.LocalDateTime.now());

        // 7) store booking
        catalog.saveBooking(booking);

        // 8) return booking id
        return bookingId;
    }

    private void validateSearchInputs(String city, LocalDate checkIn, LocalDate checkOut, int guests) {
        if (city == null || city.trim().isEmpty())
            throw new IllegalArgumentException("city is required");
        if (checkIn == null || checkOut == null)
            throw new IllegalArgumentException("checkIn/checkOut required");
        if (!checkIn.isBefore(checkOut))
            throw new IllegalArgumentException("checkIn must be before checkOut");
        if (guests < 1)
            throw new IllegalArgumentException("guests must be >= 1");
    }

    private String formatOptionLine(Hotel hotel, RoomInventory inv, long total, int minAvail) {
        return hotel.getId() + " | " + hotel.getName() + " | " + inv.getRoomType() + " | " +
                inv.getPricePerNight() + " | " + total + " | " + minAvail;
    }

    // Other methods remain TODO for now (as per our plan)

    private void validateBookInputs(String hotelId, String roomType,
            LocalDate checkIn, LocalDate checkOut,
            int guests, int roomsQty) {
        if (hotelId == null || hotelId.trim().isEmpty())
            throw new IllegalArgumentException("hotelId required");
        if (roomType == null || roomType.trim().isEmpty())
            throw new IllegalArgumentException("roomType required");
        if (checkIn == null || checkOut == null)
            throw new IllegalArgumentException("checkIn/checkOut required");
        if (!checkIn.isBefore(checkOut))
            throw new IllegalArgumentException("checkIn must be before checkOut");
        if (guests < 1)
            throw new IllegalArgumentException("guests must be >= 1");
        if (roomsQty < 1)
            throw new IllegalArgumentException("roomsQty must be >= 1");
    }
}