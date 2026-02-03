package lld.splitwise.domain;

public class TransferSuggestion {
    public final String fromUserId;
    public final String toUserId;
    public final long amountPaise;

    public TransferSuggestion(String fromUserId, String toUserId, long amountPaise) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amountPaise = amountPaise;
    }
}
