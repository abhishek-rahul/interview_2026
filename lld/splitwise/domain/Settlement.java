package lld.splitwise.domain;

public class Settlement {
    final String id;
    final String groupId;
    final String fromUserId;
    final String toUserId;
    final long amountPaise;
    final long createdAtEpochMs;

    Settlement(String id,
               String groupId,
               String fromUserId,
               String toUserId,
               long amountPaise,
               long createdAtEpochMs) {
        this.id = id;
        this.groupId = groupId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amountPaise = amountPaise;
        this.createdAtEpochMs = createdAtEpochMs;
    }
}