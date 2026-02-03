package lld.splitwise.demo;

import lld.splitwise.domain.SplitwiseCatalog;
import lld.splitwise.policy.DebtSimplificationPolicyFactory;
import lld.splitwise.policy.SplitPolicyFactory;
import lld.splitwise.orchestrator.SplitwiseService;

public class MainDemo {
    public static void main(String[] args) {
        SplitwiseCatalog catalog = new SplitwiseCatalog();
        SplitwiseService service = new SplitwiseService(
                catalog,
                new SplitPolicyFactory(),
                new DebtSimplificationPolicyFactory()
        );

        // Stage 6: only wiring. Stage 7 onwards we will make these flows work.
        // Example placeholder calls (will return null now):
        String u1 = service.createUser("Ravi");
        String u2 = service.createUser("Amit");
        String u3 = service.createUser("Neha");

        String g1 = service.createGroup("Goa Trip", Arrays.asList(u1, u2, u3), DebtSimplifyMode.MIN_TRANSFERS);

        // addExpense / getBalances / settleUp will be implemented in Stage 7.
        System.out.println("Stage 6 shells compiled. Next: Stage 7A browse/list flows.");
    }
}