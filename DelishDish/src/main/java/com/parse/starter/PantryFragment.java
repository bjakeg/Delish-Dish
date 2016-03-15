package com.parse.starter;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by david on 3/15/16.
 */
public class PantryFragment extends Fragment {

    ListView listView;


    public PantryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cookbook, container, false);

        listView = (ListView) rootView.findViewById(R.id.pantry_list_view);

        return rootView;
    }
}
