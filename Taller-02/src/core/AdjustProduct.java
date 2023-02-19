package core;

import utils.Product;

import java.util.ArrayList;

public class AdjustProduct implements Product {
    private final MenuProduct menuProduct;
    private final ArrayList<Ingredient> add;
    private final ArrayList<Ingredient> del;
    public AdjustProduct(MenuProduct menuProduct){
        this.menuProduct = menuProduct;
        add = new ArrayList<>();
        del = new ArrayList<>();
    }

    public void addIngredient(Ingredient ingredient){
        add.add(ingredient);
    }
    public ArrayList<Ingredient> getIngredients(){
        return add;
    }
    public void delIngredient(Ingredient ingredient){
        add.remove(ingredient);
        del.add(ingredient);
    }
    @Override
    public int getPrice() {
        int price = menuProduct.getPrice();
        for (Ingredient ingredient : add){
            price += ingredient.getPrice();
        }
        return price;
    }

    @Override
    public String getName() {
        return menuProduct.getName();
    }


    @Override
    public String createBill() {
        String name = menuProduct.getName();
        int price = menuProduct.getPrice();
        String bill = "";
        for (Ingredient ingredient : add){
            bill = bill.concat(name + ": " + price + "\n");
        }
        bill = bill.concat("total price: " + getPrice());
        return bill;
    }

    @Override
    public String toString() {
        String str = getName();
        for (Ingredient ingredient : add){
            str = str.concat(" +" + ingredient + ",");
        }
        for (Ingredient ingredient : del){
            str = str.concat(" -" + ingredient + ",");
        }
        if (str.endsWith(",")){
            str = str.substring(0, str.length() - 1);
        }
        str = str.concat(": $" + getPrice() );
        return str;
    }
}
