package com.parse.starter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jakegraham on 3/7/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Recipes.db";
    public static final String RECIPES_TABLE_NAME = "recipes";
    public static final String RECIPES_COLUMN_ID =  "id";
    public static final String RECIPES_COLUMN_TITLE = "title";
    public static final String RECIPES_COLUMN_INGREDIENTS = "ingredients";
    public static final String RECIPES_COLUMN_INSTRUCTIONS = "instructions";
    public static final String RECIPES_COLUMN_IMAGE_LINK = "image_link";
    public static final String RECIPES_COLUMN_CATEGORY = "category";
    public static final String RECIPES_COLUMN_IMAGE = "image";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table recipes" +
                        "(id integer primary key, title text, ingredients text, instructions text, image_link text, category text, image blob)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RECIPES_TABLE_NAME);
        onCreate(db);
    }

    public void insertRecipe (Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RECIPES_COLUMN_TITLE, recipe.getTitle());
        contentValues.put(RECIPES_COLUMN_CATEGORY, recipe.getCategory());
        contentValues.put(RECIPES_COLUMN_IMAGE_LINK, recipe.getImageLink());

        List<Ingredient> ingredientList = recipe.getIngredients();
        String ingredients = "";
        for (int i = 0; i < ingredientList.size(); i++) {
            ingredients += ingredientList.get(i).getQuantity() + "\t" +
                           ingredientList.get(i).getTitle() + "\t" +
                           ingredientList.get(i).getAmount();
            if (i < ingredientList.size() - 1) {
                ingredients += ";";
            }
        }

        contentValues.put(RECIPES_COLUMN_INGREDIENTS, ingredients);

        List<String> instructionList = recipe.getInstructions();
        String instructions = "";
        for (int i = 0; i < instructionList.size(); i++) {
            instructions += instructionList.get(i);
            if (i < instructionList.size() - 1 ) {
                instructions += ";";
            }
        }

        contentValues.put(RECIPES_COLUMN_INSTRUCTIONS, instructions);

        byte[] img = getDrawableAsByteArray(recipe.getImage());
        contentValues.put(RECIPES_COLUMN_IMAGE, img);

        db.insert(RECIPES_TABLE_NAME, null, contentValues);
    }

    public ArrayList<Recipe> getAllRecipes() {
        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + RECIPES_TABLE_NAME, null);
        res.moveToFirst();

        while(res.isAfterLast() == false) {
            String title = res.getString(res.getColumnIndex(RECIPES_COLUMN_TITLE));
            String category = res.getString(res.getColumnIndex(RECIPES_COLUMN_CATEGORY));
            String ingredients = res.getString(res.getColumnIndex(RECIPES_COLUMN_INGREDIENTS));
            String instructions = res.getString(res.getColumnIndex(RECIPES_COLUMN_INSTRUCTIONS));
            String id = res.getString(res.getColumnIndex(RECIPES_COLUMN_ID));

            String[] tokens = instructions.split(";");
            List<String> instructionList = new ArrayList<String>();
            for (int i = 0; i < tokens.length; i++) {
                instructionList.add(tokens[i]);
            }

            List<Ingredient> ingredientList = new ArrayList<Ingredient>();
            tokens = ingredients.split(";");
            for (int i = 0; i < tokens.length; i++) {
                String[] fields = tokens[i].split("\t");
                Ingredient newIngredient = new Ingredient(fields[1], fields[2], Integer.parseInt(fields[0]));
                ingredientList.add(newIngredient);
            }

            byte[] img = res.getBlob(res.getColumnIndex(RECIPES_COLUMN_IMAGE));
            Drawable image = new BitmapDrawable(BitmapFactory.decodeByteArray(img, 0, img.length));
            Recipe newRecipe = new Recipe(title, null, category, ingredientList, instructionList);
            newRecipe.setImage(image);
            recipeList.add(newRecipe);
            res.moveToNext();
        }

        return recipeList;
    }

    public Integer deleteRecipe (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(RECIPES_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public static byte[] getDrawableAsByteArray(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();
        return bitmapdata;
    }
}
