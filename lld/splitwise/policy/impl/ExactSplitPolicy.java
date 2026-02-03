package lld.splitwise.policy.impl;

import lld.splitwise.domain.SplitInput;
import lld.splitwise.domain.SplitLine;
import lld.splitwise.policy.SplitPolicy;

import java.util.ArrayList;
import java.util.List;

public class ExactSplitPolicy implements SplitPolicy {
    @Override
    public void validate(long totalPaise, String payerId, List<SplitInput> inputs) {
        if (totalPaise <= 0)
            throw new IllegalArgumentException("total must be > 0");
        if (payerId == null || payerId.isBlank())
            throw new IllegalArgumentException("payerId empty");
        if (inputs == null || inputs.isEmpty())
            throw new IllegalArgumentException("participants empty");

        long sum = 0;
        for (SplitInput in : inputs) {
            if (in.value < 0)
                throw new IllegalArgumentException("exact amount cannot be negative");
            sum += in.value;
        }
        if (sum != totalPaise)
            throw new IllegalArgumentException("exact sum != total");
    }

    @Override
    public List<SplitLine> compute(long totalPaise, String payerId, List<SplitInput> inputs) {
        List<SplitLine> lines = new ArrayList<>();
        for (SplitInput in : inputs) {
            lines.add(new SplitLine(in.userId, in.value));
        }
        return lines;
    }
}
