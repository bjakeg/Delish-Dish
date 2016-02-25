package com.parse.starter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.starter.Recipe;
import com.parse.starter.Ingredient;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements recipeFetcherCallback{

  /**
   * ATTENTION: This was auto-generated to implement the App Indexing API.
   * See https://g.co/AppIndexing/AndroidStudio for more information.
   */
  private GoogleApiClient client;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // TODO (jake): Begin activity indicator

    RecipeFetcher rf = new RecipeFetcher(this, null);
    rf.getAllRecipes();

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
    // ATTENTION: This was auto-generated to implement the App Indexing API.
    // See https://g.co/AppIndexing/AndroidStudio for more information.
    client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onStart() {
    super.onStart();

    // ATTENTION: This was auto-generated to implement the App Indexing API.
    // See https://g.co/AppIndexing/AndroidStudio for more information.
    client.connect();
    Action viewAction = Action.newAction(
            Action.TYPE_VIEW, // TODO: choose an action type.
            "Main Page", // TODO: Define a title for the content shown.
            // TODO: If you have web page content that matches this app activity's content,
            // make sure this auto-generated web page URL is correct.
            // Otherwise, set the URL to null.
            Uri.parse("http://host/path"),
            // TODO: Make sure this auto-generated app deep link URI is correct.
            Uri.parse("android-app://com.parse.starter/http/host/path")
    );
    AppIndex.AppIndexApi.start(client, viewAction);
  }

  @Override
  public void onStop() {
    super.onStop();

    // ATTENTION: This was auto-generated to implement the App Indexing API.
    // See https://g.co/AppIndexing/AndroidStudio for more information.
    Action viewAction = Action.newAction(
            Action.TYPE_VIEW, // TODO: choose an action type.
            "Main Page", // TODO: Define a title for the content shown.
            // TODO: If you have web page content that matches this app activity's content,
            // make sure this auto-generated web page URL is correct.
            // Otherwise, set the URL to null.
            Uri.parse("http://host/path"),
            // TODO: Make sure this auto-generated app deep link URI is correct.
            Uri.parse("android-app://com.parse.starter/http/host/path")
    );
    AppIndex.AppIndexApi.end(client, viewAction);
    client.disconnect();
  }

  @Override
  public void callback(List<Recipe> recipeList) {
    // TODO: Pass recipe list to UI stuffs
  }

  public void updateTable(final List<Recipe> recipeList) {
    final List<TableRow> rows = new ArrayList<TableRow>();
    final int[] count = {0};
    for (int i = 0; i < recipeList.size(); i++) {
      // create a new TableRow
      final TableRow row = new TableRow(this);

      // create a new TextView
      final TextView t = new TextView(this);
      // set the text to "text xx"
      t.setText(recipeList.get(i).getTitle());
      final ImageView imageView = new ImageView(this);

      final Drawable[] bmp = new Drawable[1];

      final int finalI = i;
      new AsyncTask<Void, Void, Void>() {
        @Override
        protected Void doInBackground(Void... params) {
          try {
            InputStream in = (InputStream) new URL(recipeList.get(finalI).getImageLink()).getContent();
            bmp[0] = Drawable.createFromStream(in, "src name");
            Bitmap b = Bitmap.createScaledBitmap(((BitmapDrawable)bmp[0]).getBitmap(), 150, 150, false);
            bmp[0] = new BitmapDrawable(getResources(), b);
          } catch (Exception e) {
            // log error
          }
          return null;
        }

        @Override
        protected void onPostExecute(Void result) {
          if (bmp[0] != null)
            imageView.setImageDrawable(bmp[0]);
          // add the TextView and the CheckBox to the new TableRow
          row.addView(t);
          row.addView(imageView);
          row.setPadding(10,10,10,10);
          rows.add(row);

          count[0]++;
          // Only update table after all rows have been formatted
          if (count[0] == recipeList.size()) {
            updateRows(rows);
          }
        }

      }.execute();


    }
  }

  public void updateRows(List<TableRow> rows) {
    // get a reference for the TableLayout
    TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);
    for (int i = 0; i < rows.size(); i++) {
      // TODO (jake): Remove activity indicator
      table.addView(rows.get(i), new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
              ViewGroup.LayoutParams.WRAP_CONTENT));
    }
  }
}
