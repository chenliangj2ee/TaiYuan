package com.glhd.tb.app.camera.jcamera.listener;

import android.graphics.Bitmap;

public interface JCameraListener {

    void captureSuccess(Bitmap bitmap);

    void recordSuccess(String url, Bitmap firstFrame, int[] videoSize);

}
