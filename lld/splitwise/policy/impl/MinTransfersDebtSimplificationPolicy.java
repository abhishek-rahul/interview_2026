package lld.splitwise.policy.impl;

import lld.splitwise.domain.TransferSuggestion;
import lld.splitwise.policy.DebtSimplificationPolicy;
import java.util.List;
import java.util.Map;

public class MinTransfersDebtSimplificationPolicy implements DebtSimplificationPolicy {
    @Override
    public List<TransferSuggestion> simplify(Map<String, Long> netBalances) {
        // TODO: true min-transfers algorithm can be added later.
        // For interview scope, reuse greedy approach.
        return new GreedyDebtSimplificationPolicy().simplify(netBalances);
    }
}
