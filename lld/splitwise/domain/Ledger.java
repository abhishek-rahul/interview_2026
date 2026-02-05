package lld.splitwise.domain;

import java.util.HashMap;
import java.util.Map;

public class Ledger {
    private final Map<String, Long> netBalance = new HashMap<>();

    Ledger() {
    }

    public void applyExpense(Expense e) {
        if (e == null)
            throw new IllegalArgumentException("expense null");

        ensureMemberInitialized(e.payerId);

        // payer paid total
        netBalance.put(e.payerId, getNetPaise(e.payerId) + e.totalPaise);

        // participants owe their shares
        for (SplitLine line : e.lines) {
            ensureMemberInitialized(line.userId);
            netBalance.put(line.userId, getNetPaise(line.userId) - line.owedPaise);
        }
    }

    void applySettlement(Settlement s) {
        if (s == null)
            throw new IllegalArgumentException("settlement null");

        ensureMemberInitialized(s.fromUserId);
        ensureMemberInitialized(s.toUserId);

        // from paid amount -> from owes less (net increases)
        netBalance.put(s.fromUserId, getNetPaise(s.fromUserId) + s.amountPaise);

        // to received amount -> to should receive less (net decreases)
        netBalance.put(s.toUserId, getNetPaise(s.toUserId) - s.amountPaise);

        assertInvariantZeroSum();
    }

    public long getNetPaise(String userId) {
        return netBalance.getOrDefault(userId, 0L);
    }

    public Map<String, Long> snapshot() {
        return new HashMap<>(netBalance);
    }

    void ensureMemberInitialized(String userId) {
        netBalance.putIfAbsent(userId, 0L);
    }

    void assertInvariantZeroSum() {
        // TODO Stage 7C
    }
}
