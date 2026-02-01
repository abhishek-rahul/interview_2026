package lld.vendingmachine.orchestrator;

import java.util.Collections;
import java.util.List;


import lld.vendingmachine.domain.enums.PurchaseStatus;
import lld.vendingmachine.domain.VendingCatalog;
import lld.vendingmachine.domain.PurchaseResult;
import lld.vendingmachine.policy.ChangeMakingPolicy;

import lld.vendingmachine.domain.Transaction;

public class VendingMachineService {
    private final VendingCatalog catalog;
    private final ChangeMakingPolicy changePolicy;
    private final Transaction txn;

    public VendingMachineService(VendingCatalog catalog, ChangeMakingPolicy changePolicy, Transaction txn) {
        this.catalog = catalog;
        this.changePolicy = changePolicy;
        this.txn = txn;
    }

    // 7A
    public List<String> listItems() {
        return Collections.emptyList();
    }

    // 7B
    public String viewItem(String code) {
        return "";
    }

    public void selectItem(String code) {
        // no logic in Stage 6
    }

    public void insertMoney(int denomination) {
        // no logic in Stage 6
    }

    public int getInsertedAmount() {
        return 0;
    }

    // 7E
    public List<Integer> cancel() {
        return Collections.emptyList();
    }

    // 7C + 7D
    public PurchaseResult confirmPurchase() {
        return new PurchaseResult(PurchaseStatus.NO_SELECTION, null, Collections.emptyList(), "NOT_IMPLEMENTED");
    }
}
