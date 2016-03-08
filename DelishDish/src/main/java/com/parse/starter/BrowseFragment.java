package com.parse.starter;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BrowseFragment extends Fragment implements recipeFetcherCallback {
    ListView listView;
    ProgressDialog activityIndicator;

    public BrowseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_browse, container, false);
        listView = (ListView) rootView.findViewById(R.id.recipe_list_view);

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

        // TODO (jake): Begin activity indicator
        activityIndicator = new ProgressDialog(this.getActivity());
        activityIndicator.setCancelable(false);
        activityIndicator.setMessage("Loading...Please Wait (00.0%)");
        activityIndicator.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        activityIndicator.setIndeterminate(true);
        activityIndicator.show();

        RecipeFetcher rf = new RecipeFetcher(this, null);
        rf.getAllRecipes();

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void callback(List<Recipe> recipeList) {
        fillImages(recipeList);
    }

    public void fillImages(final List<Recipe> recipeList) {
        final int[] count = {0};
        for (int i = 0; i < recipeList.size(); i++) {
            final ImageView imageView = new ImageView(this.getActivity());
            final Drawable[] bmp = new Drawable[1];

            final int finalI = i;
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        InputStream in = (InputStream) new URL(recipeList.get(finalI).getImageLink()).getContent();
                        bmp[0] = Drawable.createFromStream(in, "src name");
                    } catch (Exception e) {
                        // log error
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    if (bmp[0] != null)
                        recipeList.get(finalI).setImage(bmp[0]);

                    count[0]++;

                    activityIndicator.setMessage("Loading...Please Wait (" +
                                                 String.format("%2.1f", ((float)count[0]/(float)recipeList.size())*100) +
                                                "%)");

                    // Only update table after all rows have been formatted
                    if (count[0] == recipeList.size()) {
                       updateListView(recipeList);
                    }
                }

            }.execute();


        }
    }

    private void updateListView(List<Recipe> recipeList) {
        activityIndicator.dismiss();
        BrowseRowAdapter adapter = new BrowseRowAdapter(listView.getContext(), recipeList);
        listView.setAdapter(adapter);
    }

}
