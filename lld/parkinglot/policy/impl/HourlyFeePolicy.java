
package lld.parkinglot.policy.impl;
import lld.parkinglot.domain.Ticket;
import lld.parkinglot.policy.*;
public final class HourlyFeePolicy implements FeeCalculationPolicy {
    private static final long ONE_HOUR_MILLIS = 60L * 60L * 1000L;

    private final RateCard rateCard;

    public HourlyFeePolicy(RateCard rateCard) {
        if (rateCard == null) throw new IllegalArgumentException("rateCard is required");
        this.rateCard = rateCard;
    }

    @Override
    public double calculateFee(Ticket ticket, long exitTimeMillis) {
        if (ticket == null) throw new IllegalArgumentException("ticket is required");
        if (exitTimeMillis < ticket.getEntryTimeMillis()) {
            throw new IllegalArgumentException("exitTime cannot be before entryTime");
        }

        long durationMillis = exitTimeMillis - ticket.getEntryTimeMillis();

        // Minimum 1 hour charge if duration > 0; if exactly 0, charge 0 (your call)
        // Interview-friendly: if parked even for 1 minute => 1 hour
        long hours = (durationMillis == 0) ? 0 : (long) Math.ceil(durationMillis / (double) ONE_HOUR_MILLIS);

        double rate = rateCard.ratePerHour(ticket.getVehicleType());
        return hours * rate;
    }
}
