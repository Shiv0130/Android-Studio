package com.example.smartpantry;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
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
                android.content.Intent i = new android.content.Intent(
                        MainActivity.this, AddIngredientActivity.class);
                i.putExtra(AddIngredientActivity.EXTRA_ID, item.getId());
                startActivity(i);
            }

            @Override
            public void onLongClick(PantryItem item) {
                new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.btn_delete)
                        .setMessage(item.getName() + "?")
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
                startActivity(new android.content.Intent(this, AddIngredientActivity.class)));

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(item -> true);
    }

    private void seedIfEmpty() {
        if (dbHelper.getAll().isEmpty()) {
            dbHelper.insert(new PantryItem("Tomato", 3, "units", "2026-10-15"));
            dbHelper.insert(new PantryItem("Rice", 2, "cups", "2027-01-01"));
            dbHelper.insert(new PantryItem("Milk", 1, "litre", "2026-10-12"));
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
    }
}