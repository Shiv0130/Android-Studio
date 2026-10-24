package com.example.smartpantry;

// One ingredient line of a recipe, e.g. 2 units tomato
public class RecipeIngredient {
    private final String name;
    private final double quantity;
    private final String unit;

    public RecipeIngredient(String name, double quantity, String unit) {
        this.name = name.trim().toLowerCase();
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getName() { return name; }
    public double getQuantity() { return quantity; }
    public String getUnit() { return unit; }

    // Readable text for the detail screen, e.g. "2 units tomato"
    @Override
    public String toString() {
        double q = quantity;
        String qs = (q == Math.floor(q)) ? String.valueOf((long) q) : String.valueOf(q);
        return qs + " " + unit + " " + name;
    }
}
