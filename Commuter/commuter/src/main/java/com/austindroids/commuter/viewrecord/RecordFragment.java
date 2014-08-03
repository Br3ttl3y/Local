package com.austindroids.commuter.viewrecord;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.austindroids.commuter.BaseFragment;
import com.austindroids.commuter.stopwatchmodel.OnGetStopWatchTime;
import com.austindroids.commuter.R;
import com.austindroids.commuter.stopwatchmodel.StopWatchManager;
import com.austindroids.commuter.stopwatchmodel.StopWatchThreadManager;
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
    public static final String STOPWATCH_MAIN = "main";
    public static final String STOPWATCH_STOPPED = "time_stopped";

    FinishButtonFragment finishButtonFragment;
    StopWatchFragment stopWatchFragment;
    StartPauseFragment startPauseFragment;

    StopWatchManager stopWatchManager;
    StopWatchThreadManager stopWatchThreadManager;

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

        stopWatchManager = StopWatchManager.getInstance();
        // Create stopwatches
        stopWatchManager.createStopWatch(STOPWATCH_MAIN);
        stopWatchManager.createStopWatch(STOPWATCH_STOPPED);
        stopWatchThreadManager = StopWatchThreadManager.getInstance();

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
        // Register listeners to receive updates on the current time
        stopWatchThreadManager.registerListener(STOPWATCH_MAIN, 100, new OnMainTimeUpdate());
        stopWatchThreadManager.registerListener(STOPWATCH_STOPPED, 100, new OnTimeStoppedUpdate());

    }

    @Override
    public void onPause() {
        super.onPause();
        eventBus.unregister(this);
        stopWatchThreadManager.unRegisterListener(STOPWATCH_MAIN);
        stopWatchThreadManager.unRegisterListener(STOPWATCH_STOPPED);
    }

    @Subscribe
    public void onStartPause(EventStartPause event) {

        switch (event.getState()) {
            case START_ROUTE:
                stopWatchManager.startStopwatch(STOPWATCH_MAIN);
                Log.d(TAG, "start main stopwatch");
                break;
            case RUNNING:
                stopWatchManager.pauseStopWatch(STOPWATCH_STOPPED);
                Log.d(TAG, "pause time stopped stopwatch");
                break;
            case PAUSED:
                stopWatchManager.startStopwatch(STOPWATCH_STOPPED);
                Log.d(TAG, "start/resume time stopped stopwatch");
                break;
            case FINISHED:
                stopWatchManager.stopAllStopWatches();
                Log.d(TAG, "stop stopwatch");
                break;
        }

    }

    public class OnMainTimeUpdate implements OnGetStopWatchTime {
        @Override
        public void onGetCurrentTime(long currentTime) {
            stopWatchFragment.updateMainTimerView(currentTime);
        }
    }

    public class OnTimeStoppedUpdate implements OnGetStopWatchTime {
        @Override
        public void onGetCurrentTime(long currentTime) {
            stopWatchFragment.updateTimeStoppedView(currentTime);
        }
    }


}
