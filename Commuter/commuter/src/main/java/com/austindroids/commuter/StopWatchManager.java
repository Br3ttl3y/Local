package com.austindroids.commuter;

import android.util.Pair;

import com.austindroids.commuter.model.StopWatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to manage multiple stopwatch implementations
 */
public class StopWatchManager {

    private static StopWatchManager instance;

    // store stopwatches in here with string identifier
    private HashMap<String, StopWatch> map = new HashMap<String, StopWatch>();

    private StopWatchManager() {
    }

    public static StopWatchManager getInstance() {
        if (instance == null) {
            instance = new StopWatchManager();
        }

        return instance;
    }

    public void createStopWatch(String tag) {

        if (tag == null) {
            throw new NullPointerException("tag cannot be null");
        } else if (map.containsKey(tag)) {
            throw new IllegalStateException("tag " + tag + "already exists in map!");
        }

        StopWatch stopWatch = new StopWatch();
        map.put(tag, stopWatch);
    }

    public void removeStopWatch(String tag) {
        map.remove(tag);
    }

    public StopWatch getStopWatch(String tag) {
        return map.get(tag);
    }

    public void startStopwatch(String tag) {
        map.get(tag).start();
    }

    public void pauseStopWatch(String tag) {
        map.get(tag).pause();
    }

    public void resumeStopWatch(String tag) {
        map.get(tag).resume();
    }

    public long stopStopWatch(String tag) {
        return map.get(tag).stop();
    }

    public void startAllStopWatches() {
        for (StopWatch stopWatch: map.values()) {
            stopWatch.start();
        }
    }

    public List<Pair<String, Long>> stopAllStopWatches() {
        List<Pair<String, Long>> finalValues = new ArrayList<Pair<String, Long>>();
        for (Map.Entry entry: map.entrySet()) {
            StopWatch stopWatch = (StopWatch) entry.getValue();
            finalValues.add(new Pair<String, Long>(entry.getKey().toString(), stopWatch.stop()));
        }

        return finalValues;
    }


}
