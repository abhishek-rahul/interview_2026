package lld.vendingmachine.domain;

public class Item {
    private final String code;
    private final String name;
    private int price;
    private int stock;

    public Item(String code, String name, int price, int stock) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String code() { return code; }
    public String name() { return name; }
    public int price() { return price; }
    public int stock() { return stock; }

    public boolean isInStock() {
        return false;
    }

    public void decrementStock() {
        // no logic in Stage 6
    }

    public void setPrice(int newPrice) {
        // no logic in Stage 6
    }

    public String summaryLine() {
        return code + " | " + name + " | price=" + price + " | stock=" + stock;
    }
}
