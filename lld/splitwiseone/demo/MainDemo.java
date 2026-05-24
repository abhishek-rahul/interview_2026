package lld.splitwiseone.demo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Comparator;

import lld.splitwiseone.domain.SplitwiseCatalog;
import lld.splitwiseone.domain.enums.DebtSimplifyMode;
import lld.splitwiseone.domain.enums.SplitType;
import lld.splitwiseone.orchestrator.SplitwiseService;
import lld.splitwiseone.policy.DebtSimplificationPolicyFactory;
import lld.splitwiseone.policy.SplitPolicyFactory;

public class MainDemo {

    private static String money(long paise) {
        return String.format("%.2f", paise / 100.0);
    }

    private static void printBalances(String title, Map<String, Long> balances, Map<String, String> userNames) {
        System.out.println("\n=== " + title + " ===");

        List<String> userIds = new ArrayList<>(balances.keySet());
        userIds.sort(Comparator.comparing(id -> userNames.getOrDefault(id, id)));

        for (String uid : userIds) {
            long v = balances.get(uid);
            String sign = (v >= 0) ? "+" : "";
            System.out.println(userNames.getOrDefault(uid, uid) + " : " + sign + money(v));
        }
    }

    private static void printSimplified(String title,
            Map<String, Map<String, Long>> transfers,
            Map<String, String> userNames) {

        System.out.println("\n=== " + title + " ===");
        if (transfers == null || transfers.isEmpty()) {
            System.out.println("(no transfers needed)");
            return;
        }

        for (Map.Entry<String, Map<String, Long>> debtorEntry : transfers.entrySet()) {
            String debtorId = debtorEntry.getKey();
            for (Map.Entry<String, Long> creditorEntry : debtorEntry.getValue().entrySet()) {
                String creditorId = creditorEntry.getKey();
                long amount = creditorEntry.getValue();

                String from = userNames.getOrDefault(debtorId, debtorId);
                String to = userNames.getOrDefault(creditorId, creditorId);
                System.out.println(from + " -> " + to + " : " + money(amount));
            }
        }
    }

    public static void main(String[] args) {

        SplitwiseCatalog catalog = new SplitwiseCatalog();
        SplitwiseService service = new SplitwiseService(
                catalog,
                new SplitPolicyFactory(),
                new DebtSimplificationPolicyFactory());

        String ravi = service.createUser("Ravi");
        String amit = service.createUser("Amit");
        String neha = service.createUser("Neha");
        String sumeet = service.createUser("Sumeet");

        Map<String, String> userNames = new HashMap<>();
        userNames.put(ravi, "Ravi");
        userNames.put(amit, "Amit");
        userNames.put(neha, "Neha");
        userNames.put(sumeet, "Sumeet");

        System.out.println("Users created: " + ravi + ", " + amit + ", " + neha + ", " + sumeet);

        String goaTrip = service.createGroup(
                "Goa Trip",
                Arrays.asList(ravi, amit, neha, sumeet),
                DebtSimplifyMode.GREEDY_FAST);
        System.out.println("Group created: " + goaTrip);

        // Expense #1: Cab ₹1200, Ravi paid, EQUAL split among all 4
        long cab = 1200L * 100;
        String e1 = service.addExpense(
                goaTrip,
                ravi,
                cab,
                SplitType.EQUAL,
                SplitwiseService.splitValues(
                        ravi, 0L,
                        amit, 0L,
                        neha, 0L,
                        sumeet, 0L),
                "Cab");
        System.out.println("Expense added: " + e1 + " (Cab " + money(cab) + ")");

        printBalances("Balances after Cab (equal 4)", service.getBalances(goaTrip), userNames);
        printSimplified("Simplified debts after Cab", service.getSimplifiedDebts(goaTrip), userNames);

        // Expense #2: Dinner ₹2000, Amit paid, EXACT split
        // Ravi: 800, Amit: 200, Neha: 500, Sumeet: 500
        long dinner = 2000L * 100;
        String e2 = service.addExpense(
                goaTrip,
                amit,
                dinner,
                SplitType.EXACT,
                SplitwiseService.splitValues(
                        ravi, 800L * 100,
                        amit, 200L * 100,
                        neha, 500L * 100,
                        sumeet, 500L * 100),
                "Dinner (exact)");
        System.out.println("Expense added: " + e2 + " (Dinner " + money(dinner) + ")");

        printBalances("Balances after Dinner (exact)", service.getBalances(goaTrip), userNames);
        printSimplified("Simplified debts after Dinner", service.getSimplifiedDebts(goaTrip), userNames);

        // Expense #3: Water sports ₹1500, Neha paid, PERCENT split
        // Ravi 40%, Amit 20%, Neha 10%, Sumeet 30%
        long sports = 1500L * 100;
        String e3 = service.addExpense(
                goaTrip,
                neha,
                sports,
                SplitType.PERCENT,
                SplitwiseService.splitValues(
                        ravi, 40L,
                        amit, 20L,
                        neha, 10L,
                        sumeet, 30L),
                "Water Sports (percent)");
        System.out.println("Expense added: " + e3 + " (Sports " + money(sports) + ")");

        printBalances("Balances after Sports (percent)", service.getBalances(goaTrip), userNames);
        printSimplified("Simplified debts after Sports", service.getSimplifiedDebts(goaTrip), userNames);

        // Expense #4: Snacks ₹1000, Sumeet paid, SHARE split
        // Ravi 2 shares, Amit 1 share, Neha 1 share, Sumeet 1 share
        long snacks = 1000L * 100;
        String e4 = service.addExpense(
                goaTrip,
                sumeet,
                snacks,
                SplitType.SHARE,
                SplitwiseService.splitValues(
                        ravi, 2L,
                        amit, 1L,
                        neha, 1L,
                        sumeet, 1L),
                "Snacks (share)");
        System.out.println("Expense added: " + e4 + " (Snacks " + money(snacks) + ")");

        printBalances("Balances after Snacks (share)", service.getBalances(goaTrip), userNames);
        printSimplified("Simplified debts after Snacks", service.getSimplifiedDebts(goaTrip), userNames);

        System.out.println("\n✅ Demo complete till: createUser, createGroup, addExpense, getBalances, getSimplifiedDebts");

        System.out.println("\n--- Settle Up Example ---");

        printBalances("Balances BEFORE settlement", service.getBalances(goaTrip), userNames);
        Map<String, Map<String, Long>> transfers = service.getSimplifiedDebts(goaTrip);
        printSimplified("Simplified debts BEFORE settlement", transfers, userNames);

        if (!transfers.isEmpty()) {
            String fromUserId = transfers.keySet().iterator().next();
            String toUserId = transfers.get(fromUserId).keySet().iterator().next();
            long amountPaise = transfers.get(fromUserId).get(toUserId);

            String settlementId = service.settleUp(goaTrip, fromUserId, toUserId, amountPaise);
            System.out.println("\nSettlement recorded: " + settlementId + " | "
                    + userNames.getOrDefault(fromUserId, fromUserId) + " -> "
                    + userNames.getOrDefault(toUserId, toUserId) + " : " + money(amountPaise));
        }

        printBalances("Balances AFTER settlement", service.getBalances(goaTrip), userNames);
        printSimplified("Simplified debts AFTER settlement", service.getSimplifiedDebts(goaTrip), userNames);
    }
}
