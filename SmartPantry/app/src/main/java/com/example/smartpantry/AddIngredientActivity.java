package com.example.smartpantry;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddIngredientActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "extra_id";

    private EditText editName, editQty, editUnit, editExpiry;
    private PantryDbHelper dbHelper;
    private long editId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        dbHelper = new PantryDbHelper(this);
        editName = findViewById(R.id.edit_name);
        editQty = findViewById(R.id.edit_quantity);
        editUnit = findViewById(R.id.edit_unit);
        editExpiry = findViewById(R.id.edit_expiry);
        Button btnSave = findViewById(R.id.btn_save);

        editId = getIntent().getLongExtra(EXTRA_ID, -1);
        if (editId != -1) {
            setTitle(R.string.title_edit_ingredient);
            for (PantryItem item : dbHelper.getAll()) {
                if (item.getId() == editId) {
                    editName.setText(item.getName());
                    editQty.setText(String.valueOf(item.getQuantity()));
                    editUnit.setText(item.getUnit());
                    editExpiry.setText(item.getExpiry());
                    break;
                }
            }
        } else {
            setTitle(R.string.title_add_ingredient);
        }

        btnSave.setOnClickListener(v -> saveItem());
    }

    private void saveItem() {
        String name = editName.getText().toString().trim();
        String qtyStr = editQty.getText().toString().trim();
        String unit = editUnit.getText().toString().trim();
        String expiry = editExpiry.getText().toString().trim();

        if (name.isEmpty() || qtyStr.isEmpty()) {
            Toast.makeText(this, R.string.error_required, Toast.LENGTH_SHORT).show();
            return;
        }

        double qty;
        try {
            qty = Double.parseDouble(qtyStr);
            if (qty <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.error_invalid_qty, Toast.LENGTH_SHORT).show();
            return;
        }

        if (unit.isEmpty()) {
            unit = "units";
        }
        if (expiry.isEmpty()) {
            expiry = "-";
        }

        PantryItem item = new PantryItem(editId, name, qty, unit, expiry);
        if (editId == -1) {
            dbHelper.insert(item);
        } else {
            dbHelper.update(item);
        }
        finish();
    }
}
