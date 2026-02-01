package lld.vendingmachine.demo;

import java.util.*;

import lld.vendingmachine.domain.Item;
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
        List<Item> items = new ArrayList<>();
        items.add(new Item("A1", "Chips", 20, 5));
        items.add(new Item("B2", "Coke", 40, 2));
        items.add(new Item("C3", "Water", 10, 0)); // out of stock dikhane ke liye

        MoneyInventory cash = new MoneyInventory(new HashMap<>());
        VendingCatalog catalog = new VendingCatalog(items, cash);

        ChangeMakingPolicy changePolicy = new GreedyChangeMakingPolicy();
        Transaction txn = new Transaction();

        VendingMachineService service = new VendingMachineService(catalog, changePolicy, txn);

        // ---- Demo calls (will implement in Stage 7/8) ----

        System.out.println("\n\n ---- List Items ---- \n\n ");
        System.out.println("LIST: " + service.listItems());
        System.out.println("LIST: \n" + service.listItems());
        System.out.println("\n\n ---- End of List Items ---- \n\n ");


        System.out.println("\n\n ---- View Item ---- \n\n ");
        System.out.println("VIEW A1: " + service.viewItem("A1"));
        //System.out.println("VIEW A1: " + service.viewItem("A5"));
        System.out.println("\n\n ---- End of View Items ---- \n\n ");
        /* 
        System.out.println("VIEW A1: " + service.viewItem("A1"));

        service.selectItem("A1");
        service.insertMoney(10);
        System.out.println("INSERTED: " + service.getInsertedAmount());

        PurchaseResult result = service.confirmPurchase();
        System.out.println("CONFIRM: " + result);

        System.out.println("CANCEL REFUND: " + service.cancel());
        */
    }
}