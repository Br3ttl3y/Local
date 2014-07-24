package com.austindroids.commuter.viewrecord;

/**
 * Created by markrebhan on 7/23/14.
 */
public class EventStartPause {

    RecordFragment.RecordingState state;

    public EventStartPause(RecordFragment.RecordingState state) {
        this.state = state;
    }

    public RecordFragment.RecordingState getState() {
        return state;
    }
}
