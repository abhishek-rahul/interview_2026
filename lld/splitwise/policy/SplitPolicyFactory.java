package lld.splitwise.policy;

import lld.splitwise.domain.enums.SplitType;
import lld.splitwise.policy.impl.EqualSplitPolicy;
import lld.splitwise.policy.impl.ExactSplitPolicy;
import lld.splitwise.policy.impl.PercentSplitPolicy;
import lld.splitwise.policy.impl.ShareSplitPolicy;

public class SplitPolicyFactory {
    private final SplitPolicy equal = new EqualSplitPolicy();
    private final SplitPolicy exact = new ExactSplitPolicy();
    private final SplitPolicy percent = new PercentSplitPolicy();
    private final SplitPolicy share = new ShareSplitPolicy();

    SplitPolicy get(SplitType type) {
        // TODO Stage 7C (return appropriate policy)
        return null;
    }
}
