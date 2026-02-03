package lld.splitwise.policy;

import lld.splitwise.domain.enums.DebtSimplifyMode;
import lld.splitwise.policy.impl.GreedyDebtSimplificationPolicy;
import lld.splitwise.policy.impl.MinTransfersDebtSimplificationPolicy;



public class DebtSimplificationPolicyFactory {
    private final DebtSimplificationPolicy greedy = new GreedyDebtSimplificationPolicy();
    private final DebtSimplificationPolicy minTransfers = new MinTransfersDebtSimplificationPolicy();

    DebtSimplificationPolicy get(DebtSimplifyMode mode) {
        // TODO Stage 7B/7C (select policy)
        return null;
    }
}