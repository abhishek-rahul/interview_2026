package lld.splitwise.policy;

import lld.splitwise.domain.enums.DebtSimplifyMode;
import lld.splitwise.policy.impl.GreedyDebtSimplificationPolicy;
import lld.splitwise.policy.impl.MinTransfersDebtSimplificationPolicy;

public class DebtSimplificationPolicyFactory {
    private final DebtSimplificationPolicy greedy = new GreedyDebtSimplificationPolicy();
    private final DebtSimplificationPolicy minTransfers = new MinTransfersDebtSimplificationPolicy();

    public DebtSimplificationPolicy get(DebtSimplifyMode mode) {
        if (mode == null)
            return greedy;
        switch (mode) {
            case GREEDY_FAST:
                return greedy;
            case MIN_TRANSFERS:
                return minTransfers;
            default:
                return greedy;
        }
    }
}