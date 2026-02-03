package lld.splitwise.domain;

import java.util.HashMap;
import java.util.Map;

public class Ledger {
    private final Map<String, Long> netBalance = new HashMap<>();

    Ledger() {}

    void applyExpense(Expense e) {
        // TODO Stage 7C
    }

    void applySettlement(Settlement s) {
        // TODO Stage 7E
    }

    long getNetPaise(String userId) {
        return netBalance.getOrDefault(userId, 0L);
    }

    Map<String, Long> snapshot() {
        return new HashMap<>(netBalance);
    }

    void ensureMemberInitialized(String userId) {
        netBalance.putIfAbsent(userId, 0L);
    }

    void assertInvariantZeroSum() {
        // TODO Stage 7C
    }
}
