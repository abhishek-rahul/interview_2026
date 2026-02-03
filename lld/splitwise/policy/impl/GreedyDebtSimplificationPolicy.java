package lld.splitwise.policy.impl;

import lld.splitwise.domain.TransferSuggestion;
import lld.splitwise.policy.DebtSimplificationPolicy;
import java.util.List;
import java.util.Map;

public class GreedyDebtSimplificationPolicy implements DebtSimplificationPolicy {
    @Override public List<TransferSuggestion> simplify(Map<String, Long> netBalances) { return null; }
}
