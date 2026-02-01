package lld.vendingmachine.domain;

import java.util.List;

//import lld.vendingmachine.domain.Item;
//import lld.vendingmachine.domain.MoneyInventory;

public class VendingCatalog {
    private final List<Item> items;
    private final MoneyInventory cash;

    public VendingCatalog(List<Item> items, MoneyInventory cash) {
        this.items = items;
        this.cash = cash;
    }

    public List<Item> allItems() {
        return items;
    }

    public Item findByCode(String code) {
        return null;
    }

    public MoneyInventory cash() {
        return cash;
    }
}