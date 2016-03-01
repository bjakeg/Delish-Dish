package com.parse.starter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jakegraham on 2/29/16.
 */
public class BrowseRowAdapter extends ArrayAdapter<Recipe> {
    LayoutInflater inflater;

    public BrowseRowAdapter(Context context, List<Recipe> recipeList) {
        super(context, -1, recipeList);
        inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.browse_row, parent, false);
        Recipe recipe = getItem(position);
        TextView title = (TextView) view.findViewById(R.id.recipe_title);
        ImageView imageView = (ImageView) view.findViewById(R.id.recipe_image);
        imageView.setImageDrawable(recipe.getImage());
        title.setText(recipe.getTitle());

        return view;
    }
}
