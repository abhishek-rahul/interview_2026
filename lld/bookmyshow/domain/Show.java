package lld.bookmyshow.domain;

import java.time.LocalDateTime;
import java.util.*;

public class Show {
    private final String id;
    private final String city;
    private final String theatreId;
    private final String screenId;
    private final LocalDateTime startTime;

    private final Movie movie;              // relation

    private final List<Seat> seats = new ArrayList<>();                  // layout
    private final Map<String, SeatState> seatStateBySeatId = new HashMap<>(); // ✅ only necessary map

    public Show(String id, String city, String theatreId, String screenId,
                LocalDateTime startTime, Movie movie) {
        this.id = Objects.requireNonNull(id);
        this.city = Objects.requireNonNull(city);
        this.theatreId = Objects.requireNonNull(theatreId);
        this.screenId = Objects.requireNonNull(screenId);
        this.startTime = Objects.requireNonNull(startTime);
        this.movie = Objects.requireNonNull(movie);
    }

    public String getId() { return id; }
    public String getCity() { return city; }
    public String getTheatreId() { return theatreId; }
    public String getScreenId() { return screenId; }
    public LocalDateTime getStartTime() { return startTime; }
    public Movie getMovie() { return movie; }

    public List<Seat> getSeats() { return seats; }
    public Map<String, SeatState> getSeatStateBySeatId() { return seatStateBySeatId; }

    public void addSeat(Seat seat) {
        seats.add(seat);
        seatStateBySeatId.put(seat.getId(), new SeatState());
    }

    public SeatState getSeatState(String seatId) {
        return seatStateBySeatId.get(seatId);
    }

    // NO helper logic yet (seatIds(), etc.) unless needed later by orchestrator
}
