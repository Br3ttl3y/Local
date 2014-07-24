package com.austindroids.commuter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.austindroids.commuter.viewrecord.RecordFragment;

/**
 * This class represents the main activity of the application. This class contains the entire view
 * of the screen minus the action bar and status bar. This class handles creation of the nav drawer
 * and any new fragments needed for the app. To accomplish this, use the nav item list or create
 * callbacks to this class from the fragments by attaching the other classes interface in the
 * onAttach(Activity activity) event and over riding the methods in this class where it should
 * create the fragment and pass in any args that are needed.
 */
public class HomeActivity extends ActionBarActivity implements NavigationDrawer.NavigationDrawerCallbacks{

    private NavigationDrawer navigationDrawer;

    @Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationDrawer = (NavigationDrawer) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        // setup the nav drawer
        navigationDrawer.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        // By default, navigate to the record fragment.
        navigateToPage(R.id.nav_record);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavDrawerItemSelected(NavigationDrawer.NavItem item) {
        navigateToPage(item.id);
    }

    private void navigateToPage(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.id.nav_record:
                fragment = RecordFragment.getInstance();
        }
        // we do not want to add to backstack here because if we do and the user clicks on the same
        // nav drawer item over and over again, they will all be added to backstack holding the back
        // button hostage
        // TODO we can code it up so if there is not an instance if a particular fragment, we can add it to backstack, but this breaks android design guidelines.
        navigateToFragment(fragment, false);
    }

    private void navigateToFragment(Fragment fragment, boolean addToBackStack) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (addToBackStack) {
                ft.addToBackStack(null);
            }
            ft.replace(R.id.container, fragment).commit();
        }

    }

}
