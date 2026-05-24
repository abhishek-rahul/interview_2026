package lld.splitwiseone.policy;

import java.util.Map;

public interface SplitPolicy {
    void validate(long totalPaise, String payerId, Map<String, Long> splitValues);

    // returns userId -> owedPaise
    Map<String, Long> compute(long totalPaise, String payerId, Map<String, Long> splitValues);
}
