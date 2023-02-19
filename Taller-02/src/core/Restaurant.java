package core;

import utils.Usable;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class Restaurant extends Utils{
    private final HashMap<String, MenuProduct> menuProducts;
    private final HashMap<String, Ingredient> ingredients;
    private final HashMap<Integer, Order> orders;
    private final HashMap<String, Combo> combos;
    private final HashMap<String, Drink> drinks;
    private Order currentOrder;

    public Restaurant(){
        menuProducts = new HashMap<>();
        ingredients = new HashMap<>();
        orders = new HashMap<>();
        combos = new HashMap<>();
        drinks = new HashMap<>();
        currentOrder = null;
    }
    public void startOrder(String clientName, String clientAddress){
        if (currentOrder == null){
            currentOrder = new Order(clientName, clientAddress);
        }
    }
    public Order getCurrentOrder(){
        return currentOrder;
    }
    public void closeAndSaveOrder(){
        if (currentOrder != null){
            orders.put(currentOrder.getID(), currentOrder);
            try{
                currentOrder.saveBill(new File("src/orders/ORDER_"+ currentOrder.getID() + ".txt"));
            }
            catch (IOException ignored){
            	ignored.printStackTrace();
            }

            currentOrder = null;
        }
    }
    public ArrayList<Drink> getDrinks(){
        return new ArrayList<>(drinks.values());
    }
    public ArrayList<Combo> getCombos() {
        return new ArrayList<>(combos.values());
    }
    public HashMap<Integer, Order> orders(){
        return orders;
    }
    public ArrayList<Order> getOrders(){
        return new ArrayList<>(orders.values());
    }
    public ArrayList<Ingredient> getIngredients(){
        return new ArrayList<>(ingredients.values());
    }
    public ArrayList<MenuProduct> getProducts(){
        return new ArrayList<>(menuProducts.values());
    }
    public void loadRestaurantInfo(File ... files){
        try{
            loadIngredients(files[0]);
            loadMenu(files[1]);
            loadDrinks(files[3]);
            loadCombos(files[2]);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }



    public void loadRestaurantInfo(File ingredientsFile, File menuFile, File combosFile, File drinksFile){
        try{
            loadIngredients(ingredientsFile);
            loadMenu(menuFile);
            loadDrinks(drinksFile);
            loadCombos(combosFile);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void loadIngredients(File ingredientsFile) throws IOException{
        Usable.Reader ingredientsReader = new Usable.Reader(ingredientsFile);
        for (String line: ingredientsReader){
            String[] string_ingredient = line.split(";");
            Ingredient ingredient = new Ingredient(string_ingredient[0], toInt(string_ingredient[1]));
            ingredients.put(ingredient.getName(), ingredient);
        }
        ingredientsReader.close();
    }
    private void loadMenu(File menuFile) throws IOException{
        Usable.Reader menuReader = new Usable.Reader(menuFile);
        for (String line : menuReader){
            String[] string_product = line.split(";");
            MenuProduct product = new MenuProduct(string_product[0], toInt(string_product[1]));
            menuProducts.put(product.getName(), product);
        }
        menuReader.close();
    }
    private void loadCombos(File combosFile) throws IOException {
        Usable.Reader comboReader = new Usable.Reader(combosFile);
        for (String line : comboReader){
            String[] string_combo = line.split(";");
            Combo combo = new Combo(string_combo[0], toDouble((string_combo[1].replace("%","")))/100);
            combo.addItem(menuProducts.get(string_combo[2]));
            combo.addItem(menuProducts.get(string_combo[3]));
            combo.addItem(drinks.get(string_combo[4]));
            combos.put(combo.getName(), combo);
        }
        comboReader.close();
    }
    private void loadDrinks(File drinksFile) throws IOException{
        Usable.Reader drinksReader = new Usable.Reader(drinksFile);
        for (String line: drinksReader){
            String[] string_drink = line.split(";");
            Drink drink = new Drink(string_drink[0], toInt(string_drink[1]));
            drinks.put(drink.getName(), drink);
        }
    }


}
