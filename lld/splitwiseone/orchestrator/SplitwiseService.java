package lld.splitwiseone.orchestrator;

import lld.splitwiseone.domain.SplitwiseCatalog;
import lld.splitwiseone.domain.User;
import lld.splitwiseone.domain.Expense;
import lld.splitwiseone.domain.Group;
import lld.splitwiseone.domain.Settlement;
import lld.splitwiseone.policy.DebtSimplificationPolicy;
import lld.splitwiseone.policy.DebtSimplificationPolicyFactory;
import lld.splitwiseone.policy.SplitPolicy;
import lld.splitwiseone.policy.SplitPolicyFactory;
import lld.splitwiseone.domain.enums.SplitType;
import lld.splitwiseone.domain.enums.DebtSimplifyMode;

import java.util.HashSet;
import java.util.LinkedHashMap;
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


    /*
    1. Name validate karo
    2. New userId banao
    3. User object banao
    4. Catalog me store karo
    5. userId return karo
    */
    public String createUser(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name cannot be empty");
        }

        String userId = "U-" + UUID.randomUUID();
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

        String groupId = "G-" + UUID.randomUUID();
        Group g = new Group(groupId, name.trim());
        g.setSimplifyMode(mode == null ? DebtSimplifyMode.GREEDY_FAST : mode);

        for (String uid : memberUserIds) {
            g.addMember(uid);
        }

        catalog.addGroup(g);
        return groupId;
    }

    public String addExpense(String groupId,
            String payerId,
            long totalPaise,
            SplitType splitType,
            Map<String, Long> splitValues,
            String note) {

        Group g = catalog.findGroupById(groupId);
        if (g == null)
            throw new IllegalArgumentException("group not found: " + groupId);

        if (payerId == null || payerId.isBlank())
            throw new IllegalArgumentException("payerId empty");
        if (!g.hasMember(payerId))
            throw new IllegalArgumentException("payer not in group: " + payerId);

        validateParticipants(g, splitValues);

        SplitPolicy policy = splitPolicyFactory.get(splitType);
        policy.validate(totalPaise, payerId, splitValues);
        Map<String, Long> owedByUser = policy.compute(totalPaise, payerId, splitValues);

        String expenseId = "E-" + UUID.randomUUID();
        Expense e = new Expense(
                expenseId,
                groupId,
                payerId,
                totalPaise,
                owedByUser,
                note == null ? "" : note,
                System.currentTimeMillis());

        g.addExpense(e);
        return expenseId;
    }

    public Map<String, Long> getBalances(String groupId) {
        Group g = catalog.findGroupById(groupId);
        if (g == null) {
            throw new IllegalArgumentException("group not found: " + groupId);
        }
        return g.ledger.snapshot();
    }
/*
{
  "amitId": {
    "raviId": 30000,
    "nehaId": 40000,
  },
  "nehaId": {
    "raviId": 30000
  },
  "sumeetId": {
    "raviId": 30000
  }
} */
    public Map<String, Map<String, Long>> getSimplifiedDebts(String groupId) {
        Group g = catalog.findGroupById(groupId);
        if (g == null) {
            throw new IllegalArgumentException("group not found: " + groupId);
        }

        DebtSimplificationPolicy policy = debtPolicyFactory.get(g.getSimplifyMode());
        return policy.simplify(g.ledger.snapshot());
    }

    public String settleUp(String groupId, String fromUserId, String toUserId, long amountPaise) {
        Group g = catalog.findGroupById(groupId);
        if (g == null)
            throw new IllegalArgumentException("group not found: " + groupId);

        if (fromUserId == null || fromUserId.isBlank())
            throw new IllegalArgumentException("fromUserId empty");
        if (toUserId == null || toUserId.isBlank())
            throw new IllegalArgumentException("toUserId empty");
        if (fromUserId.equals(toUserId))
            throw new IllegalArgumentException("from and to cannot be same");
        if (amountPaise <= 0)
            throw new IllegalArgumentException("amount must be > 0");

        if (!g.hasMember(fromUserId))
            throw new IllegalArgumentException("fromUser not in group: " + fromUserId);
        if (!g.hasMember(toUserId))
            throw new IllegalArgumentException("toUser not in group: " + toUserId);

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

        String settlementId = "S-" + UUID.randomUUID();
        Settlement s = new Settlement(
                settlementId,
                groupId,
                fromUserId,
                toUserId,
                amountPaise,
                System.currentTimeMillis());

        g.addSettlement(s);
        return settlementId;
    }

    private void validateParticipants(Group g, Map<String, Long> splitValues) {
        if (splitValues == null || splitValues.isEmpty())
            throw new IllegalArgumentException("participants empty");

        Set<String> seen = new HashSet<>();
        for (String userId : splitValues.keySet()) {
            if (userId == null || userId.isBlank())
                throw new IllegalArgumentException("participant userId empty");
            if (!g.hasMember(userId))
                throw new IllegalArgumentException("participant not in group: " + userId);
            if (!seen.add(userId))
                throw new IllegalArgumentException("duplicate participant: " + userId);
        }
    }

    public static Map<String, Long> splitValues(Object... pairs) {
        if (pairs == null || pairs.length % 2 != 0) {
            throw new IllegalArgumentException("pairs must be userId, value, userId, value...");
        }

        Map<String, Long> map = new LinkedHashMap<>();
        for (int i = 0; i < pairs.length; i += 2) {
            String userId = (String) pairs[i];
            Long value = (Long) pairs[i + 1];
            map.put(userId, value);
        }
        return map;
    }
}
