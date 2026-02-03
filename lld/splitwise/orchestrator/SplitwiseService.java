package lld.splitwise.orchestrator;

import lld.splitwise.domain.SplitInput;
import lld.splitwise.domain.SplitwiseCatalog;
import lld.splitwise.domain.TransferSuggestion;
import lld.splitwise.policy.DebtSimplificationPolicyFactory;
import lld.splitwise.policy.SplitPolicyFactory;
import lld.splitwise.domain.enums.SplitType;
import lld.splitwise.domain.enums.DebtSimplifyMode;

import java.util.List;
import java.util.Map;


public class SplitwiseService {
    private final SplitwiseCatalog catalog;
    private final SplitPolicyFactory splitPolicyFactory;
    private final DebtSimplificationPolicyFactory debtPolicyFactory;

    SplitwiseService(SplitwiseCatalog catalog,
                     SplitPolicyFactory splitPolicyFactory,
                     DebtSimplificationPolicyFactory debtPolicyFactory) {
        this.catalog = catalog;
        this.splitPolicyFactory = splitPolicyFactory;
        this.debtPolicyFactory = debtPolicyFactory;
    }

    String createUser(String name) {
        // TODO Stage 7A
        return null;
    }

    String createGroup(String name, List<String> memberUserIds, DebtSimplifyMode mode) {
        // TODO Stage 7A
        return null;
    }

    String addExpense(String groupId,
                      String payerId,
                      long totalPaise,
                      SplitType splitType,
                      List<SplitInput> inputs,
                      String note) {
        // TODO Stage 7D:
        // 1) validate group + payer + participants
        // 2) splitPolicy.validate(...)
        // 3) lines = splitPolicy.compute(...)
        // 4) create Expense
        // 5) group.addExpense + group.ledger.applyExpense
        return null;
    }

    Map<String, Long> getBalances(String groupId) {
        // TODO Stage 7B
        return null;
    }

    List<TransferSuggestion> getSimplifiedDebts(String groupId) {
        // TODO Stage 7B/7C:
        // 1) snapshot = group.ledger.snapshot()
        // 2) policy = debtPolicyFactory.get(group.simplifyMode)
        // 3) return policy.simplify(snapshot)
        return null;
    }

    String settleUp(String groupId, String fromUserId, String toUserId, long amountPaise) {
        // TODO Stage 7E:
        // 1) validate + create Settlement
        // 2) group.addSettlement + group.ledger.applySettlement
        return null;
    }
}