package lld.splitwise.domain;

public class TransferSuggestion {
    final String fromUserId;
    final String toUserId;
    final long amountPaise;

    TransferSuggestion(String fromUserId, String toUserId, long amountPaise) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amountPaise = amountPaise;
    }
}
