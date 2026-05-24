package lld.splitwiseone.policy.impl;

import lld.splitwiseone.policy.DebtSimplificationPolicy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GreedyDebtSimplificationPolicy implements DebtSimplificationPolicy {
   /*
   creditors :
    Ravi   -> +800
    Rohit  -> +300

    debters : 
    Amit   -> -300
    Neha   -> -300
    Sumeet -> -300
    Ankit  -> -200
   */
    @Override
    public Map<String, Map<String, Long>> simplify(Map<String, Long> netBalances) {
        if (netBalances == null || netBalances.isEmpty())
            return Collections.emptyMap();

        List<Map.Entry<String, Long>> creditors = new ArrayList<>();
        List<Map.Entry<String, Long>> debtors = new ArrayList<>();

        for (Map.Entry<String, Long> e : netBalances.entrySet()) {
            long v = e.getValue();
            if (v > 0)
                creditors.add(Map.entry(e.getKey(), v));
            else if (v < 0)
                debtors.add(Map.entry(e.getKey(), v));
        }

        creditors.sort(Comparator.comparing(Map.Entry::getKey));
        debtors.sort(Comparator.comparing(Map.Entry::getKey));

        int i = 0, j = 0;
        Map<String, Map<String, Long>> transfers = new LinkedHashMap<>();

        while (i < debtors.size() && j < creditors.size()) {
            String debtorId = debtors.get(i).getKey();
            long debtorOwes = -debtors.get(i).getValue();

            String creditorId = creditors.get(j).getKey();
            long creditorGets = creditors.get(j).getValue();

            long pay = Math.min(debtorOwes, creditorGets);
            if (pay > 0) {
                transfers
                        .computeIfAbsent(debtorId, k -> new LinkedHashMap<>())
                        .put(creditorId, pay);
            }

            debtorOwes -= pay;
            creditorGets -= pay;

            debtors.set(i, Map.entry(debtorId, -debtorOwes));
            creditors.set(j, Map.entry(creditorId, creditorGets));

            if (debtorOwes == 0)
                i++;
            if (creditorGets == 0)
                j++;
        }

        return transfers;
    }
}
