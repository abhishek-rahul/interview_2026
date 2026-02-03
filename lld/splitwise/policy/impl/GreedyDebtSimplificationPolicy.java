package lld.splitwise.policy.impl;

import lld.splitwise.domain.TransferSuggestion;
import lld.splitwise.policy.DebtSimplificationPolicy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class GreedyDebtSimplificationPolicy implements DebtSimplificationPolicy {
    @Override
    public List<TransferSuggestion> simplify(Map<String, Long> netBalances) {
        if (netBalances == null || netBalances.isEmpty())
            return Collections.emptyList();

        List<Map.Entry<String, Long>> creditors = new ArrayList<>();
        List<Map.Entry<String, Long>> debtors = new ArrayList<>();

        for (Map.Entry<String, Long> e : netBalances.entrySet()) {
            long v = e.getValue();
            if (v > 0)
                creditors.add(Map.entry(e.getKey(), v));
            else if (v < 0)
                debtors.add(Map.entry(e.getKey(), v)); // negative
        }

        // deterministic order (optional): sort by userId
        creditors.sort(Comparator.comparing(Map.Entry::getKey));
        debtors.sort(Comparator.comparing(Map.Entry::getKey));

        int i = 0, j = 0;
        List<TransferSuggestion> out = new ArrayList<>();

        while (i < debtors.size() && j < creditors.size()) {
            String debtorId = debtors.get(i).getKey();
            long debtorOwes = -debtors.get(i).getValue(); // make positive

            String creditorId = creditors.get(j).getKey();
            long creditorGets = creditors.get(j).getValue();

            long pay = Math.min(debtorOwes, creditorGets);
            if (pay > 0) {
                out.add(new TransferSuggestion(debtorId, creditorId, pay));
            }

            debtorOwes -= pay;
            creditorGets -= pay;

            // update current entries (we replace entries with remaining)
            debtors.set(i, Map.entry(debtorId, -debtorOwes));
            creditors.set(j, Map.entry(creditorId, creditorGets));

            if (debtorOwes == 0)
                i++;
            if (creditorGets == 0)
                j++;
        }

        return out;
    }

}
