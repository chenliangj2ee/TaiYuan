package com.glhd.tb.app.utils;

import android.util.Log;

/**
 * Created by chenliangj2ee on 2018/4/11.
 */

public class MyLog {
    public static void i(String tag, String message){
        if(MyDevice.isApkDebugable()){
            Log.i("MyAndroid", tag + ": \n   " + message);
            Log.i(tag, message + "");
        }
    }
    public static void d(String tag, String message){
        if(MyDevice.isApkDebugable()){
            Log.d("MyAndroid", tag + ": \n   " + message);
            Log.i(tag, message + "");
        }
    }
    public static void e(String tag, String message){
        if(MyDevice.isApkDebugable()){
            Log.e("MyAndroid", tag + ": \n   " + message);
            Log.e(tag, message + "");
        }
    }
}
