package lld.splitwise.demo;

import java.util.Arrays;

import lld.splitwise.domain.SplitwiseCatalog;
import lld.splitwise.domain.enums.DebtSimplifyMode;
import lld.splitwise.policy.DebtSimplificationPolicyFactory;
import lld.splitwise.policy.SplitPolicyFactory;
import lld.splitwise.orchestrator.SplitwiseService;

import lld.splitwise.domain.SplitInput;
import lld.splitwise.domain.enums.SplitType;
import lld.splitwise.domain.TransferSuggestion;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Comparator;

public class MainDemo {

    private static String money(long paise) {
        // simple formatting: 120000 -> "1200.00"
        return String.format("%.2f", paise / 100.0);
    }

    private static void printBalances(String title, Map<String, Long> balances, Map<String, String> userNames) {
        System.out.println("\n=== " + title + " ===");
        // stable output order by name
        List<String> userIds = new ArrayList<>(balances.keySet());
        userIds.sort(Comparator.comparing(id -> userNames.getOrDefault(id, id)));

        for (String uid : userIds) {
            long v = balances.get(uid);
            String sign = (v >= 0) ? "+" : "";
            System.out.println(userNames.getOrDefault(uid, uid) + " : " + sign + money(v));
        }
    }

    private static void printSimplified(String title, List<TransferSuggestion> suggestions,
            Map<String, String> userNames) {
        System.out.println("\n=== " + title + " ===");
        if (suggestions == null || suggestions.isEmpty()) {
            System.out.println("(no transfers needed)");
            return;
        }
        for (TransferSuggestion t : suggestions) {
            String from = userNames.getOrDefault(t.fromUserId, t.fromUserId);
            String to = userNames.getOrDefault(t.toUserId, t.toUserId);
            System.out.println(from + " -> " + to + " : " + money(t.amountPaise));
        }
    }

    public static void main(String[] args) {

        SplitwiseCatalog catalog = new SplitwiseCatalog();
        SplitwiseService service = new SplitwiseService(
                catalog,
                new SplitPolicyFactory(),
                new DebtSimplificationPolicyFactory());

        // -----------------------
        // 1) Create Users
        // -----------------------
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

        // -----------------------
        // 2) Create Group
        // -----------------------
        String goaTrip = service.createGroup(
                "Goa Trip",
                Arrays.asList(ravi, amit, neha, sumeet),
                DebtSimplifyMode.GREEDY_FAST);
        System.out.println("Group created: " + goaTrip);

        // -----------------------
        // 3) Add Expenses
        // -----------------------

        // Expense #1: Cab ₹1200, Ravi paid, EQUAL split among all 4
        long cab = 1200L * 100;
        String e1 = service.addExpense(
                goaTrip,
                ravi,
                cab,
                SplitType.EQUAL,
                Arrays.asList(
                        new SplitInput(ravi, 0),
                        new SplitInput(amit, 0),
                        new SplitInput(neha, 0),
                        new SplitInput(sumeet, 0)),
                "Cab");
        System.out.println("Expense added: " + e1 + " (Cab " + money(cab) + ")");

        printBalances("Balances after Cab (equal 4)", service.getBalances(goaTrip), userNames);
        printSimplified("Simplified debts after Cab", service.getSimplifiedDebts(goaTrip), userNames);

        // Expense #2: Dinner ₹2000, Amit paid, EXACT split
        // Ravi: 800, Amit: 200, Neha: 500, Sumeet: 500 (sum=2000)
        long dinner = 2000L * 100;
        String e2 = service.addExpense(
                goaTrip,
                amit,
                dinner,
                SplitType.EXACT,
                Arrays.asList(
                        new SplitInput(ravi, 800L * 100),
                        new SplitInput(amit, 200L * 100),
                        new SplitInput(neha, 500L * 100),
                        new SplitInput(sumeet, 500L * 100)),
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
                Arrays.asList(
                        new SplitInput(ravi, 40),
                        new SplitInput(amit, 20),
                        new SplitInput(neha, 10),
                        new SplitInput(sumeet, 30)),
                "Water Sports (percent)");
        System.out.println("Expense added: " + e3 + " (Sports " + money(sports) + ")");

        printBalances("Balances after Sports (percent)", service.getBalances(goaTrip), userNames);
        printSimplified("Simplified debts after Sports", service.getSimplifiedDebts(goaTrip), userNames);

        // -----------------------
        // DONE till now
        // -----------------------
        System.out.println(
                "\n✅ Demo complete till: createUser, createGroup, addExpense, getBalances, getSimplifiedDebts");

        System.out.println("\n--- Settle Up Example ---");

        printBalances("Balances BEFORE settlement", service.getBalances(goaTrip), userNames);
        printSimplified("Simplified debts BEFORE settlement", service.getSimplifiedDebts(goaTrip), userNames);

        List<TransferSuggestion> sug = service.getSimplifiedDebts(goaTrip);
        if (!sug.isEmpty()) {
            TransferSuggestion t0 = sug.get(0);
            String sId = service.settleUp(goaTrip, t0.fromUserId, t0.toUserId, t0.amountPaise);
            System.out.println("\nSettlement recorded: " + sId + " | " +
                    userNames.getOrDefault(t0.fromUserId, t0.fromUserId) + " -> " +
                    userNames.getOrDefault(t0.toUserId, t0.toUserId) + " : " + money(t0.amountPaise));
        }

        printBalances("Balances AFTER settlement", service.getBalances(goaTrip), userNames);
        printSimplified("Simplified debts AFTER settlement", service.getSimplifiedDebts(goaTrip), userNames);

    }
}