package com.parse.starter;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
}
