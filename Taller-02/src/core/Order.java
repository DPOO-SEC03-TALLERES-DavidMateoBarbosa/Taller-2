package core;

import utils.Usable;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Order {
    private final ArrayList<Combo> combos;
    private final ArrayList<AdjustProduct> products;
    private final int ID;
    private final String clientName;
    private final String clientAddress;

    public Order(String clientName, String clientAddress){
        ID = (int) (Math.random()* 1_000_000_000);
        this.clientName = clientName;
        this.clientAddress = clientAddress;
        combos = new ArrayList<>();
        products = new ArrayList<>();
}

    public void addProduct(Combo combo){
        combos.add(combo);
    }
    public void addProduct(AdjustProduct product){
        products.add(product);
    }

    public ArrayList<AdjustProduct> getProducts(){
        return products;
    }

    public int getID(){
        return ID;
    }

    private int getNetPrice(){
        int price = 0;
        for (AdjustProduct product : products){
            price += product.getPrice();
        }
        for (Combo combo : combos){
            price += combo.getPrice();
        }
        return price;
    }

    private int getIVAPrice(){
        return (int) (getNetPrice()*.19f);
    }

    private int getTotalPrice(){
        return getNetPrice() + getIVAPrice();
    }

    private String createBill(){
        String products = "";
        for (AdjustProduct product: this.products){
            products = products.concat(product + "\n");
        }
        String combos = "";
        for (Combo combo: this.combos){
            combos = combos.concat(combo + "\n");
        }
        return  "Order ID: " + ID + "\n" +
                "client: " + clientName + "\n" +
                "client address: " + clientAddress + "\n" +
                products +
                combos +
                "price: " + getNetPrice() + "\n" +
                "IVA 19% : " + getIVAPrice()  + "\n" +
                "total: " + getTotalPrice();
    }

    public void saveBill(File file) throws IOException{
    	//Note: eclipse can't show create files by code, but it appears in the files
        Usable.Writer writer = new Usable.Writer(file);
        writer.writeByStringArray(createBill().split("\n"), true);
        writer.close();
    }

    @Override
    public String toString() {
        return createBill();
    }
}
