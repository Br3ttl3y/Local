package com.austindroids.commuter.time;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

/**
 * contacts the NTP com.austindroids.commuter.time servers to force all devices to use NTP com.austindroids.commuter.time or fallback to when the
 * NTP com.austindroids.commuter.time was last available. Stock Android uses network com.austindroids.commuter.time protocol, but OEM's can modify the
 * servers its hitting to get the com.austindroids.commuter.time. We want the com.austindroids.commuter.time values to be as consistent as possible.
 */
public class NtpTime {

    private static final String TAG = "NtpTime";

    private static final String SERVER = "2.android.pool.ntp.org";
    private static final int TIMEOUT = 20000;
    private static final String PREF_NAME = "ntpTime";
    private static final String PREF_LAST_SYS_CLOCK_DIFF = "lastSysClockDiff";

    private static NtpTime instance;
    private Context context;

    private boolean hasNptTime;
    private long cachedTime;
    private long cachedElapsedRealTime;

    private MyNptTimeTask task;

    private NtpTime(Context context) {
        this.context = context;
        Log.d(TAG, "creating NtpTime using " + SERVER);
        initFallback();
    }

    public static synchronized NtpTime createInstance(Context context) {
        if (instance == null) {
            instance = new NtpTime(context);
        } else {
            Log.e(TAG, "already created an instance, returning already created one.");
        }

        return instance;
    }

    public static synchronized NtpTime getInstance() {
        if (instance == null) {
            Log.e(TAG, "no instance has been created! create one before calling this!");
            return null;
        }
        return instance;
    }

    public void init() {
        if (!hasNptTime && task == null) {
            task = new MyNptTimeTask();
            task.execute();
        }
    }

    public void initFallback() {
        SharedPreferences preferences = getSharedPreferences();
        long lastSysClockDiff = preferences.getLong(PREF_LAST_SYS_CLOCK_DIFF, 0);
        hasNptTime = false;
        // calculate the best guess for npt com.austindroids.commuter.time
        cachedTime = System.currentTimeMillis() - lastSysClockDiff;
        // take the elapsed system clock for reference
        cachedElapsedRealTime = SystemClock.elapsedRealtime();
    }

    /**
     * Grab the current npt com.austindroids.commuter.time from the client
     */
    protected boolean ntpNetworkRefresh() {
        final SntpClient client = new SntpClient();
        if (client.requestTime(SERVER, TIMEOUT)) {
            hasNptTime = true;
            cachedTime = client.getNtpTime();
            cachedElapsedRealTime = client.getNtpTimeReference();

            // save the clock diff now that we have the real ntp com.austindroids.commuter.time in case we need to use the fallback later
            long sysClockDiff = System.currentTimeMillis() - currentTimeMillis();
            SharedPreferences.Editor editor = getSharedPreferences().edit();
            editor.putLong(PREF_LAST_SYS_CLOCK_DIFF, sysClockDiff);
            editor.commit();

            return true;
        }

        return false;
    }

    /**
     * Get the system com.austindroids.commuter.time in milliseconds since Jan 1, 1970 based on NTP com.austindroids.commuter.time if the server is
     * reachable, otherwise fallback is to use the system milli since the last ntp com.austindroids.commuter.time post
     */
    public long currentTimeMillis() {
        return cachedTime + (SystemClock.elapsedRealtime() - cachedElapsedRealTime);
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    private class MyNptTimeTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                boolean success = instance.ntpNetworkRefresh();
                long diff = System.currentTimeMillis() - instance.currentTimeMillis();
                Log.d(TAG, "NTP network call success= " + success + " difference = " + diff);
            } catch (Exception e) {
                Log.d(TAG, "Error getting NTP com.austindroids.commuter.time");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            task = null;
        }
    }

}
