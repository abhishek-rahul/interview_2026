package lld.parkinglot.domain;

import lld.parkinglot.domain.enums.TicketStatus;
import lld.parkinglot.domain.enums.VehicleType;
import lld.parkinglot.exceptions.TicketAlreadyClosedException;

public final class Ticket {
    private final String ticketId;

    private final String vehicleNumber;
    private final VehicleType vehicleType;

    private final String spotId;

    private final long entryTimeMillis;

    private TicketStatus status;
    private Long exitTimeMillis;    // nullable until closed
    private Double feePaid;         // nullable until closed

    public Ticket(String ticketId,
                  String vehicleNumber,
                  VehicleType vehicleType,
                  String spotId,
                  long entryTimeMillis) {

        if (ticketId == null || ticketId.trim().isEmpty()) {
            throw new IllegalArgumentException("ticketId is required");
        }
        if (vehicleNumber == null || vehicleNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("vehicleNumber is required");
        }
        if (vehicleType == null) {
            throw new IllegalArgumentException("vehicleType is required");
        }
        if (spotId == null || spotId.trim().isEmpty()) {
            throw new IllegalArgumentException("spotId is required");
        }

        this.ticketId = ticketId.trim();
        this.vehicleNumber = vehicleNumber.trim();
        this.vehicleType = vehicleType;
        this.spotId = spotId.trim();
        this.entryTimeMillis = entryTimeMillis;

        this.status = TicketStatus.ACTIVE;
        this.exitTimeMillis = null;
        this.feePaid = null;
    }

    public String getTicketId() { return ticketId; }
    public String getVehicleNumber() { return vehicleNumber; }
    public VehicleType getVehicleType() { return vehicleType; }
    public String getSpotId() { return spotId; }
    public long getEntryTimeMillis() { return entryTimeMillis; }
    public TicketStatus getStatus() { return status; }
    public Long getExitTimeMillis() { return exitTimeMillis; }
    public Double getFeePaid() { return feePaid; }

    public boolean isActive() {
        return status == TicketStatus.ACTIVE;
    }

    public void close(long exitTimeMillis, double feePaid) {
        if (this.status == TicketStatus.CLOSED) {
            throw new TicketAlreadyClosedException("Ticket " + ticketId + " is already closed");
        }
        if (exitTimeMillis < this.entryTimeMillis) {
            throw new IllegalArgumentException("exitTime cannot be before entryTime");
        }
        if (feePaid < 0) {
            throw new IllegalArgumentException("feePaid cannot be negative");
        }

        this.exitTimeMillis = exitTimeMillis;
        this.feePaid = feePaid;
        this.status = TicketStatus.CLOSED;
    }
}
