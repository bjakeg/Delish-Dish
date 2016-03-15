package com.parse.starter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jakegraham on 3/15/16.
 */
public class GroceryFragment extends Fragment {
    ListView listView;

    public GroceryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_grocery_list, container, false);

        listView = (ListView) rootView.findViewById(R.id.grocery_list_view);

        List<Ingredient> ingredientList = new ArrayList<Ingredient>();

        for(int i = 0; i < 3; i++) {
            Ingredient newIngredient = new Ingredient("Corned beef", "1 lb", 1);
            ingredientList.add(newIngredient);
        }

        IngredientListAdapter adapter = new IngredientListAdapter(listView.getContext(), ingredientList);
        listView.setAdapter(adapter);

        return rootView;
    }
}
