package lld.splitwise.domain;

public class SplitInput {
    public final String userId;
    public final long value;

    public SplitInput(String userId, long value) {
        this.userId = userId;
        this.value = value;
    }
}
