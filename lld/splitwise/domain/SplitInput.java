package lld.splitwise.domain;

public class SplitInput {
    final String userId;
    final long value;

    SplitInput(String userId, long value) {
        this.userId = userId;
        this.value = value;
    }
}
