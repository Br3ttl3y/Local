package com.austindroids.commuter.viewrecord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.austindroids.commuter.BaseChildFragment;
import com.austindroids.commuter.BaseFragment;
import com.austindroids.commuter.R;

/**
 * Created by markrebhan on 7/22/14.
 */
public class FinishButtonFragment extends BaseChildFragment {

    Button finishBtn;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_finish_button;
    }

    @Override
    public int getContainerViewId() {
        return R.id.frame_top;
    }

    public static FinishButtonFragment newInstance() {
        return new FinishButtonFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);

        finishBtn = (Button) view.findViewById(R.id.button_finish);
        finishBtn.setOnClickListener(new OnFinishButtonClicked());

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

    private class OnFinishButtonClicked implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            eventBus.post(new EventStartPause(RecordFragment.RecordingState.FINISHED));
        }
    }
}
