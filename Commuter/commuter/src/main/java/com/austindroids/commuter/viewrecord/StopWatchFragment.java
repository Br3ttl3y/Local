package com.austindroids.commuter.viewrecord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.austindroids.commuter.BaseChildFragment;
import com.austindroids.commuter.R;
import com.austindroids.commuter.model.StopWatch;

/**
 * Created by markrebhan on 7/22/14.
 */
public class StopWatchFragment extends BaseChildFragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);

        totalTime = (TextView) view.findViewById(R.id.time_total);
        timeStopped = (TextView) view.findViewById(R.id.time_stopped);

        return view;
    }
}
