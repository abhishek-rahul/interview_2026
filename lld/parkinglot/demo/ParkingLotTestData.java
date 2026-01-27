package lld.parkinglot.demo;

import lld.parkinglot.domain.ParkingFloor;
import lld.parkinglot.domain.ParkingLot;
import lld.parkinglot.domain.ParkingSpot;
import lld.parkinglot.domain.enums.VehicleType;
import java.util.Arrays;
import java.util.List;
public final class ParkingLotTestData {

    static ParkingLot createSmallLot() {
        // Floor 1: 1 BIKE, 1 CAR
        List<ParkingSpot> f1Spots = Arrays.asList(
                new ParkingSpot("F1-B1", VehicleType.BIKE),
                new ParkingSpot("F1-C1", VehicleType.CAR)
        );

        // Floor 2: 1 CAR, 1 TRUCK
        List<ParkingSpot> f2Spots = Arrays.asList(
                new ParkingSpot("F2-C1", VehicleType.CAR),
                new ParkingSpot("F2-T1", VehicleType.TRUCK)
        );

        ParkingFloor floor1 = new ParkingFloor("F1", f1Spots);
        ParkingFloor floor2 = new ParkingFloor("F2", f2Spots);

        return new ParkingLot("LOT-1", Arrays.asList(floor1, floor2));
    }
}