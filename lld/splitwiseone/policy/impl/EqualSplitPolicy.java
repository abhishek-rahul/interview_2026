package lld.splitwiseone.policy.impl;

import lld.splitwiseone.policy.SplitPolicy;

import java.util.LinkedHashMap;
import java.util.Map;

public class EqualSplitPolicy implements SplitPolicy {
    @Override
    public void validate(long totalPaise, String payerId, Map<String, Long> splitValues) {
        if (totalPaise <= 0)
            throw new IllegalArgumentException("total must be > 0");
        if (payerId == null || payerId.isBlank())
            throw new IllegalArgumentException("payerId empty");
        if (splitValues == null || splitValues.isEmpty())
            throw new IllegalArgumentException("participants empty");
    }

    @Override
    public Map<String, Long> compute(long totalPaise, String payerId, Map<String, Long> splitValues) {
        int n = splitValues.size();
        long each = totalPaise / n;
        long rem = totalPaise % n;

        Map<String, Long> owedByUser = new LinkedHashMap<>();
        for (String userId : splitValues.keySet()) {
            long owed = each;
            if (userId.equals(payerId)) {
                owed += rem; // remainder goes to payer
            }
            owedByUser.put(userId, owed);
        }
        return owedByUser;
    }
}
