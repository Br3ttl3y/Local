package com.austindroids.commuter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

public abstract class BaseFragment extends Fragment {

    public EventBus eventBus;

    public abstract int getLayoutId();

    public abstract int getTitleId();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(getTitleId());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        eventBus = EventBus.getInstance();
    }

    protected void inflateChildFragment(Fragment fragment, int containerView, boolean addToBackStack) {
        if (fragment != null) {
            FragmentManager childFragmentManager = getChildFragmentManager();
            FragmentTransaction cft = childFragmentManager.beginTransaction();
            if (addToBackStack) {
                cft.addToBackStack(null);
            }
            cft.replace(containerView, fragment).commit();
        }
    }
}
