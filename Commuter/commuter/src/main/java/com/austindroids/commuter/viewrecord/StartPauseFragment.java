package com.austindroids.commuter.viewrecord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.austindroids.commuter.BaseChildFragment;
import com.austindroids.commuter.R;

/**
 * Created by markrebhan on 7/22/14.
 */
public class StartPauseFragment extends BaseChildFragment {

    private static final String buttonState = "button_state";

    boolean isRunning;

    Button startRouteBtn;
    Button startBtn;
    Button pauseBtn;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_start_pause_button;
    }

    @Override
    public int getContainerViewId() {
        return R.id.frame_bottom;
    }

    public static StartPauseFragment newInstance() {
        // TODO pass in some state so we know what views to show

        return new StartPauseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);

        if (savedInstanceState != null) {
            isRunning = savedInstanceState.getBoolean(buttonState);
        }

        startRouteBtn = (Button) view.findViewById(R.id.button_start_route);
        startRouteBtn.setOnClickListener(new OnStartRouteClicked());

        startBtn = (Button) view.findViewById(R.id.button_start);
        startBtn.setOnClickListener(new OnStartClicked());

        pauseBtn = (Button) view.findViewById(R.id.button_pause);
        pauseBtn.setOnClickListener(new OnPauseClicked());

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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(buttonState, isRunning);
    }

    public class OnStartRouteClicked implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            eventBus.post(new EventStartPause(RecordFragment.RecordingState.START_ROUTE));
            startRouteBtn.setVisibility(View.GONE);
            startBtn.setVisibility(View.GONE);
            pauseBtn.setVisibility(View.VISIBLE);
        }
    }

    public class OnStartClicked implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            eventBus.post(new EventStartPause(RecordFragment.RecordingState.RUNNING));
            startRouteBtn.setVisibility(View.GONE);
            startBtn.setVisibility(View.GONE);
            pauseBtn.setVisibility(View.VISIBLE);
            isRunning = true;
        }
    }

    public class OnPauseClicked implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            eventBus.post(new EventStartPause(RecordFragment.RecordingState.PAUSED));
            startRouteBtn.setVisibility(View.GONE);
            startBtn.setVisibility(View.VISIBLE);
            pauseBtn.setVisibility(View.GONE);
            isRunning = false;
        }
    }
 }
