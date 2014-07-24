package com.austindroids.commuter.model;

/**
 * Created by Brett on 7/13/2014.
 */

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.austindroids.commuter.time.NtpTime;

public class StopWatch {

    private long start;
    private long duration;
    private NtpTime ntpTime;

    public StopWatch(Context context) {
        ntpTime = NtpTime.getInstance(context);
    }

    public long getStart() {
        return start;
    }

    public long getDuration() {
        return duration;
    }

    public long currentDuration() {
        return ntpTime.currentTimeMillis() - start;
    }

    public void start() {
        if (isRunning()) {
            return;
        }
        start = ntpTime.currentTimeMillis();
    }

    //TODO figure out how to pause
    public long pause() {

        return 0;
    }

    public long stop() {
        if (notRunning()) {
            return 0;
        }
        duration = currentDuration();
        start = 0;
        return duration;
    }

    private boolean notRunning() {
        return start == 0;
    }

    private boolean isRunning() {
        return start > 0;
    }
}
