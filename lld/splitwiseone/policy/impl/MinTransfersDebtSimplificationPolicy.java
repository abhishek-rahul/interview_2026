package lld.splitwiseone.policy.impl;

import lld.splitwiseone.policy.DebtSimplificationPolicy;

import java.util.Map;

public class MinTransfersDebtSimplificationPolicy implements DebtSimplificationPolicy {
    @Override
    public Map<String, Map<String, Long>> simplify(Map<String, Long> netBalances) {
        // TODO: true min-transfers algorithm can be added later.
        // For interview scope, reuse greedy approach.
        return new GreedyDebtSimplificationPolicy().simplify(netBalances);
    }
}
