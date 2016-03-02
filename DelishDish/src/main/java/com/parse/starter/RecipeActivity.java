package com.parse.starter;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jakegraham on 2/29/16.
 */
public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Bundle extras = getIntent().getExtras();

        Bitmap image = (Bitmap) extras.get("Image");
        List<Ingredient> ingredientList = extras.getParcelableArrayList("Ingredients");
        List<String> instructionList = extras.getStringArrayList("Instruction");
        String title = extras.getString("Title");
        String category = extras.getString("Category");

        ImageView imageView = (ImageView) findViewById(R.id.recipe_main_image);
        TextView titleView = (TextView) findViewById(R.id.recipe_main_title);
        TextView ingredientsView = (TextView) findViewById(R.id.recipe_main_ingredients);
        TextView instructionsView = (TextView) findViewById(R.id.recipe_main_instructions);

        imageView.setImageBitmap(image);
        titleView.setText(title);

        String ingredientString = "";
        for(int i = 0; i < ingredientList.size(); i++) {
            Ingredient ingredient = ingredientList.get(i);
            ingredientString += (i+1) + ".\t" + ingredient.getQuantity() +
                                "\t" + ingredient.getTitle() + "\t" +
                                "(" + ingredient.getAmount() + ")\n";
        }
        ingredientsView.setText(ingredientString);

        String instructionsString = "";
        for(int i = 0; i < instructionList.size(); i++) {
            String instruction = instructionList.get(i);
            instructionsString += (i+1) + ".\t" + instruction + "\n";
        }
        instructionsView.setText(instructionsString);
        String blank = "blank;";
    }

}
