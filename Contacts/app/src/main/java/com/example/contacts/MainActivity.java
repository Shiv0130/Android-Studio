package com.example.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Java elements
    private EditText contactName,contactNumber,contactAddress;

    private Button saveContact,backButton,homeBtn,settingsBtn,newContactBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind XML widgets to Java Elements
        contactName = findViewById(R.id.contactName);
        contactNumber = findViewById(R.id.contactNumber);
        contactAddress = findViewById(R.id.contactAddress);

        saveContact = findViewById(R.id.saveContact);
        backButton = findViewById(R.id.backButton);

        homeBtn = findViewById(R.id.home);
        settingsBtn = findViewById(R.id.settings);
        newContactBtn = findViewById(R.id.newContact);


        saveContact.setOnClickListener(v->{
            String name = contactName.getText().toString().trim();
            String number = contactNumber.getText().toString().trim();
            String address = contactAddress.getText().toString().trim();

            if(name.isEmpty() || number.isEmpty()){
                Toast.makeText(this, "please enter the contact name and cellphone number",Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this,"Contact has been successfully saved",Toast.LENGTH_SHORT).show();
                //TODO: ADD DB for storing contact (Persistent Storage)

                Intent intent = new Intent(MainActivity.this,ContactList.class);
                startActivity(intent);
                finish();
            }
        });

        backButton.setOnClickListener(v->finish());

        //Nav Buttons
        settingsBtn.setOnClickListener(v->{
            Toast.makeText(this,"Takes you to the settings section",Toast.LENGTH_SHORT).show();
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
    }
}