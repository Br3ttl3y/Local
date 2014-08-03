package com.austindroids.commuter.stopwatchmodel;

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

        pauseDuration += ntpTime.currentTimeMillis() - pause;
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
        duration = getDuration();
        start = 0;
        return duration;
    }

    public boolean notRunning() {
        return start == 0;
    }

    public boolean isRunning() {
        return start > 0;
    }

    public boolean notPaused() {
        return pause == 0;
    }

    public boolean isPaused() {
        return pause > 0;
    }


}
