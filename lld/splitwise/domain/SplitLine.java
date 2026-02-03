package lld.splitwise.domain;

public class SplitLine {
    public final String userId;
    public final long owedPaise;


    public SplitLine(String userId, long owedPaise) {
        this.userId = userId;
        this.owedPaise = owedPaise;
    }

}
