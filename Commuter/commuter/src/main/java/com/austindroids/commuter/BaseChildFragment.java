package com.austindroids.commuter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by markrebhan on 7/22/14.
 */
public abstract class BaseChildFragment extends Fragment {

    public EventBus eventBus;
    public abstract int getLayoutId();

    public abstract int getContainerViewId();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        eventBus = EventBus.getInstance();
    }
}
