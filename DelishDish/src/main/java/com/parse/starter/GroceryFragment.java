package com.parse.starter;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
        View rootView = inflater.inflate(R.layout.fragment_cookbook, container, false);

        listView = (ListView) rootView.findViewById(R.id.grocery_list_view);

        return rootView;
    }
}
