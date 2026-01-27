package lld.parkinglot.policy;
import lld.parkinglot.domain.ParkingLot;
import lld.parkinglot.domain.ParkingSpot;
import lld.parkinglot.domain.Vehicle;

public interface SpotAllocationPolicy {
    ParkingSpot findSpot(ParkingLot lot, Vehicle vehicle);
}
