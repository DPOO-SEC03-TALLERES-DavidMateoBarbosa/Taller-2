package gui;

import core.*;
import utils.Usable;
import utils.Utils;
import java.io.File;
import java.util.ArrayList;

public class Application extends Utils {
    private static boolean is_active = true;
    private static final Restaurant restaurant = new Restaurant();
    public static void main(String[] args){
        //NOTE: dots relative paths doesn't work in my code
        String path = "src/data/";
        File[] files = multiOpen(path,"ingredientes.txt", "menu.txt", "combos.txt", "bebidas.txt");
        restaurant.loadRestaurantInfo(files);
        while (is_active){
            menu();
            String input_option = input("Select an option: ");
            print();
            executeOption(input_option);
            print();
        }
    }
    private static void menu(){
        print("1. show menu.");
        print("2. start new order.");
        print("3. add new item to order.");
        print("4. save order.");
        print("5. search order by ID.");
        print("6. for exit.");
    }
    private static void executeOption(String option){
        switch (option) {
            case "1" -> showMenu();
            case "2" -> startNewOrder();
            case "3" -> addNewItemToOrder();
            case "4" -> saveOrder();
            case "5" -> searchOrderByID();
            case "6" -> is_active = false;
            default -> print("select a valid option.");
        }
    }
    private static void showMenu(){
        ArrayList<MenuProduct> menu = restaurant.getProducts();
        print("========= Menu 1 =======");
        print("=== Products ===");
        int public_index = 1;
        for (MenuProduct product : menu) {
            print(public_index + ": " + product);
            public_index++;
        }
        ArrayList<Drink> drinks = restaurant.getDrinks();
        print("==== Drinks ====");
        for (Drink drink : drinks) {
            print(public_index + ": " + drink);
            public_index++;
        }
        print();
        ArrayList<Combo> combos = restaurant.getCombos();
        print("======== Combos 2 ========");
        for (int index = 0; index < combos.size(); index ++){
            print((index + 1) + ": " + combos.get(index));
        }
        print();
    }
    private static void startNewOrder(){
        if (restaurant.getCurrentOrder() == null){
            String clientName = input("Client name: ");
            String clientAddress = input("Client address: ");
            restaurant.startOrder(clientName,clientAddress);
            print("your order ID is: " + restaurant.getCurrentOrder().getID());
        }
        else print("order in progress");

    }
    private static void addNewItemToOrder(){
        if (restaurant.getCurrentOrder() != null){
            // Example '1.1'
            print("1. add new product or combo");
            print("2. add new ingredient to product");
            print("3. del ingredient from product");
            String option = input("select: ");
            switch (option) {
                case "1" -> addProduct();
                case "2" -> addIngredientToProduct();
                case "3" -> delIngredientToProduct();
                default -> {
                    print("select a valid option");
                    addNewItemToOrder();
                }
            }
        }
        else print("no current orders to add items");

    }
    private static void addProduct(){
        String[] chose = input("type the number of section followed by a dot and the number of the item: ").split("\\.");
        int section = toInt(chose[0]);
        int item = toInt(chose[1]);
        switch (section) {
            case 1 -> {
                if (item < restaurant.getProducts().size()) restaurant.getCurrentOrder().addProduct(new AdjustProduct(restaurant.getProducts().get(item-1)));
                else restaurant.getCurrentOrder().addProduct(new AdjustProduct(restaurant.getDrinks().get(item - restaurant.getProducts().size())));
            }
            case 2 -> restaurant.getCurrentOrder().addProduct(restaurant.getCombos().get(item-1));
        }
    }
    public static void addIngredientToProduct(){
        print("======= Ingredients =======");
        ArrayList<Ingredient> ingredients = restaurant.getIngredients();
        for (int index = 0; index < ingredients.size(); index ++){
            print((index + 1) + ": " + ingredients.get(index));
        }
        print();
        print("your products: ");
        ArrayList<AdjustProduct> products = restaurant.getCurrentOrder().getProducts();
        for (int index = 0; index < products.size(); index ++){
            print((index + 1) + ": " + products.get(index));
        }
        int chose_product = toInt(input("type the number of the product: "));
        int chose = toInt(input("type the number of ingredient: "));
        products.get(chose_product - 1).addIngredient(restaurant.getIngredients().get(chose - 1));
    }

    private static void delIngredientToProduct(){
        print("======= Ingredients =======");
        ArrayList<Ingredient> ingredients = restaurant.getIngredients();
        for (int index = 0; index < ingredients.size(); index ++){
            print((index + 1) + ": " + ingredients.get(index));
        }
        print();
        print("your products: ");
        ArrayList<AdjustProduct> products = restaurant.getCurrentOrder().getProducts();
        for (int index = 0; index < products.size(); index ++){
            print((index + 1) + ": " + products.get(index));
        }
        int chose_product = toInt(input("type the number of the product: "));
        int chose = toInt(input("type the number of ingredient: "));
        if (products.get(chose_product - 1).getIngredients().contains(ingredients.get(chose - 1))){
            products.get(chose_product - 1).delIngredient(ingredients.get(chose - 1));
        }
    }

    private static void saveOrder(){

        if (restaurant.getCurrentOrder() != null){
            print(restaurant.getCurrentOrder() + "");
            restaurant.closeAndSaveOrder();
        }
        else print("not orders in progress");
    }
    private static void searchOrderByID(){
        try{
            int id = toInt(input("Search ID: "));
            if (restaurant.orders().containsKey(id)) {
                print();
                if (restaurant.orders().containsKey(id)) print(restaurant.orders().get(id) + "");
                else print("enter a valid Id");
            }
            else {
                Usable.Reader reader = new Usable.Reader("src/orders/ORDER_" + id + ".txt");
                print();
                for (String line: reader){
                    print(line);
                }
            }
        }catch (Exception ignored){
            print();
            print("Not id order found.");
        }
    }
}
