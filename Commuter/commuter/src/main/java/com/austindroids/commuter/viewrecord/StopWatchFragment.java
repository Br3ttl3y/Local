package com.austindroids.commuter.viewrecord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.austindroids.commuter.BaseChildFragment;
import com.austindroids.commuter.R;
import com.austindroids.commuter.stopwatchmodel.StopWatchManager;

public class StopWatchFragment extends BaseChildFragment {

    public static final String TAG = "StopWatchFragment";

    StopWatchManager stopWatchManager;

    TextView totalTime;
    TextView timeStopped;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_stopwatch;
    }

    @Override
    public int getContainerViewId() {
        return R.id.frame_record_middle;
    }

    public static StopWatchFragment newInstance() {
        return new StopWatchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stopWatchManager = StopWatchManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);

        totalTime = (TextView) view.findViewById(R.id.time_total);
        timeStopped = (TextView) view.findViewById(R.id.time_stopped);

        return view;
    }

    public void updateMainTimerView(long mainTimer) {
        totalTime.setText(formatTimer(mainTimer));
    }

    public void updateTimeStoppedView(long stoppedTimer) {
        timeStopped.setText(formatTimer(stoppedTimer));
    }

    private String formatTimer(long timeInMillis) {
        int seconds = (int) timeInMillis / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);

    }
}
