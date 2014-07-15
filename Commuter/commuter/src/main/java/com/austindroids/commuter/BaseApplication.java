package com.austindroids.commuter;

import android.app.Application;
import android.util.Log;

import com.austindroids.commuter.time.NtpTime;

public class BaseApplication extends Application {

    public static final String TAG = "BaseApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Base Application Created!");

        // initialize Ntp Time on application start
        NtpTime.getInstance(this).init();
    }
}
