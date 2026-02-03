package lld.vendingmachine.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lld.vendingmachine.domain.MoneyInventory;
import lld.vendingmachine.policy.ChangeMakingPolicy;
//import lld.vendingmachine.policy.impl.ChangePlan;

public class GreedyChangeMakingPolicy implements ChangeMakingPolicy {

    @Override
    public ChangePlan planChange(int requiredChange, MoneyInventory cash) {
        if (requiredChange < 0) {
            return new ChangePlan(false, Collections.emptyList());
        }
        if (requiredChange == 0) {
            return new ChangePlan(true, Collections.emptyList());
        }

        // sort available denoms in descending order
        List<Integer> denoms = new ArrayList<>(cash.supportedDenoms());
        denoms.sort(Collections.reverseOrder());

        int remaining = requiredChange;
        List<Integer> plan = new ArrayList<>();

        for (int denom : denoms) {
            if (denom <= 0)
                continue;

            int available = cash.getCount(denom);
            if (available <= 0)
                continue;

            int take = Math.min(available, remaining / denom);
            for (int i = 0; i < take; i++) {
                plan.add(denom);
                remaining -= denom;
            }

            if (remaining == 0)
                break;
        }

        if (remaining != 0) {
            return new ChangePlan(false, Collections.emptyList());
        }
        return new ChangePlan(true, plan);
    }
}