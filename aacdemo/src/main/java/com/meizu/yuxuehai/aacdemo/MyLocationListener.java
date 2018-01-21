package com.meizu.yuxuehai.aacdemo;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;

/**
 * Created by yuxuehai on 18-1-21.
 */

public class MyLocationListener implements LifecycleObserver {

    private boolean enabled = false;
    private Lifecycle mLifecycle;

    public MyLocationListener(Context context, Lifecycle lifecycle, LocationCallback callback) {
        this.mLifecycle = lifecycle;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start() {
        if (enabled) {
            // connect
        }
    }

    public void enable() {
        enabled = true;
        if (mLifecycle.getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            // connect if not connected
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stop() {
        // disconnect if connected
    }


}
