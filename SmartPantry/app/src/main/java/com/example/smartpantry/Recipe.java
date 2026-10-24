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
    // (names are singular/plural safe, units are converted)
    public boolean matches(List<PantryItem> pantry) {
        for (RecipeIngredient need : requiredIngredients) {
            boolean enough = false;
            for (PantryItem p : pantry) {
                if (IngredientMatcher.hasEnough(p, need)) {
                    enough = true;
                    break;
                }
            }
            if (!enough) return false;
        }
        return true;
    }
}
