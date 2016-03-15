package com.parse.starter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jakegraham on 3/15/16.
 */
public class IngredientListAdapter extends ArrayAdapter<Ingredient> {
    LayoutInflater inflater;

    public IngredientListAdapter(Context context, List<Ingredient> recipeList) {
        super(context, -1, recipeList);
        inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.ingredient_row, parent, false);
        Ingredient ingredient = getItem(position);

        TextView title = (TextView) view.findViewById(R.id.ingredient_title);
        TextView amount = (TextView) view.findViewById(R.id.ingredient_amount);
        TextView quantity = (TextView) view.findViewById(R.id.ingredient_quantity);
        title.setText(ingredient.getTitle() + " ");
        amount.setText("(" + ingredient.getAmount() + ")");
        quantity.setText(ingredient.getQuantity() + " ");

        return view;
    }

}
