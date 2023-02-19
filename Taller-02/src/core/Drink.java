package core;

import utils.Product;

public class Drink extends MenuProduct implements Product {
    public Drink(String name, int basePrice) {
        super(name, basePrice);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
