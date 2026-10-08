package com.example.contacts;// cpntact data source              package com.example.contacts;
import android.content.*;
import android.database.*;
import android.database.sqlite.SQLiteDatabase;

import com.example.contacts.ContactDBHelper;
import com.example.contacts.ContactList;

import java.util.ArrayList;

public class ContactDataSource {
    private SQLiteDatabase database;
    private ContactDBHelper dbHelper;

    // create three functions 1. contrunction data source 2. connection to database 3. close connection.

    public ContactDataSource(Context context){

        dbHelper = new ContactDBHelper(context);
    }
    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public boolean insertContact(Contact c){
        boolean didSucceed = false;

        try{
            ContentValues values = new ContentValues();
            values.put("fullname", c.getContactName());
            values.put("cellphoneNumber",c.getPhoneNumber());
            values.put("address",c.getAddress());

            didSucceed = database.insert("contact",null,values) > 0;

        }catch(Exception e){
            return didSucceed;
        }
        return didSucceed;
    }

    public ArrayList<Contact> getContacts(){
        ArrayList<Contact> contacts = new ArrayList<>();

        String query = "SELECT * FROM contact";

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
        Contact contact = new Contact();
        contact.setContactID(cursor.getInt(0));
        contact.setContactName(cursor.getString(1));
        contact.setPhoneNumber(cursor.getString(2));
        contact.setAddress(cursor.getString(3));
        contacts.add(contact);

        cursor.moveToNext();
        }
        cursor.close();
        return contacts;
    }



}
