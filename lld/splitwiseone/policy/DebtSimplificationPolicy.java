package lld.splitwiseone.policy;

import java.util.Map;

public interface DebtSimplificationPolicy {
    // debtorUserId -> creditorUserId -> amountPaise
    Map<String, Map<String, Long>> simplify(Map<String, Long> netBalances);
}
