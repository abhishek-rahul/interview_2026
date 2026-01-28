package lld.hotelbooking;

import lld.hotelbooking.orchestrator.HotelBookingService;
import lld.hotelbooking.policy.impl.SimpleCancellationPolicy;
import lld.hotelbooking.policy.impl.SimplePricingPolicy;
import lld.hotelbooking.policy.impl.SimpleRoomAllocationPolicy;
import lld.hotelbooking.util.SimpleIdGenerator;
import lld.hotelbooking.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
                                new SimpleIdGenerator());

                List<String> res = service.search("Goa",
                                LocalDate.of(2026, 2, 10),
                                LocalDate.of(2026, 2, 13),
                                2);

                System.out.println("\n\n -- Start of Search Result (Before Booking) -- \n\n");
                System.out.println("hotelId | hotelName | roomType | pricePerNight | total | minAvail \n");
                for (String s : res)
                        System.out.println(s);
                System.out.println("\n\n -- End of Search Result (Before Booking) -- \n\n");

                // -------------------------
                // 2) BOOK
                // -------------------------
                String bookingId = service.book(
                                "H1",
                                "DELUXE",
                                LocalDate.of(2026, 2, 10),
                                LocalDate.of(2026, 2, 13),
                                2,
                                1);

                System.out.println("\n\n -- Booking Done -- \n");
                System.out.println("bookingId = " + bookingId);
                System.out.println("\n\n");

                // -------------------------
                // 3) GET BOOKING
                // -------------------------
                Booking booking = service.getBooking(bookingId);

                System.out.println("\n\n -- Start of GetBooking Result -- \n\n");
                System.out.println("id        : " + booking.getId());
                System.out.println("hotelId   : " + booking.getHotelId());
                System.out.println("roomType  : " + booking.getRoomType());
                System.out.println("checkIn   : " + booking.getCheckIn());
                System.out.println("checkOut  : " + booking.getCheckOut());
                System.out.println("guests    : " + booking.getGuests());
                System.out.println("roomsQty  : " + booking.getRoomsQty());
                System.out.println("totalPrice: " + booking.getTotalPrice());
                System.out.println("status    : " + booking.getStatus());
                System.out.println("\n\n -- End of GetBooking Result -- \n\n");

                // -------------------------
                // 4) SEARCH (after booking) - optional, to see minAvail reduced
                // -------------------------
                List<String> resAfter = service.search("Goa",
                                LocalDate.of(2026, 2, 10),
                                LocalDate.of(2026, 2, 13),
                                2);

                System.out.println("\n\n -- Start of Search Result (After Booking) -- \n\n");
                System.out.println("hotelId | hotelName | roomType | pricePerNight | total | minAvail \n");
                for (String s : resAfter)
                        System.out.println(s);
                System.out.println("\n\n -- End of Search Result (After Booking) -- \n\n");

                // Cancel (simulate cancel time)
                CancellationReceipt receipt = service.cancel(bookingId, LocalDateTime.of(2026, 2, 8, 10, 0));
                System.out.println("\n\n -- Cancel Result -- \n");
                System.out.println("bookingId = " + receipt.getBookingId());
                System.out.println("refund    = " + receipt.getRefundAmount());
                System.out.println("fee       = " + receipt.getFeeAmount());

                // Verify booking status
                System.out.println("\nBooking status after cancel = " + service.getBooking(bookingId).getStatus());

                // Search after cancel to see availability restored
                System.out.println("\n\n -- Search After Cancel -- \n");
                for (String s : service.search("Goa",
                                LocalDate.of(2026, 2, 10),
                                LocalDate.of(2026, 2, 13),
                                2)) {
                        System.out.println(s);
                }
        }
}