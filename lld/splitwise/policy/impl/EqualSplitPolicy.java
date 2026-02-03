package lld.splitwise.policy.impl;


import  lld.splitwise.domain.SplitInput;
import  lld.splitwise.domain.SplitLine;
import  lld.splitwise.policy.SplitPolicy;

import  java.util.List;

public class EqualSplitPolicy implements SplitPolicy {
    @Override 
    public void validate(long totalPaise, String payerId, List<SplitInput> inputs) { /* TODO */ }
    @Override 
    public List<SplitLine> compute(long totalPaise, String payerId, List<SplitInput> inputs) { return null; }
}
