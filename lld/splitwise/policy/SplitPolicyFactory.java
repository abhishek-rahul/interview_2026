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

    public SplitPolicy get(SplitType type) {
        if (type == null)
            throw new IllegalArgumentException("splitType cannot be null");

        switch (type) {
            case EQUAL:
                return equal;
            case EXACT:
                return exact;
            case PERCENT:
                return percent;
            case SHARE:
                return share;
            default:
                throw new IllegalArgumentException("unsupported splitType: " + type);
        }
    }
}
