package com.austindroids.commuter.model;

/**
 * Created by Brett on 7/13/2014.
 */

import android.content.Context;

import com.austindroids.commuter.time.NtpTime;

public class StopWatch {
    private long start, duration;
    private NtpTime ntpTime;

    public StopWatch(Context context) {
        start = duration = 0;
        ntpTime = NtpTime.getInstance(context);
    }

    private void setStart(long time) {
        start = time;
    }

    private void setDuration(long time)
    {
        duration = time;
    }

    public long GetDuration()
    {
        if(start == 0 ) return 0;
        return duration;
    }

    public long CurrentDuration() { return ntpTime.currentTimeMillis() - start;}

    public void Start(){
        if(isRunning()) return;
        setStart(ntpTime.currentTimeMillis());
    }

    public long Stop(){
        if(notRunning()) return 0;
        setDuration(CurrentDuration());
        setStart(0);
        return GetDuration();
    }

    private boolean notRunning(){ return start == 0; }
    private boolean isRunning(){ return !notRunning(); }
}
