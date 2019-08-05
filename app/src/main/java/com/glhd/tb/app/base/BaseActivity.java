package com.glhd.tb.app.base;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.glhd.tb.app.API;
import com.glhd.tb.app.act.LoginActivity;
import com.glhd.tb.app.event.EventExit;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResUpgrade;
import com.glhd.tb.app.utils.MyLog;
import com.glhd.tb.app.utils.MySp;
import com.jaeger.library.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by chenliangj2ee on 2017/8/3.
 */

public class BaseActivity extends AppCompatActivity {
    public String TAG;
    public int width, height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
//        StatusBarUtil.setTransparent(this);
        width = getWindowManager().getDefaultDisplay().getWidth();
        height = getWindowManager().getDefaultDisplay().getHeight();
        hideSoftInput();
        TAG = this.getClass().getSimpleName();
        Log.i("MyActManager",TAG);
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eixt(EventExit event) {
        if (!TAG.equals(LoginActivity.class.getSimpleName())) {
            finish();
        }

    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    /**
     * 返回
     *
     * @param v
     */
    public void back(View v) {
        finish();
    }

    public void showFragment(Fragment f, int id) {
        FragmentTransaction mft = getSupportFragmentManager().beginTransaction();
        if (f.isAdded()) {
            mft.show(f);
        } else {
            mft.add(id, f);
        }
        mft.commitAllowingStateLoss();
    }

    public void hideFragment(Fragment f) {
        FragmentTransaction mft = getSupportFragmentManager().beginTransaction();
        if (f.isAdded()) {
            mft.hide(f);
        }
        mft.commitAllowingStateLoss();
    }


    public void startActivity(Class<?> cls) {
        if (cls == null)
            return;
        Intent intent = new Intent(this, cls);
        super.startActivity(intent);
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    private Fragment currentFragment;//当前显示的fragment

    /**
     * 打印log，通过tag：Railway12306
     *
     * @param message
     */
    public void log(String message) {
        MyLog.i(TAG, message);
    }

    public void replace(int id, Fragment f) {
        if (f == currentFragment)
            return;
        FragmentTransaction mft = getSupportFragmentManager().beginTransaction();
        if (currentFragment != null)
            mft.hide(currentFragment);
        if (f.isAdded()) {
            mft.show(f);
        } else {
            mft.add(id, f);
        }
        mft.commitAllowingStateLoss();
        currentFragment = f;
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 升级检查
     */
    public void upgrade() {

        API.upgrade(getVersionCode() + "", new MyHttp.ResultCallback<ResUpgrade>() {
            @Override
            public void onSuccess(ResUpgrade res) {
                if (res.getCode() == 0 && "0".equals(res.getUpgrade())) {

                }
            }

            @Override
            public void onError(String message) {

            }
        });


    }

    private int getVersionCode() {
        try {
            PackageInfo pi = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void showUpgradeDialog(final String url) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("升级提醒");
        dialog.setMessage("存在最新版本，是否更新？");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri apk_url = Uri.parse(url);
                intent.setData(apk_url);
                startActivity(intent);//打开浏览器

            }
        });
        dialog.setNegativeButton("取消", null);
        dialog.show();
    }

    public String getUserId() {
        return MySp.getUserId(this);
    }

    public int myY;


    public void setMyY(int myY) {
        this.myY = myY;
    }

    public int getMyY() {
        return myY;
    }

    public void refresh(final View view) {
        myY=0;
        MotionEvent e = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, width / 2, 0, 0);
        view.dispatchTouchEvent(e);

        ObjectAnimator animator = ObjectAnimator.ofInt(this, "myY", 0, 800);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                MotionEvent e = MotionEvent.obtain(SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(), MotionEvent.ACTION_MOVE,
                        width / 2, myY, 0);
                view.dispatchTouchEvent(e);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                MotionEvent e = MotionEvent.obtain(SystemClock.uptimeMillis(),SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, width / 2, 800, 0);
                view.dispatchTouchEvent(e);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();


    }
}
