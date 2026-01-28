package lld.hotelbooking.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class HotelCatalog {
    private final Map<String , Hotel> hotelsById = new HashMap<>();
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
}
