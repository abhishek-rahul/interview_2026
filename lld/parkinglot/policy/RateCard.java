package lld.parkinglot.policy;

import java.util.*;

import lld.parkinglot.domain.enums.VehicleType;

public final class RateCard {
    private final Map<VehicleType, Double> hourlyRateByType = new EnumMap<>(VehicleType.class);

    public RateCard(double bikePerHour, double carPerHour, double truckPerHour) {
        hourlyRateByType.put(VehicleType.BIKE, bikePerHour);
        hourlyRateByType.put(VehicleType.CAR, carPerHour);
        hourlyRateByType.put(VehicleType.TRUCK, truckPerHour);
    }

    public double ratePerHour(VehicleType type) {
        Double rate = hourlyRateByType.get(type);
        if (rate == null) throw new IllegalArgumentException("No rate configured for " + type);
        return rate;
    }
}
