package core;

public class Ingredient {
    private final String name;
    private final int additionalCost;
    public Ingredient(String name, int additionalCost){
        this.name = name;
        this.additionalCost = additionalCost;
    }
    public String getName() {
        return name;
    }
    public int getPrice() {
        return additionalCost;
    }

    @Override
    public String toString() {
        return name + ": $" + additionalCost;
    }
}
