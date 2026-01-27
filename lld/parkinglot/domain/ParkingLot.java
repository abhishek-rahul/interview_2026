package lld.parkinglot.domain;

import java.util.*;

public final class ParkingLot {
    private final String lotId;
    private final List<ParkingFloor> floors;

    // optional but useful in unpark() to resolve spotId quickly
    private final Map<String, ParkingSpot> spotById;

    public ParkingLot(String lotId, List<ParkingFloor> floors) {
        if (lotId == null || lotId.trim().isEmpty()) {
            throw new IllegalArgumentException("lotId is required");
        }
        if (floors == null) {
            throw new IllegalArgumentException("floors list is required");
        }
        this.lotId = lotId.trim();
        this.floors = new ArrayList<>(floors);

        // build index
        this.spotById = new HashMap<>();
        for (ParkingFloor f : this.floors) {
            for (ParkingSpot s : f.getSpots()) {
                if (spotById.containsKey(s.getSpotId())) {
                    throw new IllegalArgumentException("Duplicate spotId: " + s.getSpotId());
                }
                spotById.put(s.getSpotId(), s);
            }
        }
    }

    public String getLotId() {
        return lotId;
    }

    public List<ParkingFloor> getFloors() {
        return Collections.unmodifiableList(floors);
    }

    public ParkingSpot getSpotById(String spotId) {
        if (spotId == null || spotId.trim().isEmpty()) {
            throw new IllegalArgumentException("spotId is required");
        }
        return spotById.get(spotId.trim());
    }
}

