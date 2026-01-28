package lld.hotelbooking.orchestrator;

import lld.hotelbooking.domain.Booking;
import lld.hotelbooking.domain.CancellationReceipt;
import lld.hotelbooking.policy.CancellationPolicy;
import lld.hotelbooking.policy.PricingPolicy;
import lld.hotelbooking.policy.RoomAllocationPolicy;
import lld.hotelbooking.domain.HotelCatalog;
import lld.hotelbooking.util.IdGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public List<String> search(String city, LocalDate checkIn, LocalDate checkOut, int guests) {
        throw new UnsupportedOperationException("TODO");
    }

    public String book(String hotelId, String roomType,
                       LocalDate checkIn, LocalDate checkOut,
                       int guests, int roomsQty) {
        throw new UnsupportedOperationException("TODO");
    }

    public Booking getBooking(String bookingId) {
        throw new UnsupportedOperationException("TODO");
    }

    public CancellationReceipt cancel(String bookingId, LocalDateTime cancelTime) {
        throw new UnsupportedOperationException("TODO");
    }
}