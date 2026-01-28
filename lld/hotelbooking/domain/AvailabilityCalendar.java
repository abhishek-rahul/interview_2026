package lld.hotelbooking.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class AvailabilityCalendar {
    private final Map<LocalDate, Integer> qtyByDate = new HashMap<>();

    public void setQty(LocalDate date, int qty) {
        qtyByDate.put(date, qty);
    }

    public Map<LocalDate, Integer> getQtyByDate() {
        return qtyByDate;
    }

    // NOTE: No minAvailable/decrement/increment yet (we’ll add during search/book
    // flows)

    public int minAvailable(LocalDate checkIn, LocalDate checkOut) {
        int min = Integer.MAX_VALUE;
        for (LocalDate d = checkIn; d.isBefore(checkOut); d = d.plusDays(1)) {
            int q = qtyByDate.getOrDefault(d, 0);
            if (q < min)
                min = q;
        }
        return (min == Integer.MAX_VALUE) ? 0 : min;
    }

    public void decrement(LocalDate checkIn, LocalDate checkOut, int qty) {
        for (LocalDate d = checkIn; d.isBefore(checkOut); d = d.plusDays(1)) {
            int current = qtyByDate.getOrDefault(d, 0);
            int next = current - qty;
            if (next < 0) {
                throw new IllegalStateException("Insufficient availability on " + d +
                        " current=" + current + " need=" + qty);
            }
            qtyByDate.put(d, next);
        }
    }
}
