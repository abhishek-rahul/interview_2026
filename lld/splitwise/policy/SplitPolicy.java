package lld.splitwise.policy;

import lld.splitwise.domain.SplitInput;
import lld.splitwise.domain.SplitLine;

import java.util.List;

public interface SplitPolicy {
    void validate(long totalPaise, String payerId, List<SplitInput> inputs);
    List<SplitLine> compute(long totalPaise, String payerId, List<SplitInput> inputs);
}