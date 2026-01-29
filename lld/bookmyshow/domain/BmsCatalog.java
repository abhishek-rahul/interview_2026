package lld.bookmyshow.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lld.hotelbooking.domain.Booking;

public class BmsCatalog {
    private final List<Movie> movies = new ArrayList<>();
    private final List<Show> shows = new ArrayList<>();
    private final List<LockToken> lockTokens = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();
    public void addMovie(Movie movie) { movies.add(Objects.requireNonNull(movie)); }
    public void addShow(Show show) { shows.add(Objects.requireNonNull(show)); }
    public void addLockToken(LockToken lockToken) { lockTokens.add(Objects.requireNonNull(lockToken)); }
    public void addBooking(Booking booking) { bookings.add(Objects.requireNonNull(booking)); }    

    public void removeLockToken(String tokenId) { throw new UnsupportedOperationException("TODO"); }

    public List<Show> findShows(String city, String movieId, LocalDate date) {
        throw new UnsupportedOperationException("TODO");
    }

    public List<Movie> getMovies() { return movies; }
    public List<Show> getShows() { return shows; }
    public List<LockToken> getLockTokens() { return lockTokens; }
    public List<Booking> getBookings() { return bookings; }    
}
