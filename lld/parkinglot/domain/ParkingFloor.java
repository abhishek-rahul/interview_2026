package lld.parkinglot.domain;

import java.util.*;

public final class ParkingFloor {
    private final String floorId;
    private final List<ParkingSpot> spots;

    public ParkingFloor(String floorId, List<ParkingSpot> spots) {
        if (floorId == null || floorId.trim().isEmpty()) {
            throw new IllegalArgumentException("floorId is required");
        }
        if (spots == null) {
            throw new IllegalArgumentException("spots list is required");
        }
        this.floorId = floorId.trim();
        this.spots = new ArrayList<>(spots); // defensive copy
    }

    public String getFloorId() {
        return floorId;
    }

    public List<ParkingSpot> getSpots() {
        return Collections.unmodifiableList(spots);
    }
}

