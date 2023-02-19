package core;

import utils.Product;

public class MenuProduct implements Product {
    private final String name;
    private final int basePrice;
    public MenuProduct(String name, int basePrice){
        this.name = name;
        this.basePrice = basePrice;
    }
    public String getName() {
        return name;
    }

    @Override
    public String createBill() {
        return basePrice + "";
    }

    @Override
    public String toString() {
        return name + ": $" + basePrice;
    }

    public int getPrice() {
        return basePrice;
    }
}
