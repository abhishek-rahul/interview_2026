package lld.vendingmachine.domain;

import java.util.Map;
import java.util.Set;

public class MoneyInventory {
    private final Map<Integer, Integer> denomCounts;

    public MoneyInventory(Map<Integer, Integer> denomCounts) {
        this.denomCounts = denomCounts;
    }

    public int getCount(int denom) {
        Integer c = denomCounts.get(denom);
        return (c == null) ? 0 : c;
    }

    public boolean hasAtLeast(int denom, int qty) {
        if (qty < 0)
            return false;
        return getCount(denom) >= qty;
    }

    public void add(int denom, int qty) {
        if (denom <= 0) {
            throw new IllegalArgumentException("Invalid denomination: " + denom);
        }
        if (qty <= 0) {
            throw new IllegalArgumentException("Qty must be > 0");
        }
        int newCount = getCount(denom) + qty;
        denomCounts.put(denom, newCount);
    }

    public void remove(int denom, int qty) {
        if (denom <= 0) {
            throw new IllegalArgumentException("Invalid denomination: " + denom);
        }
        if (qty <= 0) {
            throw new IllegalArgumentException("Qty must be > 0");
        }
        int cur = getCount(denom);
        if (cur < qty) {
            throw new IllegalStateException("Not enough cash for denom=" + denom +
                    ". have=" + cur + ", need=" + qty);
        }
        int newCount = cur - qty;
        if (newCount == 0)
            denomCounts.remove(denom);
        else
            denomCounts.put(denom, newCount);
    }

    public Set<Integer> supportedDenoms() {
        return denomCounts.keySet();
    }
}
