package com.austindroids.commuter.stopwatchmodel;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Manager that manages running a number of stop watch instances on separate thread and posting
 * data to the main thread via handlers.
 *
 * SINGLETON
 */
public class StopWatchThreadManager {

    private static final String TAG = "StopWatchThreadManager";

    private static final int MSG_TIMER = 0;

    protected static StopWatchThreadManager instance;
    private StopWatchManager stopWatchManager;

    ExecutorService service = Executors.newCachedThreadPool();

    // maintain a map of future object references so we can unregister then later;
    private Hashtable<String, Future<?>> threadPoolMap = new Hashtable<String, Future<?>>();

    // maintain a map of boolean values to check to see if certain stopwatch listener threads are running.
    private Hashtable<String, Boolean> isThreadRunningMap = new Hashtable<String, Boolean>();

    private Hashtable<String, MyRecordHandler> handlerMap = new Hashtable<String, MyRecordHandler>();

    private StopWatchThreadManager() {
        stopWatchManager = StopWatchManager.getInstance();
    }

    public static StopWatchThreadManager getInstance() {
        if (instance == null) {
            instance = new StopWatchThreadManager();
        }

        return instance;
    }

    public void registerListener(final String tag, final long refreshRate, final OnGetStopWatchTime callback) {

        Log.d(TAG, "listener Registered!");

        final StopWatch stopWatch = stopWatchManager.getStopWatch(tag);
        handlerMap.put(tag, new MyRecordHandler(callback));
        isThreadRunningMap.put(tag, true);

        final Future<?> future = service.submit(new Runnable() {
            @Override
            public void run() {
                while (isThreadRunningMap.get(tag)) {
                    if (stopWatch.notPaused() && stopWatch.isRunning()) {
                        MyRecordHandler handler = handlerMap.get(tag);
                        Message message = handler.obtainMessage(MSG_TIMER, stopWatch.getDuration());
                        handler.sendMessage(message);
                    }
                    try {
                        Thread.sleep(refreshRate);
                    } catch (InterruptedException e) {
                        threadPoolMap.remove(tag);
                    }
                }
            }
        });

        threadPoolMap.put(tag, future);

    }

    public void unRegisterListener(String tag) {

        Log.d(TAG, "listener UnRegistered!");

        if (tag == null || !threadPoolMap.containsKey(tag)) {
            return;
        }

        isThreadRunningMap.put(tag, false);
        threadPoolMap.remove(tag);
        handlerMap.remove(tag);
    }


    private class MyRecordHandler extends Handler {

        OnGetStopWatchTime callback;

        protected MyRecordHandler(OnGetStopWatchTime callback) {
            this.callback = callback;
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_TIMER) {
                callback.onGetCurrentTime((Long) msg.obj);
            } else {
                Log.d(TAG, "unknown message type");
            }
        }
    }

}
