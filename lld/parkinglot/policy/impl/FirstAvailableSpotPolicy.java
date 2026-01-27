package lld.parkinglot.policy.impl;

import lld.parkinglot.domain.ParkingFloor;
import lld.parkinglot.domain.ParkingLot;
import lld.parkinglot.domain.ParkingSpot;
import lld.parkinglot.domain.Vehicle;
import lld.parkinglot.policy.SpotAllocationPolicy;
public final class FirstAvailableSpotPolicy implements SpotAllocationPolicy {

    @Override
    public ParkingSpot findSpot(ParkingLot lot, Vehicle vehicle) {
        if (lot == null) throw new IllegalArgumentException("lot is required");
        if (vehicle == null) throw new IllegalArgumentException("vehicle is required");

        for (ParkingFloor floor : lot.getFloors()) {
            for (ParkingSpot spot : floor.getSpots()) {
                if (spot.isFree() && spot.getSpotType() == vehicle.getType()) {
                    return spot;
                }
            }
        }
        return null; // means no spot available
    }
}
