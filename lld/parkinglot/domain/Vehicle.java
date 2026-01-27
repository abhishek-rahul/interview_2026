package lld.parkinglot.domain;
import lld.parkinglot.domain.enums.VehicleType;
public final class Vehicle {
    private final String vehicleNumber;
    private final VehicleType type;

    public Vehicle(String vehicleNumber, VehicleType type) {
        if (vehicleNumber == null || vehicleNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("vehicleNumber is required");
        }
        if (type == null) {
            throw new IllegalArgumentException("vehicle type is required");
        }
        this.vehicleNumber = vehicleNumber.trim();
        this.type = type;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public VehicleType getType() {
        return type;
    }
}
