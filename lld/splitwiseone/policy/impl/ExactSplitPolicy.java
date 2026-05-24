package lld.splitwiseone.policy.impl;

import lld.splitwiseone.policy.SplitPolicy;

import java.util.LinkedHashMap;
import java.util.Map;

public class ExactSplitPolicy implements SplitPolicy {
    @Override
    public void validate(long totalPaise, String payerId, Map<String, Long> splitValues) {
        if (totalPaise <= 0)
            throw new IllegalArgumentException("total must be > 0");
        if (payerId == null || payerId.isBlank())
            throw new IllegalArgumentException("payerId empty");
        if (splitValues == null || splitValues.isEmpty())
            throw new IllegalArgumentException("participants empty");

        long sum = 0;
        for (long amount : splitValues.values()) {
            if (amount < 0)
                throw new IllegalArgumentException("exact amount cannot be negative");
            sum += amount;
        }
        if (sum != totalPaise)
            throw new IllegalArgumentException("exact sum != total");
    }

    @Override
    public Map<String, Long> compute(long totalPaise, String payerId, Map<String, Long> splitValues) {
        return new LinkedHashMap<>(splitValues);
    }
}
