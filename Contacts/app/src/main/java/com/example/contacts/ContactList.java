package com.example.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contacts.MainActivity;
import com.example.contacts.R;

public class ContactList extends AppCompatActivity {

    //Declare Java components
    private RecyclerView recyclerViewContacts;
    private Button saveContact, backButton, homeButton, settingsButton, newContactBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        //Initialize Widgets
        recyclerViewContacts = findViewById(R.id.recyclerContactView);

        settingsButton =findViewById(R.id.settings);
        newContactBtn = findViewById(R.id.newContact);
        homeButton = findViewById(R.id.home);

        //Setting-Up the recycler view
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));

        //TODO: Bind a custom Adaptor to the recyclerViewContacts with the contacts data array list


        //Navbar Logic
        settingsButton.setOnClickListener(v -> {
            Toast.makeText(this, "This  button takes you to the settings section.", Toast.LENGTH_SHORT).show();
        });

        homeButton.setOnClickListener(v -> {
            Toast.makeText(this, "Already on the Home (view contact) section", Toast.LENGTH_SHORT).show();
        });

        newContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactList.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
