package com.glhd.tb.app.act.inspection;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.glhd.tb.app.R;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.event.EventInspFilter;
import com.glhd.tb.app.utils.MyToast;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InspRangeActivity extends BaseActivity implements View.OnClickListener {


    protected LinearLayout dateLayout;
    protected ImageView back;
    private TabLayout tabLayout;
    private List<Fragment> fragmentList;
    private Button btnStartTime;
    private Button btnStopTime;
    private int FLAG = 0;
    private boolean showDate;

    private String startDate, endDate;
    private String taskState = "0";
    public static boolean isSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insp_range);
        showDate = getIntent().getBooleanExtra("showDate", false);
        taskState = getIntent().getStringExtra("taskState");
        initView();
        initEvent();
        initTimePicker();
        initFragment();

    }

    /**
     * 初始化视图
     */
    private void initView() {
        tabLayout = findViewById(R.id.myTab);
        btnStartTime = findViewById(R.id.btn_start_time);
        btnStopTime = findViewById(R.id.btn_stop_time);
        btnStartTime.setOnClickListener(this);
        btnStopTime.setOnClickListener(this);

        tabLayout.addTab(tabLayout.newTab().setText("车站"));
        tabLayout.addTab(tabLayout.newTab().setText("列车"));
        dateLayout = (LinearLayout) findViewById(R.id.date_layout);

        dateLayout.setVisibility(showDate ? View.VISIBLE : View.GONE);
        back = (ImageView) findViewById(R.id.back);

        if (InspRangeActivity.isSelect == false && showDate == false) {
            back.setVisibility(View.GONE);
        }
    }

    /**
     * 添加事件
     */
    private void initEvent() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switchFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    StationFragment stationFragment;
    TrainFragment trainFragment;

    /**
     * 初始化fragment
     */
    private void initFragment() {
        fragmentList = new ArrayList<>();
        stationFragment = new StationFragment();
        stationFragment.setTaskState(taskState);
        trainFragment = new TrainFragment();
        trainFragment.setTaskState(taskState);
        fragmentList.add(stationFragment);
        fragmentList.add(trainFragment);
        switchFragment(0);
    }

    /**
     * 点击车站或者列车按钮切换fragment
     *
     * @param position
     */
    public void switchFragment(int position) {
        //开启事务
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //遍历集合
        for (int i = 0; i < fragmentList.size(); i++) {
            Fragment fragment = fragmentList.get(i);
            if (i == position) {
                //显示fragment
                if (fragment.isAdded()) {
                    //如果这个fragment已经被事务添加,显示
                    fragmentTransaction.show(fragment);
                } else {
                    //如果这个fragment没有被事务添加过,添加
                    fragmentTransaction.add(R.id.fl_home, fragment);
                }
            } else {
                //隐藏fragment
                if (fragment.isAdded()) {
                    //如果这个fragment已经被事务添加,隐藏
                    fragmentTransaction.hide(fragment);
                }
            }
        }
        //提交事务
        fragmentTransaction.commit();
    }

    private TimePickerView pvTime;

    private void initTimePicker() {//Dialog 模式下，在底部弹出

        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Log.i("pvTime", "onTimeSelect");
                String currentTime = getTime(date);
                if (FLAG == 0) {
                    btnStartTime.setText(currentTime);
                    startDate = currentTime;
                } else if (FLAG == 1) {
                    btnStopTime.setText(currentTime);
                    endDate = currentTime;
                }
            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setRangDate(getStartTime(2018, 6), getEndTime(2100, 11))
                .setDate(Calendar.getInstance())
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private Calendar getStartTime(int year, int monthOfYear) {
        Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        return cal;
    }

    private Calendar getEndTime(int year, int monthOfYear) {
        Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        return cal;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_time:
                FLAG = 0;
                break;
            case R.id.btn_stop_time:
                FLAG = 1;
                break;
        }
        if (pvTime != null) {
            pvTime.show();
        }
    }


    public void okAction(View v) {
        if (showDate == false) {
            if ("".equals(stationFragment.stationId) && "".equals(trainFragment.train)) {
                MyToast.showMessage(this, "请选择巡检范围");
                return;
            }
        }
        if (0 == tabLayout.getSelectedTabPosition()) {
            EventInspFilter event = new EventInspFilter();
            event.stationId = stationFragment.stationId;
            event.floorId = stationFragment.floorId;
            event.locationId = stationFragment.locationId;
            event.regionId = stationFragment.regionId;
            event.stationName = stationFragment.stationName;
            event.floorName = stationFragment.floorName;
            event.locationName = stationFragment.locationName;
            event.regionName = stationFragment.regionName;
            event.startDate = startDate;
            event.endDate = endDate;
            event.isHistory = showDate;

            EventBus.getDefault().post(event);
        } else {
            EventInspFilter event = new EventInspFilter();
            event.train = trainFragment.train;
            event.trainNo = trainFragment.trainNo;
            event.isHistory = showDate;
            event.trainName = trainFragment.trainName;
            event.trainNoName = trainFragment.trainNoName;
            event.startDate = startDate;
            event.endDate = endDate;
            EventBus.getDefault().post(event);
        }
        InspRangeActivity.isSelect = true;
        finish();

    }


    public void back(View view) {
        if (InspRangeActivity.isSelect == false && showDate == false) {
            MyToast.showMessage(this, "请选择巡检范围");
            return;
        }
        finish();
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    if (InspRangeActivity.isSelect == false && showDate == false) {
                        MyToast.showMessage(this, "请选择巡检范围");
                        return true;
                    }
                    finish();
                    return false;

            }
        }
        return super.dispatchKeyEvent(event);
    }

}
