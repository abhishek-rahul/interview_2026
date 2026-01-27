package lld.parkinglot.service;

import java.util.*;

import lld.parkinglot.domain.*;
import lld.parkinglot.exceptions.*;
import lld.parkinglot.policy.*;
import lld.parkinglot.util.*;

public final class ParkingLotService {

    private final ParkingLot parkingLot;
    private final SpotAllocationPolicy allocationPolicy;
    private final FeeCalculationPolicy feePolicy;
    private final Clock clock;
    private final TicketIdGenerator ticketIdGenerator;

    // In-memory ticket registry (MVP)
    private final Map<String, Ticket> activeTickets = new HashMap<>();
    private final Map<String, Ticket> closedTickets = new HashMap<>(); // optional, but useful for "already closed" check

    public ParkingLotService(ParkingLot parkingLot,
                             SpotAllocationPolicy allocationPolicy,
                             FeeCalculationPolicy feePolicy,
                             Clock clock,
                             TicketIdGenerator ticketIdGenerator) {

        if (parkingLot == null) throw new IllegalArgumentException("parkingLot is required");
        if (allocationPolicy == null) throw new IllegalArgumentException("allocationPolicy is required");
        if (feePolicy == null) throw new IllegalArgumentException("feePolicy is required");
        if (clock == null) throw new IllegalArgumentException("clock is required");
        if (ticketIdGenerator == null) throw new IllegalArgumentException("ticketIdGenerator is required");

        this.parkingLot = parkingLot;
        this.allocationPolicy = allocationPolicy;
        this.feePolicy = feePolicy;
        this.clock = clock;
        this.ticketIdGenerator = ticketIdGenerator;
    }

    public Ticket park(Vehicle vehicle) {
        // 1) validate
        if (vehicle == null) {
            throw new IllegalArgumentException("vehicle is required");
        }

        // 2) decision: find spot
        ParkingSpot spot = allocationPolicy.findSpot(parkingLot, vehicle);
        if (spot == null) {
            throw new NoSpotAvailableException("No spot available for type: " + vehicle.getType());
        }

        // 3) mutate: occupy spot (invariant guard inside spot)
        spot.occupy(vehicle);

        // 4) create ticket
        String ticketId = ticketIdGenerator.nextId();
        long entryTime = clock.nowMillis();

        Ticket ticket = new Ticket(
                ticketId,
                vehicle.getVehicleNumber(),
                vehicle.getType(),
                spot.getSpotId(),
                entryTime
        );

        // 5) store active ticket
        activeTickets.put(ticketId, ticket);

        return ticket;
    }

    public double unpark(String ticketId) {
        // 1) validate
        if (ticketId == null || ticketId.trim().isEmpty()) {
            throw new IllegalArgumentException("ticketId is required");
        }
        ticketId = ticketId.trim();

        // 2) locate ticket
        Ticket ticket = activeTickets.get(ticketId);
        if (ticket == null) {
            // optional: if present in closedTickets -> already closed
            if (closedTickets.containsKey(ticketId)) {
                throw new TicketAlreadyClosedException("Ticket " + ticketId + " is already closed");
            }
            throw new InvalidTicketException("Invalid ticket: " + ticketId);
        }

        // 3) compute fee (decision)
        long exitTime = clock.nowMillis();
        double fee = feePolicy.calculateFee(ticket, exitTime);

        // 4) free spot
        ParkingSpot spot = parkingLot.getSpotById(ticket.getSpotId());
        if (spot == null) {
            // Shouldn't happen if lot index is correct; still guard
            throw new IllegalStateException("Spot not found for spotId=" + ticket.getSpotId());
        }
        spot.vacate();

        // 5) close ticket lifecycle
        ticket.close(exitTime, fee);

        // 6) move registries
        activeTickets.remove(ticketId);
        closedTickets.put(ticketId, ticket);

        return fee;
    }

    // Optional helper (nice for tests/debug)
    public int getActiveTicketCount() {
        return activeTickets.size();
    }
}
