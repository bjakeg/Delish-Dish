package com.parse.starter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jakegraham on 3/15/16.
 */
public class PantryRemoveAdapter extends ArrayAdapter<Ingredient> {
    LayoutInflater inflater;
    Button button;
    PantryDB pantryDB;

    PantryRemoveCallback lc;

    public PantryRemoveAdapter(Context context, List<Ingredient> recipeList, PantryRemoveCallback lc) {
        super(context, -1, recipeList);
        this.lc = lc;
        inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.ingredient_delete_row, parent, false);
        final Ingredient ingredient = getItem(position);

        TextView title = (TextView) view.findViewById(R.id.ingredient_title);
        TextView amount = (TextView) view.findViewById(R.id.ingredient_amount);
        TextView quantity = (TextView) view.findViewById(R.id.ingredient_quantity);
        button = (Button) view.findViewById(R.id.ingredient_delete_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pantryDB = new PantryDB(getContext());
                pantryDB.deleteIngredient(ingredient.getTitle());
                lc.itemDeleted();
            }
        });

        title.setText(ingredient.getTitle() + " ");
        amount.setText("(" + ingredient.getAmount() + ")");
        quantity.setText(ingredient.getQuantity() + " ");

        return view;
    }

}

interface PantryRemoveCallback {
    public void itemDeleted();
}

