package lld.splitwise.policy;

import lld.splitwise.domain.TransferSuggestion;

import java.util.List;
import java.util.Map;

public interface DebtSimplificationPolicy {
    List<TransferSuggestion> simplify(Map<String, Long> netBalances);
}
