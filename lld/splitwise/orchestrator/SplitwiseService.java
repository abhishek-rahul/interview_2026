package lld.splitwise.orchestrator;

import lld.splitwise.domain.SplitInput;
import lld.splitwise.domain.SplitLine;
import lld.splitwise.domain.SplitwiseCatalog;
import lld.splitwise.domain.TransferSuggestion;
import lld.splitwise.domain.User;
import lld.splitwise.domain.Expense;
import lld.splitwise.domain.Group;
import lld.splitwise.domain.Settlement;
import lld.splitwise.policy.DebtSimplificationPolicy;
import lld.splitwise.policy.DebtSimplificationPolicyFactory;
import lld.splitwise.policy.SplitPolicy;
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

        // 1) group validation
        Group g = catalog.findGroupById(groupId);
        if (g == null)
            throw new IllegalArgumentException("group not found: " + groupId);

        // 2) payer validation
        if (payerId == null || payerId.isBlank())
            throw new IllegalArgumentException("payerId empty");
        if (!g.hasMember(payerId))
            throw new IllegalArgumentException("payer not in group: " + payerId);

        // 3) participant validation: all must be members + no duplicates
        if (inputs == null || inputs.isEmpty())
            throw new IllegalArgumentException("participants empty");
        Set<String> seen = new HashSet<>();
        for (SplitInput in : inputs) {
            if (in.userId == null || in.userId.isBlank())
                throw new IllegalArgumentException("participant userId empty");
            if (!g.hasMember(in.userId))
                throw new IllegalArgumentException("participant not in group: " + in.userId);
            if (!seen.add(in.userId))
                throw new IllegalArgumentException("duplicate participant: " + in.userId);
        }

        // 4) split compute via policy
        SplitPolicy policy = splitPolicyFactory.get(splitType);
        policy.validate(totalPaise, payerId, inputs);
        List<SplitLine> lines = policy.compute(totalPaise, payerId, inputs);

        // 5) create expense entity
        String expenseId = "E-" + UUID.randomUUID().toString();
        Expense e = new Expense(
                expenseId,
                groupId,
                payerId,
                totalPaise,
                lines,
                (note == null ? "" : note),
                System.currentTimeMillis());

        // 6) store record + apply to ledger
        g.addExpense(e);

        return expenseId;
    }

    public Map<String, Long> getBalances(String groupId) {
        // 1) validate + fetch group
        Group g = catalog.findGroupById(groupId);
        if (g == null) {
            throw new IllegalArgumentException("group not found: " + groupId);
        }

        // 2) return a defensive copy (read-only snapshot)
        return g.ledger.snapshot();
    }

    public List<TransferSuggestion> getSimplifiedDebts(String groupId) {
        Group g = catalog.findGroupById(groupId);
        if (g == null) {
            throw new IllegalArgumentException("group not found: " + groupId);
        }

        Map<String, Long> snapshot = g.ledger.snapshot(); // read-only copy
        DebtSimplificationPolicy policy = debtPolicyFactory.get(g.getSimplifyMode());
        return policy.simplify(snapshot);
    }

    public String settleUp(String groupId, String fromUserId, String toUserId, long amountPaise) {
        // 1) validate + fetch group
        Group g = catalog.findGroupById(groupId);
        if (g == null)
            throw new IllegalArgumentException("group not found: " + groupId);

        // 2) validate inputs
        if (fromUserId == null || fromUserId.isBlank())
            throw new IllegalArgumentException("fromUserId empty");
        if (toUserId == null || toUserId.isBlank())
            throw new IllegalArgumentException("toUserId empty");
        if (fromUserId.equals(toUserId))
            throw new IllegalArgumentException("from and to cannot be same");
        if (amountPaise <= 0)
            throw new IllegalArgumentException("amount must be > 0");

        // 3) both must be members
        if (!g.hasMember(fromUserId))
            throw new IllegalArgumentException("fromUser not in group: " + fromUserId);
        if (!g.hasMember(toUserId))
            throw new IllegalArgumentException("toUser not in group: " + toUserId);

        // 4) (optional correctness guard) from should owe and to should receive
        long fromNet = g.ledger.getNetPaise(fromUserId);
        long toNet = g.ledger.getNetPaise(toUserId);

        if (fromNet >= 0) {
            throw new IllegalArgumentException("fromUser does not owe money currently: net=" + fromNet);
        }
        if (toNet <= 0) {
            throw new IllegalArgumentException("toUser is not a receiver currently: net=" + toNet);
        }

        long maxPay = Math.min(-fromNet, toNet);
        if (amountPaise > maxPay) {
            throw new IllegalArgumentException("amount too high. max allowed=" + maxPay);
        }

        // 5) create settlement entity
        String settlementId = "S-" + UUID.randomUUID().toString();
        Settlement s = new Settlement(
                settlementId,
                groupId,
                fromUserId,
                toUserId,
                amountPaise,
                System.currentTimeMillis());

        // 6) apply (Option 2: group owns atomic update)
        g.addSettlement(s);

        return settlementId;
    }
}