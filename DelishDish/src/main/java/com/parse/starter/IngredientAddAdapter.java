package com.parse.starter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
public class IngredientAddAdapter extends ArrayAdapter<Ingredient> {
    LayoutInflater inflater;
    Button button;
    GroceryDB groceryDB;
    PantryDB pantryDB;


    public IngredientAddAdapter(Context context, List<Ingredient> ingredientList) {
        super(context, -1, ingredientList);
        inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.ingredient_add_row, parent, false);
        final Ingredient ingredient = getItem(position);

        TextView title = (TextView) view.findViewById(R.id.ingredient_title);
        TextView amount = (TextView) view.findViewById(R.id.ingredient_amount);
        TextView quantity = (TextView) view.findViewById(R.id.ingredient_quantity);
        button = (Button) view.findViewById(R.id.ingredient_add_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Where should it be added?");

                dialog.setPositiveButton("Grocery List", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        groceryDB = new GroceryDB(getContext());
                        groceryDB.insertIngredient(ingredient);
                    }
                });

                dialog.setNegativeButton("Pantry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pantryDB = new PantryDB(getContext());
                        pantryDB.insertIngredient(ingredient);
                    }
                });
                dialog.show();
            }
        });

        title.setText(ingredient.getTitle() + " ");
        amount.setText("(" + ingredient.getAmount() + ")");
        quantity.setText(ingredient.getQuantity() + " ");

        return view;
    }

}
