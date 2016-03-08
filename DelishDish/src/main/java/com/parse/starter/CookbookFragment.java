package com.parse.starter;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CookbookFragment extends Fragment {
    ListView listView;
    DBHelper mydb;

    public CookbookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cookbook, container, false);

        mydb = new DBHelper(this.getActivity());

        List<Recipe> recipeList = mydb.getAllRecipes();

//        String temp = "";
//        for (int i = 0; i < recipeList.size(); i++) {
//            temp += recipeList.get(i).getTitle() + "\n";
//        }
//
//        TextView textView = (TextView) rootview.findViewById(R.id.recipeList);
//        textView.setText(temp);

        listView = (ListView) rootView.findViewById(R.id.cookbook_list_view);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Recipe recipe = (Recipe) parent.getItemAtPosition(position);
                Intent myIntent = new Intent(getActivity(), RecipeActivity.class);
                Bitmap image = ((BitmapDrawable) recipe.getImage()).getBitmap();
                myIntent.putExtra("Title", recipe.getTitle());
                myIntent.putExtra("Category", recipe.getCategory());
                myIntent.putStringArrayListExtra("Instruction", (ArrayList<String>) recipe.getInstructions());
                myIntent.putExtra("Image", image);
                myIntent.putParcelableArrayListExtra("Ingredients", (ArrayList<? extends Parcelable>) recipe.getIngredients());
                getActivity().startActivity(myIntent);
            }
        });

        BrowseRowAdapter adapter = new BrowseRowAdapter(listView.getContext(), recipeList);
        listView.setAdapter(adapter);

        return rootView;
    }



}
