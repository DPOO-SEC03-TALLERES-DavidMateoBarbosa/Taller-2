package core;

import utils.Product;
import utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;

public class Combo implements Product {
    private final String name;
    private final double discount;
    private final ArrayList<MenuProduct> products;
    public Combo(String name, double discount){
        this.name = name;
        this.discount = 1 - discount;
        products = new ArrayList<>();
    }
    public void addItem(MenuProduct item){
        products.add(item);
    }
    @Override
    public int getPrice() {
        int price = 0;
        for (MenuProduct menuProduct : products){
            price += menuProduct.getPrice();
        }
        return (int) (price*discount);
    }
    @Override
    public String getName(){
        return name;
    }
    @Override
    public String createBill() {
        String final_bill = "";
        for (MenuProduct menuProduct : products){
            final_bill = final_bill.concat(menuProduct.getName() + ": " + menuProduct.getPrice() + "\n");
        }
        return final_bill + "total: " + getPrice();
    }

    @Override
    public String toString() {
        return name + "-> " + Arrays.toString(products.toArray()).replace("[", "{").replace("]", "}") + ": " + getPrice();
    }
}
