package lld.vendingmachine.policy.impl;

import java.util.Collections;
import lld.vendingmachine.domain.MoneyInventory;
import lld.vendingmachine.policy.ChangeMakingPolicy;
//import lld.vendingmachine.policy.impl.ChangePlan;

public class GreedyChangeMakingPolicy implements ChangeMakingPolicy {
    @Override
    public ChangePlan planChange(int requiredChange, MoneyInventory cash) {
        return new ChangePlan(false, Collections.emptyList());
    }
}