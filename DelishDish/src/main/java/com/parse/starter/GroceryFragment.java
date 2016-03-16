package com.parse.starter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
 * Created by jakegraham on 3/15/16.
 */
public class GroceryFragment extends Fragment implements CookbookRemoveCallback {
    ListView listView;
    GroceryDB groceryDB;


    public GroceryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_grocery_list, container, false);

        groceryDB = new GroceryDB(this.getActivity());


        List<Ingredient> ingredientList = new ArrayList<Ingredient>();
        ingredientList = groceryDB.getAllIngredients();

        listView = (ListView) rootView.findViewById(R.id.grocery_list_view);

        IngredientListAdapter adapter = new IngredientListAdapter(listView.getContext(), ingredientList);
        listView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_grocery_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.add_grocery_item:
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
                        groceryDB.insertIngredient(new Ingredient(title, amount, quantity));
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
            case R.id.remove_grocery_item:
                updateListRemove();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateList() {
        List<Ingredient> ingredientList = new ArrayList<Ingredient>();
        ingredientList = groceryDB.getAllIngredients();

        IngredientListAdapter adapter = new IngredientListAdapter(listView.getContext(), ingredientList);
        listView.setAdapter(adapter);
    }

    public void updateListRemove() {
        List<Ingredient> ingredientList = new ArrayList<Ingredient>();
        ingredientList = groceryDB.getAllIngredients();

        CookbookRemoveAdapter adapter = new CookbookRemoveAdapter(listView.getContext(), ingredientList, this);
        listView.setAdapter(adapter);
    }

    @Override
    public void itemDeleted() {
        updateList();
    }
}
