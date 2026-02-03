package lld.splitwise.domain;

public class SplitLine {
    final String userId;
    final long owedPaise;

    SplitLine(String userId, long owedPaise) {
        this.userId = userId;
        this.owedPaise = owedPaise;
    }
}
