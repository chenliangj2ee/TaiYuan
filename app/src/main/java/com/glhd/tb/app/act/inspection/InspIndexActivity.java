package com.glhd.tb.app.act.inspection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.glhd.tb.app.R;
import com.glhd.tb.app.act.InspMyActivity;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.bean.BeanUser;
import com.glhd.tb.app.utils.MyLocation;
import com.glhd.tb.app.utils.MyLog;
import com.glhd.tb.app.utils.MySp;


public class InspIndexActivity extends BaseActivity implements View.OnClickListener {


    protected RadioButton tab01;
    protected RadioButton tab02;
    protected LinearLayout container;
    protected LinearLayout myLayout;
    protected LinearLayout historyLayout;

    private TodayInspFragment todayInspFragment;//今日巡检
    private UpAdsConstructionFragment upAdsConstructionFragment;//上刊施工
    private RadioGroup radioGroup;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_insp_index);
        initFragment();
        initView();
        upgrade();
        startLocation();
    }


    /**
     * 启动定位
     */
    private void startLocation() {
        final MyLocation location = new MyLocation(getApplicationContext());
        location.start(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {

                MyLog.i("", "------------------------------------------------");
                MyLocation.latitude = bdLocation.getLatitude() + "";
                MyLocation.longitude = bdLocation.getLongitude() + "";

                MyLog.i("", "latitude:" + MyLocation.latitude);
                MyLog.i("", "longitude:" + MyLocation.longitude);

                location.stop();

            }


        });
    }

    private void initView() {
        title = findViewById(R.id.title);
        radioGroup = findViewById(R.id.radiogroup);
        tab01 = (RadioButton) findViewById(R.id.tab01);
        tab01.setOnClickListener(InspIndexActivity.this);
        tab02 = (RadioButton) findViewById(R.id.tab02);
        tab02.setOnClickListener(InspIndexActivity.this);
        container = (LinearLayout) findViewById(R.id.container);
        myLayout = (LinearLayout) findViewById(R.id.my_Layout);
        historyLayout = (LinearLayout) findViewById(R.id.history_layout);
        tab01.setChecked(true);
        onClick(tab01);

        BeanUser user = MySp.getUser(this);
        if ("U02".equals(user.getType())) {
            radioGroup.setVisibility(View.GONE);
            title.setText("巡检列表");
        } else if ("U05".equals(user.getType())) {
            radioGroup.setVisibility(View.GONE);
            replace(R.id.content, upAdsConstructionFragment);
            historyLayout.setVisibility(View.GONE);
            title.setText("上刊列表");
        } else {
            title.setText("");
        }


    }

    private void initFragment() {
        todayInspFragment = new TodayInspFragment();
        upAdsConstructionFragment = new UpAdsConstructionFragment();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tab01) {
            replace(R.id.content, todayInspFragment);
            myLayout.setVisibility(View.VISIBLE);
            historyLayout.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.tab02) {
            replace(R.id.content, upAdsConstructionFragment);
            myLayout.setVisibility(View.GONE);
            historyLayout.setVisibility(View.GONE);
        }
    }


    public void myAction(View view) {
        startActivity(InspMyActivity.class);

    }

    public void historyAction(View view) {
        Intent intent = new Intent(this, SelectStationActivity.class);
        intent.putExtra("fromActivity", getClass().getSimpleName());
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        todayInspFragment.onActivityResult(requestCode, resultCode, data);
    }
}