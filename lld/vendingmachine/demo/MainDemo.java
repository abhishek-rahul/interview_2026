package lld.vendingmachine.demo;

import java.util.*;


import lld.vendingmachine.domain.MoneyInventory;
import lld.vendingmachine.domain.VendingCatalog;
import lld.vendingmachine.policy.ChangeMakingPolicy;
import lld.vendingmachine.policy.impl.GreedyChangeMakingPolicy;
import lld.vendingmachine.orchestrator.VendingMachineService;
import lld.vendingmachine.domain.Transaction;
import lld.vendingmachine.domain.PurchaseResult;
// =======================
// Stage 6: Shells Only
// =======================

public class MainDemo {
    public static void main(String[] args) {
        // ---- Wiring (no real logic yet) ----
        MoneyInventory cash = new MoneyInventory(new HashMap<>());
        VendingCatalog catalog = new VendingCatalog(new ArrayList<>(), cash);

        ChangeMakingPolicy changePolicy = new GreedyChangeMakingPolicy();
        Transaction txn = new Transaction();

        VendingMachineService service = new VendingMachineService(catalog, changePolicy, txn);

        // ---- Demo calls (will implement in Stage 7/8) ----
        System.out.println("LIST: " + service.listItems());
        System.out.println("VIEW A1: " + service.viewItem("A1"));

        service.selectItem("A1");
        service.insertMoney(10);
        System.out.println("INSERTED: " + service.getInsertedAmount());

        PurchaseResult result = service.confirmPurchase();
        System.out.println("CONFIRM: " + result);

        System.out.println("CANCEL REFUND: " + service.cancel());
    }
}