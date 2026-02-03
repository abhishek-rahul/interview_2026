package lld.vendingmachine.orchestrator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lld.vendingmachine.domain.enums.PurchaseStatus;
import lld.vendingmachine.domain.VendingCatalog;
import lld.vendingmachine.domain.Item;
import lld.vendingmachine.domain.PurchaseResult;
import lld.vendingmachine.policy.ChangeMakingPolicy;
import lld.vendingmachine.policy.impl.ChangePlan;
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
        List<String> out = new ArrayList<>();
        for (Item it : catalog.allItems()) {
            out.add(it.summaryLine());
        }
        return out;
    }

    // 7B
    public String viewItem(String code) {
        Item it = catalog.findByCode(code);
        return it.summaryLine();
    }

    public void selectItem(String code) {
        // validate item exists (read-only)
        catalog.findByCode(code);

        // set selection + reset txn money
        txn.select(code);
    }

    public void insertMoney(int denomination) {
        if (denomination <= 0) {
            throw new IllegalArgumentException("Invalid denomination: " + denomination);
        }
        txn.addMoney(denomination);
    }

    public int getInsertedAmount() {
        return txn.totalInserted();
    }

    public List<Integer> cancel() {
        return txn.refundAllAndReset();
    }

    // 7C + 7D
    public PurchaseResult confirmPurchase() {
        // -------- Phase 1: Validate (no mutation) --------
        String code = txn.selectedCode();
        if (code == null) {
            return new PurchaseResult(PurchaseStatus.NO_SELECTION, null,
                    Collections.emptyList(), "No item selected");
        }

        Item item;
        try {
            item = catalog.findByCode(code);
        } catch (IllegalArgumentException e) {
            // selection got stale somehow
            return new PurchaseResult(PurchaseStatus.NO_SELECTION, null,
                    Collections.emptyList(), "Invalid selection");
        }

        if (!item.isInStock()) {
            return new PurchaseResult(PurchaseStatus.OUT_OF_STOCK, null,
                    Collections.emptyList(), "Item out of stock: " + code);
        }

        int inserted = txn.totalInserted();
        int price = item.price();

        if (inserted < price) {
            return new PurchaseResult(PurchaseStatus.INSUFFICIENT_FUNDS, null,
                    Collections.emptyList(), "Need " + (price - inserted) + " more");
        }

        int requiredChange = inserted - price;

        ChangePlan plan = changePolicy.planChange(requiredChange, catalog.cash());
        if (!plan.possible()) {
            return new PurchaseResult(PurchaseStatus.NO_CHANGE, null,
                    Collections.emptyList(), "Cannot return exact change: " + requiredChange);
        }

        // -------- Phase 2: Commit (all mutations together) --------
        // 1) decrement stock
        item.decrementStock();

        // 2) accept inserted money into machine cash
        for (int denom : txn.insertedDenoms()) {
            catalog.cash().add(denom, 1);
        }

        // 3) dispense change (remove from machine cash)
        for (int denom : plan.changeDenoms()) {
            catalog.cash().remove(denom, 1);
        }

        // 4) reset txn
        txn.reset();

        return new PurchaseResult(PurchaseStatus.SUCCESS, code,
                new ArrayList<>(plan.changeDenoms()),
                "Dispensed " + code + ", change=" + requiredChange);
    }

}
