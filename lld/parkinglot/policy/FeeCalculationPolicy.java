package lld.parkinglot.policy;
import lld.parkinglot.domain.Ticket;

public interface FeeCalculationPolicy {
    double calculateFee(Ticket ticket, long exitTimeMillis);
}
