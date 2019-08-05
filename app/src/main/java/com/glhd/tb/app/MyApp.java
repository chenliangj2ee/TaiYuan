package com.glhd.tb.app;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

public class MyApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();


        UMConfigure.init(getApplicationContext(), "5d381db60cafb2133200033a", "", 0, "pushSecret");

        MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
    }
}
