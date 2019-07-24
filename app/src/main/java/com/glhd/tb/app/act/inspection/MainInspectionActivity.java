package com.glhd.tb.app.act.inspection;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.glhd.tb.app.R;
import com.glhd.tb.app.act.MyFragment;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.event.EventRefreshInspHistoryLayout;
import com.glhd.tb.app.utils.MyLog;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.util.HashMap;

public class MainInspectionActivity extends BaseActivity {

    private HashMap<String, Fragment> fs = new HashMap<>();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            replaceFragment(fs.get(item.getTitle()));
            return true;
        }
    };

    /**
     * 1、初始化Wifi、休闲娱乐、个人中心
     * 2、默认打开wifi Tab
     */
    private void initFragment() {
        InspRangeActivity.isSelect = false;
        fs.put("巡检", new IndexFragment());
        fs.put("历史", new InspHistoryFragment());
        fs.put("我的", new MyFragment());

        replaceFragment(fs.get("巡检"));//默认选中Wifi
    }

    private void replaceFragment(Fragment f) {
        replace(R.id.contentContainer, f);//默认选中WIFI tab
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_inspection);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        disableShiftMode(navigation);
        initFragment();
    }

    public void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }
    }


    int downY = 0;
    int upY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {


        if (ev.getAction() == KeyEvent.ACTION_DOWN) {
            downY = (int) ev.getY();
        }

        if (ev.getAction() == KeyEvent.ACTION_UP) {
            upY = (int) ev.getY();

            if (upY - downY > 100) {
                EventRefreshInspHistoryLayout e = new EventRefreshInspHistoryLayout();
                e.show = true;
                EventBus.getDefault().post(e);
            }
            if (upY - downY < -100) {
                EventRefreshInspHistoryLayout e = new EventRefreshInspHistoryLayout();
                e.show = false;
                EventBus.getDefault().post(e);
            }

            MyLog.i("chenliangtag","(upY - downY:"+(upY - downY));
        }


        return super.dispatchTouchEvent(ev);
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        PackageManager pm = getPackageManager();
        ResolveInfo homeInfo =
                pm.resolveActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME), 0);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ActivityInfo ai = homeInfo.activityInfo;
            Intent startIntent = new Intent(Intent.ACTION_MAIN);
            startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            startIntent.setComponent(new ComponentName(ai.packageName, ai.name));
            startActivity(startIntent);
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }
}
