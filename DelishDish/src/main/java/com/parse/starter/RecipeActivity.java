package com.parse.starter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jakegraham on 2/29/16.
 */
public class RecipeActivity extends AppCompatActivity {

    Button addButton;
    DBHelper mydb;
    List<Ingredient> ingredientList;
    List<String> instructionList;
    String title;
    String category;
    Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mydb = new DBHelper(this);

        addButtonListener();

        Bundle extras = getIntent().getExtras();

        image = (Bitmap) extras.get("Image");
        ingredientList = extras.getParcelableArrayList("Ingredients");
        instructionList = extras.getStringArrayList("Instruction");
        title = extras.getString("Title");
        category = extras.getString("Category");
        // TODO: Add image link

        ImageView imageView = (ImageView) findViewById(R.id.recipe_main_image);
        TextView titleView = (TextView) findViewById(R.id.recipe_main_title);
        TextView ingredientsView = (TextView) findViewById(R.id.recipe_main_ingredients);
        TextView instructionsView = (TextView) findViewById(R.id.recipe_main_instructions);

        imageView.setImageBitmap(image);
        titleView.setText(title);

        String ingredientString = "";
        for(int i = 0; i < ingredientList.size(); i++) {
            Ingredient ingredient = ingredientList.get(i);
            ingredientString += "\t" + ingredient.getQuantity() +
                                "\t" + ingredient.getTitle() + "\t" +
                                "(" + ingredient.getAmount() + ")\n";
        }
        ingredientsView.setText(ingredientString);

        String instructionsString = "";
        for(int i = 0; i < instructionList.size(); i++) {
            String instruction = instructionList.get(i);
            instructionsString += (i+1) + ".\t" + instruction + "\n\n";
        }
        instructionsView.setText(instructionsString);

    }

    public void addButtonListener() {
        addButton = (Button) findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Recipe newRecipe = new Recipe(title, null, category, ingredientList, instructionList);
                Drawable d = new BitmapDrawable(getResources(), image);
                newRecipe.setImage(d);
                mydb.insertRecipe(newRecipe);
            }

        });

    }

}
