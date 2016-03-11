package com.parse.starter;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.ParseAnalytics;

import java.util.List;


public class MainActivity extends AppCompatActivity implements recipeFetcherCallback{

  /**
   * ATTENTION: This was auto-generated to implement the App Indexing API.
   * See https://g.co/AppIndexing/AndroidStudio for more information.
   */
  private GoogleApiClient client;

  private DrawerLayout mDrawer;
  private Toolbar toolbar;
  private NavigationView nvDrawer;
  private ActionBarDrawerToggle drawerToggle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //Set the fragment initially
    BrowseFragment fragment = new BrowseFragment();
    android.support.v4.app.FragmentTransaction fragmentTransaction =
            getSupportFragmentManager().beginTransaction();

    fragmentTransaction.replace(R.id.flContent, fragment);
    fragmentTransaction.commit();

    // Set a Toolbar to replace the ActionBar.
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    // Find our drawer view
    mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawerToggle = setupDrawerToggle();

    // Tie DrawerLayout events to the ActionBarToggle
    mDrawer.setDrawerListener(drawerToggle);

    // Find our drawer view
    nvDrawer = (NavigationView) findViewById(R.id.nvView);
    // Setup drawer view
    setupDrawerContent(nvDrawer);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
    // ATTENTION: This was auto-generated to implement the App Indexing API.
    // See https://g.co/AppIndexing/AndroidStudio for more information.
    client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
  }

  private ActionBarDrawerToggle setupDrawerToggle() {
    return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
  }

  private void setupDrawerContent(NavigationView navigationView) {
    navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
              @Override
              public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
              }
            });
  }

  public void selectDrawerItem(MenuItem menuItem) {
    // Create a new fragment and specify the planet to show based on
    // position
    Fragment fragment = null;

    Class fragmentClass;

    switch(menuItem.getItemId()) {
      case R.id.nav_first_fragment:
        fragmentClass = BrowseFragment.class;
        break;
      case R.id.nav_second_fragment:
        fragmentClass = CookbookFragment.class;
        break;
      default:
        fragmentClass = BrowseFragment.class;
    }

    try {
      fragment = (Fragment) fragmentClass.newInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Insert the fragment by replacing any existing fragment
    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

    // Highlight the selected item, update the title, and close the drawer
    menuItem.setChecked(true);
    setTitle(menuItem.getTitle());
    mDrawer.closeDrawers();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // The action bar home/up action should open or close the drawer.
    switch (item.getItemId()) {
      case android.R.id.home:
        mDrawer.openDrawer(GravityCompat.START);
        return true;
    }

    if (drawerToggle.onOptionsItemSelected(item)) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  // `onPostCreate` called when activity start-up is complete after `onStart()`
  // NOTE! Make sure to override the method with only a single `Bundle` argument
  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    // Sync the toggle state after onRestoreInstanceState has occurred.
    drawerToggle.syncState();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    // Pass any configuration change to the drawer toggles
    drawerToggle.onConfigurationChanged(newConfig);
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
    // updateTable(recipeList);
  }

}
