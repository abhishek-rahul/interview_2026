package lld.splitwise.demo;

import java.util.Arrays;

import lld.splitwise.domain.SplitwiseCatalog;
import lld.splitwise.domain.enums.DebtSimplifyMode;
import lld.splitwise.policy.DebtSimplificationPolicyFactory;
import lld.splitwise.policy.SplitPolicyFactory;
import lld.splitwise.orchestrator.SplitwiseService;

public class MainDemo {
    public static void main(String[] args) {
        SplitwiseCatalog catalog = new SplitwiseCatalog();
        SplitwiseService service = new SplitwiseService(
                catalog,
                new SplitPolicyFactory(),
                new DebtSimplificationPolicyFactory());

        // Stage 6: only wiring. Stage 7 onwards we will make these flows work.
        // Example placeholder calls (will return null now):
        String u1 = service.createUser("Ravi");
        String u2 = service.createUser("Amit");
        String u3 = service.createUser("Neha");

        System.out.println("Created users: " + u1 + ", " + u2 + ", " + u3);

        String g1 = service.createGroup("Goa Trip", Arrays.asList(u1, u2, u3), DebtSimplifyMode.MIN_TRANSFERS);
        System.out.println("Group created: " + g1);
    }
}