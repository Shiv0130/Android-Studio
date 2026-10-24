package com.example.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PantryAdapter adapter;
    private PantryDbHelper dbHelper;
    private View textEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setTitle(R.string.title_pantry);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new PantryDbHelper(this);
        textEmpty = findViewById(R.id.text_empty);

        RecyclerView recycler = findViewById(R.id.recycler_pantry);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PantryAdapter();
        recycler.setAdapter(adapter);

        adapter.setListener(new PantryAdapter.Listener() {
            @Override
            public void onClick(PantryItem item) {
                Intent i = new Intent(MainActivity.this, AddIngredientActivity.class);
                i.putExtra(AddIngredientActivity.EXTRA_ID, item.getId());
                startActivity(i);
            }

            @Override
            public void onLongClick(PantryItem item) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.btn_delete)
                        .setMessage(getString(R.string.delete_item_confirm, item.getName()))
                        .setPositiveButton(R.string.btn_delete, (d, w) -> {
                            dbHelper.delete(item.getId());
                            refreshList();
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
            }
        });

        seedIfEmpty();
        refreshList();

        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(v ->
                startActivity(new Intent(this, AddIngredientActivity.class)));

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_pantry);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_recipes) {
                startActivity(new Intent(this, RecipesActivity.class));
                return true;
            }
            if (id == R.id.nav_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            }
            return true;
        });
    }

    private void seedIfEmpty() {
        if (dbHelper.getAll().isEmpty()) {
            dbHelper.insert(new PantryItem("Tomato", 3, "units", "2026-10-25"));
            dbHelper.insert(new PantryItem("Rice", 2, "cups", "2027-01-01"));
            dbHelper.insert(new PantryItem("Eggs", 6, "units", "2026-10-28"));
        }
    }

    private void refreshList() {
        List<PantryItem> items = dbHelper.getAll();
        adapter.setItems(items);
        textEmpty.setVisibility(items.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dbHelper != null) {
            refreshList();
        }
        // Coming back from Recipes/Settings: highlight Pantry tab again
        BottomNavigationView nav = findViewById(R.id.bottom_nav);
        if (nav != null) nav.getMenu().findItem(R.id.nav_pantry).setChecked(true);
    }
}
