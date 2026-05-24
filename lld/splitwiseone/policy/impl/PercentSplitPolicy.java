package lld.splitwiseone.policy.impl;

import lld.splitwiseone.policy.SplitPolicy;

import java.util.LinkedHashMap;
import java.util.Map;

public class PercentSplitPolicy implements SplitPolicy {
    @Override
    public void validate(long totalPaise, String payerId, Map<String, Long> splitValues) {
        if (totalPaise <= 0)
            throw new IllegalArgumentException("total must be > 0");
        if (payerId == null || payerId.isBlank())
            throw new IllegalArgumentException("payerId empty");
        if (splitValues == null || splitValues.isEmpty())
            throw new IllegalArgumentException("participants empty");

        long sumPercent = 0;
        for (long percent : splitValues.values()) {
            if (percent <= 0)
                throw new IllegalArgumentException("percent must be > 0");
            sumPercent += percent;
        }
        if (sumPercent != 100)
            throw new IllegalArgumentException("percent total must be 100");
    }

    @Override
    public Map<String, Long> compute(long totalPaise, String payerId, Map<String, Long> splitValues) {
        Map<String, Long> owedByUser = new LinkedHashMap<>();
        long allocated = 0;

        for (Map.Entry<String, Long> entry : splitValues.entrySet()) {
            long owed = (totalPaise * entry.getValue()) / 100;
            allocated += owed;
            owedByUser.put(entry.getKey(), owed);
        }

        long rem = totalPaise - allocated;
        if (rem != 0) {
            owedByUser.put(payerId, owedByUser.getOrDefault(payerId, 0L) + rem);
        }

        return owedByUser;
    }
}
