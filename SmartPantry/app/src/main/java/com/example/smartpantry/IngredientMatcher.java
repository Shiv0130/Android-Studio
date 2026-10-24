package com.example.smartpantry;

// Helpers so "tomato" matches "Tomatoes" and 1 kg matches 1000 g
public final class IngredientMatcher {

    private IngredientMatcher() { }

    // Lower-case, trim and make singular: tomatoes -> tomato, eggs -> egg
    public static String normalizeName(String name) {
        if (name == null) return "";
        String n = name.trim().toLowerCase();
        if (n.endsWith("oes")) return n.substring(0, n.length() - 2);   // tomatoes, potatoes
        if (n.endsWith("ies")) return n.substring(0, n.length() - 3) + "y"; // berries
        if (n.endsWith("s") && !n.endsWith("ss") && n.length() > 3)
            return n.substring(0, n.length() - 1);                         // eggs, onions
        return n;
    }

    // Unit family: g / ml / units / cups / tbsp ... (kg->g, l->ml)
    public static String baseUnit(String unit) {
        String u = unit == null ? "" : unit.trim().toLowerCase();
        switch (u) {
            case "": case "unit": case "units": case "pc": case "pcs": case "piece": case "pieces":
                return "units";
            case "g": case "gram": case "grams": case "kg": case "kilogram": case "kilograms":
                return "g";
            case "ml": case "l": case "litre": case "litres": case "liter": case "liters":
                return "ml";
            case "cup": case "cups":
                return "cups";
            case "tbsp": case "tablespoon": case "tablespoons":
                return "tbsp";
            case "tsp": case "teaspoon": case "teaspoons":
                return "tsp";
            default:
                return u;
        }
    }

    // Convert quantity into its base unit (1 kg -> 1000 g, 1 l -> 1000 ml)
    public static double toBase(double qty, String unit) {
        String u = unit == null ? "" : unit.trim().toLowerCase();
        if (u.startsWith("kg") || u.startsWith("kilogram")) return qty * 1000;
        if (u.equals("l") || u.startsWith("litre") || u.startsWith("liter")) return qty * 1000;
        return qty;
    }

    // True when the pantry item is the same ingredient with at least the needed amount
    public static boolean hasEnough(PantryItem p, RecipeIngredient need) {
        if (!normalizeName(p.getName()).equals(normalizeName(need.getName()))) return false;
        if (!baseUnit(p.getUnit()).equals(baseUnit(need.getUnit()))) return false;
        return toBase(p.getQuantity(), p.getUnit()) >= toBase(need.getQuantity(), need.getUnit());
    }
}
