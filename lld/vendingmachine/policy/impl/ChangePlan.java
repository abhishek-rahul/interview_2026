package lld.vendingmachine.policy.impl;

import java.util.List;

public class ChangePlan {
    private final boolean possible;
    private final List<Integer> changeDenoms;

    public ChangePlan(boolean possible, List<Integer> changeDenoms) {
        this.possible = possible;
        this.changeDenoms = changeDenoms;
    }

    public boolean possible() { return possible; }
    public List<Integer> changeDenoms() { return changeDenoms; }
}
