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

    // NOTE: No minAvailable/decrement/increment yet (we’ll add during search/book flows)
}
