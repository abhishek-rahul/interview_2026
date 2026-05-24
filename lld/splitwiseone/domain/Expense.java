package lld.splitwiseone.domain;

import java.util.Map;

public class Expense {
    final String id;
    final String groupId;
    final String payerId;
    final long totalPaise;

    // userId -> how much this user owes for this expense
    final Map<String, Long> owedByUser;

    final String note;
    final long createdAtEpochMs;

    public Expense(String id,
            String groupId,
            String payerId,
            long totalPaise,
            Map<String, Long> owedByUser,
            String note,
            long createdAtEpochMs) {
        this.id = id;
        this.groupId = groupId;
        this.payerId = payerId;
        this.totalPaise = totalPaise;
        this.owedByUser = owedByUser;
        this.note = note;
        this.createdAtEpochMs = createdAtEpochMs;
    }
}
