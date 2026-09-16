package com.example.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contacts.ContactList;
import com.example.contacts.R;

public class MainActivity extends AppCompatActivity {

    private EditText contactName, contactNum, contactAddress;
    private Button saveContact, backButton, homeButton, settingsButton, newContactBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing the Widgets
        contactName = findViewById(R.id.contactName);
        contactNum = findViewById(R.id.contactNumber);
        contactAddress = findViewById(R.id.contactAddress);

        saveContact = findViewById(R.id.saveContact);
        backButton = findViewById(R.id.backButton);

        settingsButton =findViewById(R.id.settings);
        newContactBtn = findViewById(R.id.newContact);
        homeButton = findViewById(R.id.home);

        //Save Button logic
        saveContact.setOnClickListener(v ->{
            String name = contactName.getText().toString().trim();
            String number = contactNum.getText().toString().trim();
            String address = contactAddress.getText().toString().trim();

            if(name.isEmpty() || number.isEmpty()){
                Toast.makeText(this, "Please enter a name and cell number", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Contact saved successfully!", Toast.LENGTH_SHORT).show();

                // Navigates back to the list view after saving
                Intent intent = new Intent(MainActivity.this, ContactList.class);
                startActivity(intent);
                finish();
            }
        });

        //Back button logic
        backButton.setOnClickListener(v -> finish());


        //Nav bar sample logic
        settingsButton.setOnClickListener(v -> {
            Toast.makeText(this, "This  button takes you to the settings section.", Toast.LENGTH_SHORT).show();
        });

        homeButton.setOnClickListener(new View.OnClickListener() { //Takes you to the view contact section
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        newContactBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Already on the Add contact section", Toast.LENGTH_SHORT).show();
        });
    }
}
