package lld.hotelbooking;

import lld.hotelbooking.orchestrator.HotelBookingService;
import lld.hotelbooking.policy.impl.SimpleCancellationPolicy;
import lld.hotelbooking.policy.impl.SimplePricingPolicy;
import lld.hotelbooking.policy.impl.SimpleRoomAllocationPolicy;
import lld.hotelbooking.util.SimpleIdGenerator;
import lld.hotelbooking.domain.*;

import java.time.LocalDate;
import java.util.List;

public class MainDemo {
    public static void main(String[] args) {
        HotelCatalog catalog = new HotelCatalog();

        Hotel h1 = new Hotel("H1", "Sea View Resort", "Goa");
        AvailabilityCalendar calDeluxe = new AvailabilityCalendar();
        calDeluxe.setQty(LocalDate.of(2026, 2, 10), 3);
        calDeluxe.setQty(LocalDate.of(2026, 2, 11), 3);
        calDeluxe.setQty(LocalDate.of(2026, 2, 12), 3);
        h1.addInventory(new RoomInventory("DELUXE", 2, 4000, calDeluxe));

        Hotel h2 = new Hotel("H2", "Budget Inn", "Goa");
        AvailabilityCalendar calStd = new AvailabilityCalendar();
        calStd.setQty(LocalDate.of(2026, 2, 10), 0); // sold out -> should not appear
        calStd.setQty(LocalDate.of(2026, 2, 11), 0);
        calStd.setQty(LocalDate.of(2026, 2, 12), 0);
        h2.addInventory(new RoomInventory("STANDARD", 2, 1800, calStd));

        catalog.addHotel(h1);
        catalog.addHotel(h2);

        HotelBookingService service = new HotelBookingService(
                catalog,
                new SimplePricingPolicy(),
                new SimpleCancellationPolicy(),
                new SimpleRoomAllocationPolicy(),
                new SimpleIdGenerator()
        );

        List<String> res = service.search("Goa",
                LocalDate.of(2026, 2, 10),
                LocalDate.of(2026, 2, 13),
                2);
        

        System.out.println("\n\n -- Start of Result -- \n\n");
        System.out.println("hotelId | hotelName | roomType | pricePerNight | total | minAvail \n");
        for (String s : res) System.out.println(s);
        System.out.println("\n\n -- End of Result -- \n\n");
    }
}
