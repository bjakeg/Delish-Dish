package com.parse.starter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.starter.Ingredient;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jakegraham on 2/21/16.
 */
public class Recipe {

    private String title;
    private String imageLink;
    private String category;
    private List<Ingredient> ingredients = new ArrayList<Ingredient>();
    private List<String> instructions = new ArrayList<String>();

    public Recipe (String title,
                   String imageLink,
                   String category,
                   List<Ingredient> ingredients,
                   List<String> instructions) {
        this.title = title;
        this.imageLink = imageLink;
        this.category = category;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    // Add an ingredient to the ingredients list
    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    // Add an instruction to the instructions
    public void addInstruction(String instruction) {
        instructions.add(instruction);
    }

    // Getters
    public String getTitle() {
        return title;
    }
    public String getImageLink() {
        return imageLink;
    }
    public String getCategory() {
        return category;
    }
    public List<Ingredient> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }
    public List<String> getInstructions() {
        return Collections.unmodifiableList(instructions);
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }
    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }


}
