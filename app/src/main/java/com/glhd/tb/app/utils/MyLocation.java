package com.glhd.tb.app.utils;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class MyLocation {

    public static String latitude;
    public static String longitude;
    public static BDLocation bdLocation;
    public LocationClient mLocationClient = null;

    public MyLocation(Context context) {
        mLocationClient = new LocationClient(context);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);
        option.setIsNeedAddress(true);
        option.setIsNeedAltitude(true);
        option.setWifiCacheTimeOut(5 * 60 * 1000);
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);


    }

    public void start(BDAbstractLocationListener listener) {
        mLocationClient.registerLocationListener(listener);
        mLocationClient.start();
    }

    public void stop() {
        mLocationClient.stop();
    }

}
