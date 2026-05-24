package lld.splitwiseone.policy;

import lld.splitwiseone.domain.enums.DebtSimplifyMode;
import lld.splitwiseone.policy.impl.GreedyDebtSimplificationPolicy;
import lld.splitwiseone.policy.impl.MinTransfersDebtSimplificationPolicy;

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