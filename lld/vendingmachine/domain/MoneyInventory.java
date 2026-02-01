package lld.vendingmachine.domain;

import java.util.Map;
import java.util.Set;

public class MoneyInventory {
    private final Map<Integer, Integer> denomCounts;

    public MoneyInventory(Map<Integer, Integer> denomCounts) {
        this.denomCounts = denomCounts;
    }

    public int getCount(int denom) {
        return 0;
    }

    public boolean hasAtLeast(int denom, int qty) {
        return false;
    }

    public void add(int denom, int qty) {
        // no logic in Stage 6
    }

    public void remove(int denom, int qty) {
        // no logic in Stage 6
    }

    public Set<Integer> supportedDenoms() {
        return denomCounts.keySet();
    }
}
