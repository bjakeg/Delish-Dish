package com.parse.starter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 3/15/16.
 */
public class PantryDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "PantryDB.db";
    public static final String PANTRY_TABLE_NAME = "pantry";
    public static final String PANTRY_COLUMN_ID =  "id";
    public static final String PANTRY_COLUMN_TITLE = "title";
    public static final String PANTRY_COLUMN_AMOUNT = "amount";
    public static final String PANTRY_COLUMN_QUANTITY = "quantity";

    public PantryDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table pantry" +
                        "(id integer primary key, title text, amount text, quantity text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PANTRY_TABLE_NAME);
        onCreate(db);
    }

    public void insertIngredient(Ingredient ingredient){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(PANTRY_COLUMN_TITLE, ingredient.getTitle());
        contentValues.put(PANTRY_COLUMN_AMOUNT, ingredient.getAmount());
        contentValues.put(PANTRY_COLUMN_QUANTITY, ingredient.getQuantity());

        List<Ingredient> currentList = getAllIngredients();

        for (int i = 0; i < currentList.size(); i++) {
            if (currentList.get(i).getTitle().equals(ingredient.getTitle())) {
                return;
            }
        }

        db.insert(PANTRY_TABLE_NAME, null, contentValues);
    }


    public ArrayList<Ingredient> getAllIngredients() {
        ArrayList<Ingredient> ingredientList = new ArrayList<Ingredient>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + PANTRY_TABLE_NAME, null);
        res.moveToFirst();

        while(res.isAfterLast() == false) {
            String title = res.getString(res.getColumnIndex(PANTRY_COLUMN_TITLE));
            String amount = res.getString(res.getColumnIndex(PANTRY_COLUMN_AMOUNT));
            String quantity = res.getString(res.getColumnIndex(PANTRY_COLUMN_QUANTITY));


            Ingredient newIngredient = new Ingredient(title, amount, Integer.parseInt(quantity));

            ingredientList.add(newIngredient);
            res.moveToNext();
        }

        return ingredientList;
    }

    public Integer deleteIngredient (String title)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PANTRY_TABLE_NAME,
                "title = ?",
                new String[] { title});
    }
}
