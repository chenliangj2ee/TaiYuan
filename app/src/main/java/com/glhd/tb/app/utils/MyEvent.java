package com.glhd.tb.app.utils;

import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyEvent {

    public static void touch(final View view) {
        view.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis()  , MotionEvent.ACTION_DOWN, view.getX(), view.getY(), 0));
        view.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis()  , MotionEvent.ACTION_MOVE, view.getX()+100, view.getY() + 700, 0));
        view.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis()+800  , MotionEvent.ACTION_UP, view.getX()+100, view.getY() + 700, 0));
    }
}
