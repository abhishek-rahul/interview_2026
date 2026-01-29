package lld.bookmyshow.demo;
import lld.bookmyshow.domain.BmsCatalog;
import lld.bookmyshow.domain.Movie;
import lld.bookmyshow.domain.Seat;
import lld.bookmyshow.domain.Show;
import lld.bookmyshow.orchestrator.BookMyShowService;
import lld.bookmyshow.policy.impl.SimpleSeatAllocationPolicy;
import lld.bookmyshow.policy.impl.SimpleSeatLockPolicy;
import lld.bookmyshow.util.SimpleIdGenerator;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MainDemo {

    public static void main(String[] args) {

        // 1) Seed catalog
        BmsCatalog catalog = new BmsCatalog();

        Movie m1 = new Movie("M1", "Interstellar", "EN", 169);
        Movie m2 = new Movie("M2", "3 Idiots", "HI", 170);

        catalog.addMovie(m1);
        catalog.addMovie(m2);

        // Shows for Bangalore on 2026-02-10
        Show s1 = new Show("S1", "Bangalore", "PVR-Koramangala", "Screen-1",
                LocalDateTime.of(2026, 2, 10, 10, 0), m1);

        // ✅ Seed seats for S1
        s1.addSeat(new Seat("A1", "A", 1));
        s1.addSeat(new Seat("A2", "A", 2));
        s1.addSeat(new Seat("A3", "A", 3));
        s1.addSeat(new Seat("A4", "A", 4));
        s1.addSeat(new Seat("A5", "A", 5));

        Show s2 = new Show("S2", "Bangalore", "PVR-Koramangala", "Screen-2",
                LocalDateTime.of(2026, 2, 10, 13, 30), m1);

        // Show for Bangalore but different movie
        Show s3 = new Show("S3", "Bangalore", "INOX-Mall", "Screen-3",
                LocalDateTime.of(2026, 2, 10, 18, 0), m2);

        // Show for different city
        Show s4 = new Show("S4", "Goa", "PVR-Goa", "Screen-1",
                LocalDateTime.of(2026, 2, 10, 11, 0), m1);

        catalog.addShow(s1);
        catalog.addShow(s2);
        catalog.addShow(s3);
        catalog.addShow(s4);

        // 2) Orchestrator
        BookMyShowService service = new BookMyShowService(
                catalog,
                new SimpleSeatAllocationPolicy(), // TODO methods later
                new SimpleSeatLockPolicy(),       // TODO methods later
                new SimpleIdGenerator()
        );

        // 3) Test listShows
        System.out.println("\n--- LIST SHOWS (Bangalore, Interstellar, 2026-02-10) ---\n");
        List<String> out = service.listShows("Bangalore", "M1", LocalDate.of(2026, 2, 10));

        if (out.isEmpty()) {
            System.out.println("No shows found");
        } else {
            for (String line : out) System.out.println(line);
        }

        System.out.println("\n--- LIST SHOWS (Goa, Interstellar, 2026-02-10) ---\n");
        List<String> out2 = service.listShows("Goa", "M1", LocalDate.of(2026, 2, 10));
        for (String line : out2) System.out.println(line);

        System.out.println("\n--- LIST SHOWS (Bangalore, Interstellar, 2026-02-11) ---\n");
        List<String> out3 = service.listShows("Bangalore", "M1", LocalDate.of(2026, 2, 11));
        if (out3.isEmpty()) System.out.println("No shows found");

        List<String> shows = service.listShows("Bangalore", "M1", LocalDate.of(2026, 2, 10));
        for (String line : shows) System.out.println(line);

        // ✅ View seats for S1
        Instant now = Instant.parse("2026-02-01T10:00:00Z");

        System.out.println("\n--- VIEW SEATS (Show S1) ---\n");
        System.out.println("seatId | status | owner | expiry | bookingId");
        List<String> seats = service.viewSeats("S1", now);
        for (String line : seats) System.out.println(line);
    }
}
