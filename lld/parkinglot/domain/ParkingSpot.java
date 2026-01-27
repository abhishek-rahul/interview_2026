package lld.parkinglot.domain;
import lld.parkinglot.domain.enums.VehicleType;
import lld.parkinglot.exceptions.*;

public final class ParkingSpot {
    private final String spotId;
    private final VehicleType spotType;

    private boolean occupied;
    private Vehicle parkedVehicle; // null when free

    public ParkingSpot(String spotId, VehicleType spotType) {
        if (spotId == null || spotId.trim().isEmpty()) {
            throw new IllegalArgumentException("spotId is required");
        }
        if (spotType == null) {
            throw new IllegalArgumentException("spotType is required");
        }
        this.spotId = spotId.trim();
        this.spotType = spotType;
        this.occupied = false;
        this.parkedVehicle = null;
    }

    public String getSpotId() {
        return spotId;
    }

    public VehicleType getSpotType() {
        return spotType;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public boolean isFree() {
        return !occupied;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    // Invariant guard:
    // - already occupied? -> fail
    // - type mismatch? -> fail
    public void occupy(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("vehicle is required");
        }
        if (this.occupied) {
            throw new SpotAlreadyOccupiedException("Spot " + spotId + " is already occupied");
        }
        if (vehicle.getType() != this.spotType) {
            throw new SpotTypeMismatchException(
                    "Spot " + spotId + " is for " + spotType + " but vehicle is " + vehicle.getType()
            );
        }

        this.parkedVehicle = vehicle;
        this.occupied = true;
    }

    // Invariant guard:
    // - already free? -> fail (optional but clean)
    public void vacate() {
        if (!this.occupied) {
            throw new SpotAlreadyFreeException("Spot " + spotId + " is already free");
        }
        this.parkedVehicle = null;
        this.occupied = false;
    }
}

