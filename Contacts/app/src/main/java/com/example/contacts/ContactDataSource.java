package com.example.contacts;// cpntact data source              package com.example.contacts;
import android.content.*;
import android.database.*;
import android.database.sqlite.SQLiteDatabase;

import com.example.contacts.ContactDBHelper;
import com.example.contacts.ContactList;

public class ContactDataSource {
    private SQLiteDatabase database;
    private ContactDBHelper dbHelper;

    // create three functions 1. contrunction data source 2. connection to database 3. close connection.

    public ContactDataSource(Context context){
        dbHelper = new ContactDBHelper(context);
    }
    public void open() throws SQLException{
        dbHelper = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }
}