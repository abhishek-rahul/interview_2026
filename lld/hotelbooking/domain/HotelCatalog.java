package lld.hotelbooking.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class HotelCatalog {
    // Hotels
    private final Map<String, Hotel> hotelsById = new HashMap<>();

    // Bookings
    private final Map<String, Booking> bookingsById = new HashMap<>();

    public void addHotel(Hotel hotel) {
        hotelsById.put(hotel.getId(), hotel);
    }

    public Hotel getHotel(String hotelId) {
        Hotel h = hotelsById.get(hotelId);
        if (h == null) throw new NoSuchElementException("Hotel not found: " + hotelId);
        return h;
    }

    public List<Hotel> findHotelsByCity(String city) {
        List<Hotel> result = new ArrayList<>();
        for (Hotel h : hotelsById.values()) {
            if (h.getCity().equalsIgnoreCase(city)) {
                result.add(h);
            }
        }
        // deterministic order for tests/debug
        result.sort(Comparator.comparing(Hotel::getId));
        return result;
    }    

    // ✅ Step 7 support: store booking
    public void saveBooking(Booking booking) {
        bookingsById.put(booking.getId(), booking);
    }

    // support for getBooking()
    public Booking getBooking(String bookingId) {
        return bookingsById.get(bookingId);
    }
}
