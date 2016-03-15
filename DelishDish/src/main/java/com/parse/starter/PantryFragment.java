package com.parse.starter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 3/15/16.
 */
public class PantryFragment extends Fragment {

    ListView listView;
    PantryDB pantryDB;

    public PantryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pantry, container, false);

        pantryDB = new PantryDB(this.getActivity());

        List<Ingredient> ingredientList = new ArrayList<Ingredient>();
        ingredientList = pantryDB.getAllIngredients();

        listView = (ListView) rootView.findViewById(R.id.pantry_list_view);

        IngredientListAdapter adapter = new IngredientListAdapter(listView.getContext(), ingredientList);
        listView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_pantry_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.add_pantry_item:
                // open form
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Set Target Title & Description");
                LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText titleBox = new EditText(getActivity());
                titleBox.setHint("Title");
                layout.addView(titleBox);

                final EditText amountBox = new EditText(getActivity());
                amountBox.setHint("Amount");
                layout.addView(amountBox);

                final EditText quantityBox = new EditText(getActivity());
                quantityBox.setHint("Quantity");
                layout.addView(quantityBox);

                dialog.setView(layout);
                dialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = titleBox.getText().toString();
                        String amount = amountBox.getText().toString();
                        int quantity = Integer.parseInt(quantityBox.getText().toString());
                        pantryDB.insertIngredient(new Ingredient(title, amount, quantity));
                        updateList();
                    }
                });

                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateList() {
        List<Ingredient> ingredientList = new ArrayList<Ingredient>();
        ingredientList = pantryDB.getAllIngredients();

        IngredientListAdapter adapter = new IngredientListAdapter(listView.getContext(), ingredientList);
        listView.setAdapter(adapter);
    }

}
