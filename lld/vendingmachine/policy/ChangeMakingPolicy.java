package lld.vendingmachine.policy;

import lld.vendingmachine.domain.MoneyInventory;
import lld.vendingmachine.policy.impl.ChangePlan;

public interface ChangeMakingPolicy {
    ChangePlan planChange(int requiredChange, MoneyInventory cash);
}