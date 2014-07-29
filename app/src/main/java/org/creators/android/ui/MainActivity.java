package org.creators.android.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import org.creators.android.R;
import org.creators.android.data.model.User;
import org.creators.android.ui.nav.NavItem;
import org.creators.android.ui.nav.NavigationDrawerFragment;


public class MainActivity extends Activity
  implements NavigationDrawerFragment.NavigationDrawerCallbacks {
  public static final String TAG = "MainActivity";

  private User mUser;
  private NavigationDrawerFragment mNavigationDrawerFragment;
  private CharSequence mTitle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

//    getActionBar().setTitle(mUser.getFullName());

    mNavigationDrawerFragment = (NavigationDrawerFragment)
      getFragmentManager().findFragmentById(R.id.navigation_drawer);
    mTitle = getTitle();

    // Set up the drawer.
    mNavigationDrawerFragment.setUp(
      R.id.navigation_drawer,
      (DrawerLayout) findViewById(R.id.drawer_layout));

    mUser = User.getCurrentUser();
    if (mUser == null) {
      startActivity(new Intent(this, LoginActivity.class));
      finish();
    }
  }

  @Override
  public void onNavigationDrawerItemSelected(NavItem item) {
    item.replace(R.id.container);
    mTitle = item.getTitle();
    restoreActionBar();
  }

  public void restoreActionBar() {
    ActionBar actionBar = getActionBar();
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    actionBar.setDisplayShowTitleEnabled(true);
    actionBar.setTitle(mTitle);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    if (!mNavigationDrawerFragment.isDrawerOpen()) {
      getMenuInflater().inflate(R.menu.main, menu);
      restoreActionBar();
      return true;
    }
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    return id == R.id.action_settings || super.onOptionsItemSelected(item);
  }

}
