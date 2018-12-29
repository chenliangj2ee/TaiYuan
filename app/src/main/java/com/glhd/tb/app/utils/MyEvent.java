package com.glhd.tb.app.utils;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;

public class MyEvent {

    public static void touch(final View view) {
        final int width = view.getWidth();
        final int height = view.getHeight();
        MotionEvent e = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis() + 10, MotionEvent.ACTION_DOWN, width / 2, height / 2, 0);
        view.dispatchTouchEvent(e);
        e = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis() + 10, MotionEvent.ACTION_MOVE, width / 2, height / 2 + 400, 0);
        view.dispatchTouchEvent(e);
        e = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis() + 10, MotionEvent.ACTION_UP, width / 2, height / 2 + 400, 0);
        view.dispatchTouchEvent(e);
    }
}
