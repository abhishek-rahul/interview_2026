package lld.splitwise.orchestrator;

import lld.splitwise.domain.SplitInput;
import lld.splitwise.domain.SplitwiseCatalog;
import lld.splitwise.domain.TransferSuggestion;
import lld.splitwise.domain.User;
import lld.splitwise.domain.Group;
import lld.splitwise.policy.DebtSimplificationPolicyFactory;
import lld.splitwise.policy.SplitPolicyFactory;
import lld.splitwise.domain.enums.SplitType;
import lld.splitwise.domain.enums.DebtSimplifyMode;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class SplitwiseService {
    private final SplitwiseCatalog catalog;
    private final SplitPolicyFactory splitPolicyFactory;
    private final DebtSimplificationPolicyFactory debtPolicyFactory;

    public SplitwiseService(SplitwiseCatalog catalog,
            SplitPolicyFactory splitPolicyFactory,
            DebtSimplificationPolicyFactory debtPolicyFactory) {
        this.catalog = catalog;
        this.splitPolicyFactory = splitPolicyFactory;
        this.debtPolicyFactory = debtPolicyFactory;
    }

    public String createUser(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name cannot be empty");
        }

        String userId = "U-" + UUID.randomUUID().toString();
        User u = new User(userId, name.trim());
        catalog.addUser(u);
        return userId;
    }

    public String createGroup(String name, List<String> memberUserIds, DebtSimplifyMode mode) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("group name cannot be empty");
        }
        if (memberUserIds == null || memberUserIds.isEmpty()) {
            throw new IllegalArgumentException("group must have at least 1 member");
        }

        // 1) validate all members exist + no duplicates
        Set<String> seen = new HashSet<>();
        for (String uid : memberUserIds) {
            if (uid == null || uid.trim().isEmpty()) {
                throw new IllegalArgumentException("member userId cannot be empty");
            }
            if (!seen.add(uid)) {
                throw new IllegalArgumentException("duplicate member in request: " + uid);
            }
            if (catalog.findUserById(uid) == null) {
                throw new IllegalArgumentException("user not found: " + uid);
            }
        }

        // 2) create group entity
        String groupId = "G-" + UUID.randomUUID().toString();
        Group g = new Group(groupId, name.trim());
        DebtSimplifyMode sMode = g.getSimplifyMode();
        sMode = (mode == null) ? DebtSimplifyMode.GREEDY_FAST : mode;
        g.setSimplifyMode(sMode);

        // 3) add members (entity enforces invariants + ledger init)
        for (String uid : memberUserIds) {
            g.addMember(uid);
        }

        // 4) store in catalog
        catalog.addGroup(g);

        return groupId;
    }

    public String addExpense(String groupId,
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

    public Map<String, Long> getBalances(String groupId) {
        // TODO Stage 7B
        return null;
    }

    public List<TransferSuggestion> getSimplifiedDebts(String groupId) {
        // TODO Stage 7B/7C:
        // 1) snapshot = group.ledger.snapshot()
        // 2) policy = debtPolicyFactory.get(group.simplifyMode)
        // 3) return policy.simplify(snapshot)
        return null;
    }

    public String settleUp(String groupId, String fromUserId, String toUserId, long amountPaise) {
        // TODO Stage 7E:
        // 1) validate + create Settlement
        // 2) group.addSettlement + group.ledger.applySettlement
        return null;
    }
}