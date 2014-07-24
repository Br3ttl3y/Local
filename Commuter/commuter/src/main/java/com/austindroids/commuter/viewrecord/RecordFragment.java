package com.austindroids.commuter.viewrecord;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.austindroids.commuter.BaseFragment;
import com.austindroids.commuter.R;
import com.austindroids.commuter.model.StopWatch;
import com.squareup.otto.Subscribe;

/**
 * Created by markrebhan on 7/5/14.
 */
public class RecordFragment extends BaseFragment {

    // TODO move this to service or manager
    public enum RecordingState{
        NOT_STARTED,
        START_ROUTE,
        RUNNING,
        PAUSED,
        FINISHED
    }

    public static final String TAG = "RecordFragment";

    FinishButtonFragment finishButtonFragment;
    StopWatchFragment stopWatchFragment;
    StartPauseFragment startPauseFragment;

    private StopWatch stopWatch;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_record;
    }

    @Override
    public int getTitleId() {
        return R.string.record;
    }

    public static RecordFragment getInstance() {
        RecordFragment fragment = new RecordFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO move this to a service
        stopWatch = new StopWatch(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);

        finishButtonFragment = FinishButtonFragment.newInstance();
        inflateChildFragment(finishButtonFragment, finishButtonFragment.getContainerViewId(), true);
        stopWatchFragment = StopWatchFragment.newInstance();
        inflateChildFragment(stopWatchFragment, stopWatchFragment.getContainerViewId(), true);
        startPauseFragment = StartPauseFragment.newInstance();
        inflateChildFragment(startPauseFragment, startPauseFragment.getContainerViewId(), true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        eventBus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        eventBus.unregister(this);
    }

    @Subscribe
    public void onStartPause(EventStartPause event) {

        switch (event.getState()) {
            case START_ROUTE:
                stopWatch.start();
                Log.d(TAG, "start stopwatch");
                break;
            case RUNNING:
                stopWatch.start();
                Log.d(TAG, "start stopwatch");
                break;
            case PAUSED:
                stopWatch.stop();
                Log.d(TAG, "stop stopwatch");
                break;
            case FINISHED:
                Log.d(TAG, "stop stopwatch");
                break;
        }

    }
}
