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

    final Ledger ledger = new Ledger();

    DebtSimplifyMode simplifyMode = DebtSimplifyMode.GREEDY_FAST;

    Group(String id, String name) {
        this.id = id;
        this.name = name;
    }

    boolean hasMember(String userId) {
        return memberUserIds.contains(userId);
    }

    void addMember(String userId) {
        // TODO Stage 7A
    }

    void addExpense(Expense e) {
        // TODO Stage 7D
    }

    void addSettlement(Settlement s) {
        // TODO Stage 7E
    }
}

