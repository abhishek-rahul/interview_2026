package lld.splitwise.policy.impl;

import lld.splitwise.domain.SplitInput;
import lld.splitwise.domain.SplitLine;
import lld.splitwise.policy.SplitPolicy;

import java.util.ArrayList;
import java.util.List;

public class EqualSplitPolicy implements SplitPolicy {
    @Override
    public void validate(long totalPaise, String payerId, List<SplitInput> inputs) {
        if (totalPaise <= 0)
            throw new IllegalArgumentException("total must be > 0");
        if (payerId == null || payerId.isBlank())
            throw new IllegalArgumentException("payerId empty");
        if (inputs == null || inputs.isEmpty())
            throw new IllegalArgumentException("participants empty");
    }

    @Override
    public List<SplitLine> compute(long totalPaise, String payerId, List<SplitInput> inputs) {
        int n = inputs.size();
        long each = totalPaise / n;
        long rem = totalPaise % n;

        List<SplitLine> lines = new ArrayList<>();
        for (SplitInput in : inputs) {
            long owed = each;
            if (in.userId.equals(payerId)) {
                owed += rem; // remainder to payer
            }
            lines.add(new SplitLine(in.userId, owed));
        }
        return lines;
    }

}
