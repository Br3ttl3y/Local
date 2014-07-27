package com.austindroids.commuter.model;

/**
 * Created by Brett on 7/13/2014.
 */

import android.content.Context;

import com.austindroids.commuter.time.NtpTime;

public class StopWatch {

    private long start;
    private long duration;
    private long pause;
    private long pauseDuration;
    private NtpTime ntpTime;

    public StopWatch() {
        ntpTime = NtpTime.getInstance();
    }

    public long getStart() {
        return start;
    }

    public long getDuration() {
        return currentDuration();
    }

    public long currentDuration() {
        return ntpTime.currentTimeMillis() - start - pauseDuration;
    }

    public void start() {
        if (isRunning()) {
            return;
        }
        start = ntpTime.currentTimeMillis();
    }

    public void resume() {
        if (notPaused() || notRunning()) {
            return;
        }

        pauseDuration = ntpTime.currentTimeMillis() - pause;
        pause = 0;
    }

    public void pause() {
        if (isPaused() || notRunning()) {
            return;
        }
        pause = ntpTime.currentTimeMillis();
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

    private boolean notPaused() {
        return pause == 0;
    }

    private boolean isPaused() {
        return pause > 0;
    }
}
