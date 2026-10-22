package com.example.smartpantry;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS = "smart_pantry_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(R.string.title_settings);

        EditText editName = findViewById(R.id.edit_display_name);
        CheckBox checkNotif = findViewById(R.id.check_notifications);
        Button btnSave = findViewById(R.id.btn_save_settings);

        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        editName.setText(prefs.getString("display_name", ""));
        checkNotif.setChecked(prefs.getBoolean("notifications", true));

        btnSave.setOnClickListener(v -> {
            prefs.edit()
                    .putString("display_name", editName.getText().toString().trim())
                    .putBoolean("notifications", checkNotif.isChecked())
                    .apply();
            Toast.makeText(this, R.string.settings_saved, Toast.LENGTH_SHORT).show();
        });
    }
}
