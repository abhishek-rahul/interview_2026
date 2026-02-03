package lld.splitwise.domain;

import lld.splitwise.domain.enums.DebtSimplifyMode;

import java.util.ArrayList;
import java.util.List;

public class Group {
    final String id;
    final String name;

    final List<String> memberUserIds = new ArrayList<>();
    final List<Expense> expenses = new ArrayList<>();
    final List<Settlement> settlements = new ArrayList<>();

    public final Ledger ledger = new Ledger();

    DebtSimplifyMode simplifyMode = DebtSimplifyMode.GREEDY_FAST;

    public DebtSimplifyMode getSimplifyMode() {
        return simplifyMode;
    }

    public Group(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean hasMember(String userId) {
        return memberUserIds.contains(userId);
    }

    public void addMember(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("userId cannot be empty");
        }
        if (memberUserIds.contains(userId)) {
            throw new IllegalArgumentException("duplicate member: " + userId);
        }
        memberUserIds.add(userId);

        // ensure ledger has an entry for this member (0 balance initially)
        ledger.ensureMemberInitialized(userId);
    }

    public void addExpense(Expense e) {
        if (e == null)
            throw new IllegalArgumentException("expense cannot be null");
        expenses.add(e);
        ledger.applyExpense(e);
    }

    public void addSettlement(Settlement s) {
        // TODO Stage 7E
    }

    public void setSimplifyMode(DebtSimplifyMode simplifyMode) {
        this.simplifyMode = simplifyMode;
    }
}
