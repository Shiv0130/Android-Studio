package com.example.contacts;// db helper                          package com.example.contacts;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactDBHelper extends SQLiteOpenHelper {
   private static final String DATABASE_NAME = "contacts.db";
   private static final int DATABASE_VERSION = 1;

   private static final String CREATE_TABLE_CONTACTS = "CREATE table contact(id integer primary key autoincrement,"
           + "contactname text not null,"
           + "phonenumber text not null,"
           + "address text);";

   public ContactDBHelper(Context context) {
      super(context, DATABASE_NAME, null, DATABASE_VERSION);

   }
   @Override
   public void onCreate(SQLiteDatabase db){
      db.execSQL(CREATE_TABLE_CONTACTS);
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
      db.execSQL("DROP TABLE IF EXISTS contact");
      onCreate(db);
   }

}