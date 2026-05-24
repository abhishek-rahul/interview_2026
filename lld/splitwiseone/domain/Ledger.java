package lld.splitwiseone.domain;

import java.util.HashMap;
import java.util.Map;

public class Ledger {
    private final Map<String, Long> netBalance = new HashMap<>();

    Ledger() {
    }
/* ravi -> 900  , payeeid : rohit : 300 , rahul : 300 , aniket : 300 */
    public void applyExpense(Expense e) {
        if (e == null)
            throw new IllegalArgumentException("expense null");

        ensureMemberInitialized(e.payerId);

        // payer paid total amount, so payer should get money back
        netBalance.put(e.payerId, getNetPaise(e.payerId) + e.totalPaise);

        // each participant owes their share
        for (Map.Entry<String, Long> entry : e.owedByUser.entrySet()) {
            String userId = entry.getKey();
            long owedPaise = entry.getValue();

            ensureMemberInitialized(userId);
            netBalance.put(userId, getNetPaise(userId) - owedPaise);
        }

        assertInvariantZeroSum();
    }

    void applySettlement(Settlement s) {
        if (s == null)
            throw new IllegalArgumentException("settlement null");

        ensureMemberInitialized(s.fromUserId);
        ensureMemberInitialized(s.toUserId);

        // from paid amount -> from owes less
        netBalance.put(s.fromUserId, getNetPaise(s.fromUserId) + s.amountPaise);

        // to received amount -> to should receive less
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
        long sum = 0;
        for (long v : netBalance.values()) {
            sum += v;
        }
        if (sum != 0) {
            throw new IllegalStateException("ledger invariant failed. total balance must be 0 but was " + sum);
        }
    }
}
