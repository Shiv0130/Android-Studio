package com.example.smartpantry;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private final String name;
    private final List<String> requiredIngredients;
    private final String instructions;
    private final int prepMinutes;

    public Recipe(String name, String instructions, int prepMinutes, String... ingredients) {
        this.name = name;
        this.instructions = instructions;
        this.prepMinutes = prepMinutes;
        this.requiredIngredients = new ArrayList<>();
        for (String i : ingredients) {
            this.requiredIngredients.add(i.trim().toLowerCase());
        }
    }

    public String getName() { return name; }
    public List<String> getRequiredIngredients() { return requiredIngredients; }
    public String getInstructions() { return instructions; }
    public int getPrepMinutes() { return prepMinutes; }

    public boolean matches(List<PantryItem> pantry) {
        for (String need : requiredIngredients) {
            boolean found = false;
            for (PantryItem p : pantry) {
                if (p.getName() != null
                        && p.getName().trim().toLowerCase().contains(need)) {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }

    public static List<Recipe> catalog() {
        List<Recipe> list = new ArrayList<>();
        list.add(new Recipe("Tomato Rice Bowl",
                "1. Cook rice.\n2. Chop tomato and onion.\n3. Mix and season.",
                25, "tomato", "rice", "onion"));
        list.add(new Recipe("Cheesy Eggs",
                "1. Beat eggs.\n2. Scramble.\n3. Add cheese until melted.",
                10, "eggs", "cheese"));
        list.add(new Recipe("Garlic Pasta",
                "1. Boil pasta.\n2. Fry garlic in oil.\n3. Toss pasta with garlic oil.",
                20, "pasta", "garlic", "olive oil"));
        list.add(new Recipe("Simple Omelette",
                "1. Beat eggs.\n2. Cook in pan.\n3. Fold and serve.",
                8, "eggs"));
        list.add(new Recipe("Chicken Rice",
                "1. Cook rice.\n2. Cook chicken.\n3. Serve together.",
                35, "chicken", "rice"));
        return list;
    }
}
