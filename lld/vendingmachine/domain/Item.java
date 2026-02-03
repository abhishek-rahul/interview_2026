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

    public String code() {
        return code;
    }

    public String name() {
        return name;
    }

    public int price() {
        return price;
    }

    public int stock() {
        return stock;
    }

    public boolean isInStock() {
        return stock > 0;
    }

    public void decrementStock() {
        if (stock <= 0) {
            throw new IllegalStateException("Out of stock for item: " + code);
        }
        stock--;
    }

    public void setPrice(int newPrice) {
        if (newPrice <= 0) {
            throw new IllegalArgumentException("Price must be > 0");
        }
        this.price = newPrice;
    }

    public String summaryLine() {
        return code + " | " + name + " | price=" + price + " | stock=" + stock;
    }
}
