package com.example.smartpantry;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_INSTRUCTIONS = "instructions";
    public static final String EXTRA_PREP = "prep";
    public static final String EXTRA_INGREDIENTS = "ingredients";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        setTitle(R.string.title_recipe_detail);

        String name = getIntent().getStringExtra(EXTRA_NAME);
        String instructions = getIntent().getStringExtra(EXTRA_INSTRUCTIONS);
        int prep = getIntent().getIntExtra(EXTRA_PREP, 0);
        String ingredients = getIntent().getStringExtra(EXTRA_INGREDIENTS);

        ((TextView) findViewById(R.id.text_detail_name)).setText(name);
        ((TextView) findViewById(R.id.text_detail_prep))
                .setText(getString(R.string.label_prep_minutes, prep));
        ((TextView) findViewById(R.id.text_detail_ingredients))
                .setText(getString(R.string.label_needs_list, ingredients));
        ((TextView) findViewById(R.id.text_detail_instructions)).setText(instructions);
    }
}
