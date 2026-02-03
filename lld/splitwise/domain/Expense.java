package lld.splitwise.domain;

import java.util.List;

public class Expense {
    final String id;
    final String groupId;
    final String payerId;
    final long totalPaise;
    final List<SplitLine> lines;
    final String note;
    final long createdAtEpochMs;

    public Expense(String id,
            String groupId,
            String payerId,
            long totalPaise,
            List<SplitLine> lines,
            String note,
            long createdAtEpochMs) {
        this.id = id;
        this.groupId = groupId;
        this.payerId = payerId;
        this.totalPaise = totalPaise;
        this.lines = lines;
        this.note = note;
        this.createdAtEpochMs = createdAtEpochMs;
    }
}
