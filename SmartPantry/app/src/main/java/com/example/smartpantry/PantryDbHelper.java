package com.example.smartpantry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class PantryDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "smart_pantry.db";
    private static final int DB_VERSION = 2;  // v2 adds recipe tables

    public static final String TABLE = "pantry";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_QTY = "quantity";
    public static final String COL_UNIT = "unit";
    public static final String COL_EXPIRY = "expiry";

    // Recipe tables (seeded with 18 recipes on first run)
    public static final String T_RECIPES = "recipes";
    public static final String T_RECIPE_ING = "recipe_ingredients";

    public PantryDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
            "CREATE TABLE " + TABLE + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_NAME + " TEXT NOT NULL, " +
            COL_QTY + " REAL NOT NULL, " +
            COL_UNIT + " TEXT, " +
            COL_EXPIRY + " TEXT)"
        );
        createRecipeTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Keep the user's pantry, only rebuild the recipe tables
        db.execSQL("DROP TABLE IF EXISTS " + T_RECIPE_ING);
        db.execSQL("DROP TABLE IF EXISTS " + T_RECIPES);
        createRecipeTables(db);
    }

    // Create recipe + ingredient tables, then seed them
    private void createRecipeTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + T_RECIPES + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, instructions TEXT, prep INTEGER)");
        db.execSQL("CREATE TABLE " + T_RECIPE_ING + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "recipe_id INTEGER NOT NULL, name TEXT NOT NULL, " +
                "quantity REAL NOT NULL, unit TEXT, " +
                "FOREIGN KEY(recipe_id) REFERENCES " + T_RECIPES + "(_id))");
        seedRecipes(db);
    }

    // Insert one recipe and its ingredient lines
    private void insertRecipe(SQLiteDatabase db, String name, String instructions,
                              int prep, RecipeIngredient[] ings) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("instructions", instructions);
        cv.put("prep", prep);
        long id = db.insert(T_RECIPES, null, cv);
        for (RecipeIngredient i : ings) {
            ContentValues c = new ContentValues();
            c.put("recipe_id", id);
            c.put("name", i.getName());
            c.put("quantity", i.getQuantity());
            c.put("unit", i.getUnit());
            db.insert(T_RECIPE_ING, null, c);
        }
    }

    // 18 starter recipes (runs once, when the database is first created)
    private void seedRecipes(SQLiteDatabase db) {
        insertRecipe(db, "Tomato Rice Bowl",
                "1. Cook the rice.\n2. Chop tomatoes and onion.\n3. Fry onion, add tomatoes, stir in rice and season.",
                25,
                new RecipeIngredient[]{
                        new RecipeIngredient("tomato", 2, "units"),
                        new RecipeIngredient("rice", 1, "cups"),
                        new RecipeIngredient("onion", 1, "units")});
        insertRecipe(db, "Cheesy Scrambled Eggs",
                "1. Beat the eggs.\n2. Scramble in a pan on low heat.\n3. Stir in grated cheese until melted.",
                10,
                new RecipeIngredient[]{
                        new RecipeIngredient("egg", 3, "units"),
                        new RecipeIngredient("cheese", 50, "g")});
        insertRecipe(db, "Simple Omelette",
                "1. Beat the eggs with salt.\n2. Pour into a hot oiled pan.\n3. Fold and serve.",
                8,
                new RecipeIngredient[]{
                        new RecipeIngredient("egg", 2, "units")});
        insertRecipe(db, "Garlic Pasta",
                "1. Boil the pasta.\n2. Fry sliced garlic in olive oil.\n3. Toss pasta in the garlic oil.",
                20,
                new RecipeIngredient[]{
                        new RecipeIngredient("pasta", 200, "g"),
                        new RecipeIngredient("garlic", 3, "units"),
                        new RecipeIngredient("olive oil", 2, "tbsp")});
        insertRecipe(db, "Chicken Fried Rice",
                "1. Cook rice and let it cool.\n2. Fry diced chicken.\n3. Add rice, egg and onion and stir-fry.",
                30,
                new RecipeIngredient[]{
                        new RecipeIngredient("chicken", 300, "g"),
                        new RecipeIngredient("rice", 2, "cups"),
                        new RecipeIngredient("egg", 2, "units"),
                        new RecipeIngredient("onion", 1, "units")});
        insertRecipe(db, "Potato Wedges",
                "1. Cut potatoes into wedges.\n2. Toss with oil and salt.\n3. Bake at 200C for 35 minutes.",
                45,
                new RecipeIngredient[]{
                        new RecipeIngredient("potato", 4, "units"),
                        new RecipeIngredient("olive oil", 2, "tbsp")});
        insertRecipe(db, "Tomato Soup",
                "1. Fry onion and garlic.\n2. Add chopped tomatoes and water.\n3. Simmer 20 minutes and blend.",
                35,
                new RecipeIngredient[]{
                        new RecipeIngredient("tomato", 6, "units"),
                        new RecipeIngredient("onion", 1, "units"),
                        new RecipeIngredient("garlic", 2, "units")});
        insertRecipe(db, "French Toast",
                "1. Whisk eggs and milk.\n2. Dip the bread slices.\n3. Fry until golden.",
                15,
                new RecipeIngredient[]{
                        new RecipeIngredient("bread", 4, "units"),
                        new RecipeIngredient("egg", 2, "units"),
                        new RecipeIngredient("milk", 100, "ml")});
        insertRecipe(db, "Pancakes",
                "1. Mix flour, milk and eggs into a batter.\n2. Pour into a hot pan.\n3. Flip when bubbles form.",
                20,
                new RecipeIngredient[]{
                        new RecipeIngredient("flour", 200, "g"),
                        new RecipeIngredient("milk", 300, "ml"),
                        new RecipeIngredient("egg", 2, "units")});
        insertRecipe(db, "Cheese Toastie",
                "1. Butter the bread.\n2. Add sliced cheese between slices.\n3. Toast until crispy.",
                10,
                new RecipeIngredient[]{
                        new RecipeIngredient("bread", 2, "units"),
                        new RecipeIngredient("cheese", 60, "g"),
                        new RecipeIngredient("butter", 1, "tbsp")});
        insertRecipe(db, "Mince and Pasta",
                "1. Brown the mince with onion.\n2. Add chopped tomatoes and simmer.\n3. Serve over boiled pasta.",
                40,
                new RecipeIngredient[]{
                        new RecipeIngredient("beef mince", 500, "g"),
                        new RecipeIngredient("pasta", 250, "g"),
                        new RecipeIngredient("tomato", 3, "units"),
                        new RecipeIngredient("onion", 1, "units")});
        insertRecipe(db, "Vegetable Stir-Fry",
                "1. Slice carrots, pepper and onion.\n2. Stir-fry in oil.\n3. Season with soy sauce.",
                20,
                new RecipeIngredient[]{
                        new RecipeIngredient("carrot", 2, "units"),
                        new RecipeIngredient("green pepper", 1, "units"),
                        new RecipeIngredient("onion", 1, "units"),
                        new RecipeIngredient("soy sauce", 2, "tbsp")});
        insertRecipe(db, "Mashed Potatoes",
                "1. Boil peeled potatoes.\n2. Mash with butter and milk.\n3. Season to taste.",
                30,
                new RecipeIngredient[]{
                        new RecipeIngredient("potato", 5, "units"),
                        new RecipeIngredient("butter", 2, "tbsp"),
                        new RecipeIngredient("milk", 100, "ml")});
        insertRecipe(db, "Banana Smoothie",
                "1. Peel the bananas.\n2. Blend with milk and honey.\n3. Serve cold.",
                5,
                new RecipeIngredient[]{
                        new RecipeIngredient("banana", 2, "units"),
                        new RecipeIngredient("milk", 250, "ml"),
                        new RecipeIngredient("honey", 1, "tbsp")});
        insertRecipe(db, "Chicken Curry",
                "1. Fry onion, garlic and curry powder.\n2. Add chicken and tomatoes.\n3. Simmer 30 minutes, serve with rice.",
                50,
                new RecipeIngredient[]{
                        new RecipeIngredient("chicken", 500, "g"),
                        new RecipeIngredient("onion", 1, "units"),
                        new RecipeIngredient("tomato", 2, "units"),
                        new RecipeIngredient("curry powder", 2, "tbsp"),
                        new RecipeIngredient("rice", 2, "cups")});
        insertRecipe(db, "Egg Fried Rice",
                "1. Cook rice and let it cool.\n2. Scramble eggs in a pan.\n3. Add rice and soy sauce and fry.",
                20,
                new RecipeIngredient[]{
                        new RecipeIngredient("rice", 2, "cups"),
                        new RecipeIngredient("egg", 2, "units"),
                        new RecipeIngredient("soy sauce", 1, "tbsp")});
        insertRecipe(db, "Bean and Tomato Stew",
                "1. Fry onion.\n2. Add beans and chopped tomatoes.\n3. Simmer 15 minutes.",
                25,
                new RecipeIngredient[]{
                        new RecipeIngredient("baked beans", 400, "g"),
                        new RecipeIngredient("tomato", 2, "units"),
                        new RecipeIngredient("onion", 1, "units")});
        insertRecipe(db, "Carrot and Onion Soup",
                "1. Chop carrots and onion.\n2. Boil in stock for 20 minutes.\n3. Blend until smooth.",
                35,
                new RecipeIngredient[]{
                        new RecipeIngredient("carrot", 4, "units"),
                        new RecipeIngredient("onion", 1, "units"),
                        new RecipeIngredient("stock", 1, "l")});
    }

    // Read all recipes with their ingredients from SQLite
    public List<Recipe> getAllRecipes() {
        List<Recipe> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(T_RECIPES, null, null, null, null, null, "name ASC");
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndexOrThrow("_id"));
            List<RecipeIngredient> ings = new ArrayList<>();
            Cursor ic = db.query(T_RECIPE_ING, null, "recipe_id=?",
                    new String[]{String.valueOf(id)}, null, null, null);
            while (ic.moveToNext()) {
                ings.add(new RecipeIngredient(
                        ic.getString(ic.getColumnIndexOrThrow("name")),
                        ic.getDouble(ic.getColumnIndexOrThrow("quantity")),
                        ic.getString(ic.getColumnIndexOrThrow("unit"))));
            }
            ic.close();
            list.add(new Recipe(
                    c.getString(c.getColumnIndexOrThrow("name")),
                    c.getString(c.getColumnIndexOrThrow("instructions")),
                    c.getInt(c.getColumnIndexOrThrow("prep")),
                    ings));
        }
        c.close();
        return list;
    }

    public long insert(PantryItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, item.getName());
        cv.put(COL_QTY, item.getQuantity());
        cv.put(COL_UNIT, item.getUnit());
        cv.put(COL_EXPIRY, item.getExpiry());
        return db.insert(TABLE, null, cv);
    }

    public List<PantryItem> getAll() {
        List<PantryItem> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE, null, null, null, null, null, COL_NAME + " ASC");
        while (c.moveToNext()) {
            list.add(new PantryItem(
                c.getLong(c.getColumnIndexOrThrow(COL_ID)),
                c.getString(c.getColumnIndexOrThrow(COL_NAME)),
                c.getDouble(c.getColumnIndexOrThrow(COL_QTY)),
                c.getString(c.getColumnIndexOrThrow(COL_UNIT)),
                c.getString(c.getColumnIndexOrThrow(COL_EXPIRY))
            ));
        }
        c.close();
        return list;
    }

    public int delete(long id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE, COL_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int update(PantryItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, item.getName());
        cv.put(COL_QTY, item.getQuantity());
        cv.put(COL_UNIT, item.getUnit());
        cv.put(COL_EXPIRY, item.getExpiry());
        return db.update(TABLE, cv, COL_ID + "=?",
                new String[]{String.valueOf(item.getId())});
    }
}
