package com.parse.starter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jakegraham on 2/22/16.
 */
public class RecipeFetcher {

    recipeFetcherCallback rc;
    String category;
    RecipeFetcher(recipeFetcherCallback rc, String category) {
        this.rc = rc;
        this.category = category;
    }

    public void getAllRecipes() {
        final List<Recipe> recipeList = new ArrayList<Recipe>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Recipe");
        query.include("Ingredients");
        query.include("ingredients");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> text, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < text.size(); i++) {
                        String title = text.get(i).getString("title");
                        String imageLink = text.get(i).getString("imageLink");
                        String category = text.get(i).getString("Category");
                        JSONArray instructions = text.get(i).getJSONArray("instructions");
                        List<ParseObject> ingredients = text.get(i).getList("ingredients");

                        List<String> instructionsList = new ArrayList<String>();
                        for (int j = 0; j < instructions.length(); j++) {
                            try {
                                instructionsList.add(instructions.getString(j));
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }

                        List<Ingredient> ingredientList = new ArrayList<Ingredient>();
                        for (int j = 0; j < ingredients.size(); j++) {
                            ParseObject obj = ingredients.get(j);
                            String ingTitle = obj.getString("title");
                            String ingAmount = obj.getString("amount");
                            Number ingQuantity = obj.getNumber("quantity");
                            Ingredient ing = new Ingredient(ingTitle, ingAmount, ingQuantity.intValue());
                            ingredientList.add(ing);
                        }

                        Recipe newRecipe =
                                new Recipe(title, imageLink, category, ingredientList, instructionsList);
                        recipeList.add(newRecipe);
                    }
                    System.out.println(recipeList.toString());
                    rc.callback(recipeList);
                } else {
                    System.out.println("Had an issue");
                }
            }
        });

    }

}

interface recipeFetcherCallback {
    public void callback(List<Recipe> recipeList);

}