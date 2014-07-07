package austindroids.com.commuter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NavigationDrawer extends Fragment{

    /**
     * The design guidelines state that the drawer should open up automatically until the user clicks
     * on it themselves, this preference saves this state.
     */
    private static final String PREF_LEARNED_ABOUT_DRAWER = "nav_drawer_learned";

    /**
     * save the currently selected position in case of fragment is destroyed and recreated
     */
    private static final String STATE_SELECTED_POSITION = "selected_nav_drawer_position";

    NavigationDrawerCallbacks callbacks;

    private int currentSelectedPosition = 0;
    private boolean userLearnedAboutDrawer;

    private ListView drawerListView;
    private View fragmentContainerView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private NavItem[] navItemsDefault;
    private ArrayAdapter<NavItem> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        userLearnedAboutDrawer = sharedPreferences.getBoolean(PREF_LEARNED_ABOUT_DRAWER, false);

        navItemsDefault = new NavItem[] {
          new NavItem(R.id.nav_record, getString(R.string.record))
        };

        if (savedInstanceState != null) {
            currentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            // TODO figure out how to navigate to this fragment
        }

    }

    @Override
    public void onAttach(Activity activity) {
        // tell the activity that options reside here.
        setHasOptionsMenu(true);
        super.onAttach(activity);
        try {
            callbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        drawerListView = (ListView) inflater.inflate(R.layout.fragment_nav_drawer, container, false);
        drawerListView.setOnItemClickListener(new OnNavItemSelected());

        adapter = new ArrayAdapter<NavItem>(
                getActionBar().getThemedContext(),
                R.layout.row_nav_item,
                R.id.text_nav_text
        );

        // TODO move this behind auth layer if implemented
        adapter.clear();
        adapter.addAll(navItemsDefault);
        adapter.notifyDataSetChanged();

        drawerListView.setAdapter(adapter);

        drawerListView.setItemChecked(currentSelectedPosition, true);
        return drawerListView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, currentSelectedPosition);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        fragmentContainerView = getActivity().findViewById(fragmentId);
        this.drawerLayout = drawerLayout;

        // TODO set a shadow

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

        drawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                drawerLayout,
                R.drawable.ic_drawer,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_closed
        ) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // do not open or close drawer if fragment is not attached to activity
                if (!isAdded()) {
                    return;
                }

                if (!userLearnedAboutDrawer) {
                    // The user has not learned about the drawer.
                    userLearnedAboutDrawer = true;
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    sharedPreferences.edit().putBoolean(PREF_LEARNED_ABOUT_DRAWER, true).apply();
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // do not open or close drawer if fragment is not attached to activity
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

        };

        // open the drawer if the user has not learned about it yet
        if (!userLearnedAboutDrawer) {
            drawerLayout.openDrawer(fragmentContainerView);
        }

        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });

        // set the listener to listen for drawer state changes
        drawerLayout.setDrawerListener(drawerToggle);
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    // A common interface that all activities using the drawer must implement
    public static interface NavigationDrawerCallbacks {
        void onNavDrawerItemSelected(NavItem item);
    }

    private void selectItem(int position, NavItem item) {
        currentSelectedPosition = position;

        if (drawerListView != null) {
            drawerListView.setItemChecked(position, true);
        }
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(fragmentContainerView);
        }
        if (callbacks != null) {
            // Send a callback to the activity to replace the container with the new fragment
            callbacks.onNavDrawerItemSelected(item);
        }
    }

    public static class NavItem {
        final int id;
        final String title;

        NavItem(int id, String title) {
            this.id = id;
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    private class OnNavItemSelected implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            NavItem item = (NavItem) parent.getItemAtPosition(position);
            selectItem(position, item);
        }
    }
}


