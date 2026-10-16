package com.example.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        setTitle(R.string.title_recipes);

        TextView empty = findViewById(R.id.text_empty_recipes);
        RecyclerView recycler = findViewById(R.id.recycler_recipes);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        RecipeAdapter adapter = new RecipeAdapter();
        recycler.setAdapter(adapter);

        adapter.setListener(recipe -> {
            Intent i = new Intent(this, RecipeDetailActivity.class);
            i.putExtra(RecipeDetailActivity.EXTRA_NAME, recipe.getName());
            i.putExtra(RecipeDetailActivity.EXTRA_INSTRUCTIONS, recipe.getInstructions());
            i.putExtra(RecipeDetailActivity.EXTRA_PREP, recipe.getPrepMinutes());
            i.putExtra(RecipeDetailActivity.EXTRA_INGREDIENTS,
                    recipe.getRequiredIngredients().toString());
            startActivity(i);
        });

        List<PantryItem> pantry = new PantryDbHelper(this).getAll();
        List<Recipe> matched = new ArrayList<>();
        for (Recipe r : Recipe.catalog()) {
            if (r.matches(pantry)) matched.add(r);
        }

        adapter.setItems(matched);
        empty.setVisibility(matched.isEmpty() ? View.VISIBLE : View.GONE);
        recycler.setVisibility(matched.isEmpty() ? View.GONE : View.VISIBLE);
    }
}