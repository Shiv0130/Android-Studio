package com.example.smartpantry;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private final String name;
    private final List<RecipeIngredient> requiredIngredients;
    private final String instructions;
    private final int prepMinutes;

    public Recipe(String name, String instructions, int prepMinutes,
                  List<RecipeIngredient> ingredients) {
        this.name = name;
        this.instructions = instructions;
        this.prepMinutes = prepMinutes;
        this.requiredIngredients = new ArrayList<>(ingredients);
    }

    public String getName() { return name; }
    public List<RecipeIngredient> getRequiredIngredients() { return requiredIngredients; }
    public String getInstructions() { return instructions; }
    public int getPrepMinutes() { return prepMinutes; }

    // Strict match: EVERY ingredient must be in the pantry
    // AND the pantry must hold at least the required quantity
    public boolean matches(List<PantryItem> pantry) {
        for (RecipeIngredient need : requiredIngredients) {
            boolean enough = false;
            for (PantryItem p : pantry) {
                if (p.getName() != null
                        && p.getName().trim().toLowerCase().equals(need.getName())
                        && p.getQuantity() >= need.getQuantity()) {
                    enough = true;
                    break;
                }
            }
            if (!enough) return false;
        }
        return true;
    }

    // Small helper to build an ingredient list
    static List<RecipeIngredient> list(RecipeIngredient... items) {
        List<RecipeIngredient> l = new ArrayList<>();
        for (RecipeIngredient i : items) l.add(i);
        return l;
    }

    static RecipeIngredient ing(String name, double qty, String unit) {
        return new RecipeIngredient(name, qty, unit);
    }

    public static List<Recipe> catalog() {
        List<Recipe> list = new ArrayList<>();
        list.add(new Recipe("Tomato Rice Bowl",
                "1. Cook rice.\n2. Chop tomato and onion.\n3. Mix and season.",
                25, list(ing("tomato", 2, "units"), ing("rice", 1, "cups"), ing("onion", 1, "units"))));
        list.add(new Recipe("Cheesy Eggs",
                "1. Beat eggs.\n2. Scramble.\n3. Add cheese until melted.",
                10, list(ing("eggs", 3, "units"), ing("cheese", 50, "g"))));
        list.add(new Recipe("Simple Omelette",
                "1. Beat eggs.\n2. Cook in pan.\n3. Fold and serve.",
                8, list(ing("eggs", 2, "units"))));
        return list;
    }
}
