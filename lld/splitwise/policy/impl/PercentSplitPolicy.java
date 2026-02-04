package lld.splitwise.policy.impl;

import lld.splitwise.domain.SplitInput;
import lld.splitwise.domain.SplitLine;
import lld.splitwise.policy.SplitPolicy;

import java.util.ArrayList;
import java.util.List;

public class PercentSplitPolicy implements SplitPolicy {
    @Override
    public void validate(long totalPaise, String payerId, List<SplitInput> inputs) {
        if (totalPaise <= 0)
            throw new IllegalArgumentException("total must be > 0");
        if (payerId == null || payerId.isBlank())
            throw new IllegalArgumentException("payerId empty");
        if (inputs == null || inputs.isEmpty())
            throw new IllegalArgumentException("participants empty");

        long sumShares = 0;
        for (SplitInput in : inputs) {
            if (in.value <= 0)
                throw new IllegalArgumentException("share must be > 0");
            sumShares += in.value;
        }
        if (sumShares <= 0)
            throw new IllegalArgumentException("sumShares must be > 0");
    }

    @Override
    public List<SplitLine> compute(long totalPaise, String payerId, List<SplitInput> inputs) {
        long sumShares = 0;
        for (SplitInput in : inputs)
            sumShares += in.value;

        List<SplitLine> lines = new ArrayList<>();
        long allocated = 0;

        for (SplitInput in : inputs) {
            long owed = (totalPaise * in.value) / sumShares; // floor
            allocated += owed;
            lines.add(new SplitLine(in.userId, owed));
        }

        long rem = totalPaise - allocated;
        if (rem != 0) {
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).userId.equals(payerId)) {
                    SplitLine old = lines.get(i);
                    lines.set(i, new SplitLine(old.userId, old.owedPaise + rem));
                    break;
                }
            }
        }
        return lines;
    }

}
