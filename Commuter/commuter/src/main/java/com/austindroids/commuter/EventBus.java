package com.austindroids.commuter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.util.Objects;

/**
 * Class that extends basic functionality of the event but to be used to register events on a
 * non-main thread and passing the message to the main thread via a handler.
 */
public class EventBus extends Bus {

    private static final String TAG = "EventBus";

    private static final int POST_MSG = 0;
    private static final int REGISTER_MSG = 1;

    private static EventBus instance;

    private Handler handler;

    // use a singleton pattern for event bus.
    public static EventBus getInstance() {
        if (instance == null) {
            instance = new EventBus();
        }

        return instance;
    }

    private EventBus() {
        this(ThreadEnforcer.MAIN);
    }

    // For registering async bus events.
    private EventBus(ThreadEnforcer enforcer) {
        super(enforcer, "EventBus");
        this.handler = new MyAsyncHandler(this);
    }

    // pass the register signal and object to the Handler
    public void registerAsync(final Object object) {
        Message message = handler.obtainMessage(REGISTER_MSG, object);
        handler.sendMessage(message);
    }

    //  pass the post signal and the event to the handler
    public void postAsync(Object event) {
        Message message = handler.obtainMessage(POST_MSG, event);
        handler.sendMessage(message);
    }

    // inner class that handles running event but actions on a different thread on the main looper.
    protected static class MyAsyncHandler extends Handler {

        private EventBus eventBus;

        public MyAsyncHandler(EventBus eventBus) {
            super(Looper.getMainLooper());
            this.eventBus = eventBus;
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == POST_MSG) {
                eventBus.post(msg.obj);
            } else if (msg.what == REGISTER_MSG) {
                eventBus.register(msg.obj);
            } else {
                Log.d(TAG, "unknown message type");
            }
        }
    }

}
