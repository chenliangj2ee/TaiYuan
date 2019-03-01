package com.glhd.tb.app.act.repair;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.glhd.tb.app.R;
import com.glhd.tb.app.act.InspMyActivity;
import com.glhd.tb.app.act.inspection.InspIndexActivity;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.utils.MyLocation;
import com.glhd.tb.app.utils.MyLog;
import com.glhd.tb.app.utils.MyVideo;

public class RepairIndexActivity extends BaseActivity implements View.OnClickListener {
    protected RadioButton tab01;
    protected RadioButton tab02;
    protected LinearLayout container;
    protected LinearLayout myLayout;


    private RepairListFragment noRepairFragment;//未修
    private RepairListFragment yesRepairFragment;//已修

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_repair_index);
        initFragment();
        initView();
        startLocation();
    }

    /**
     * 启动定位
     */
    private void startLocation(){
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
        tab01 = (RadioButton) findViewById(R.id.tab01);
        tab01.setOnClickListener(this);
        tab02 = (RadioButton) findViewById(R.id.tab02);
        tab02.setOnClickListener(this);
        container = (LinearLayout) findViewById(R.id.container);
        myLayout = (LinearLayout) findViewById(R.id.my_Layout);
        tab01.setChecked(true);
        onClick(tab01);

    }

    private void initFragment() {
        noRepairFragment = new RepairListFragment(0);
        yesRepairFragment = new RepairListFragment(1);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tab01) {
            replace(R.id.content, noRepairFragment);
        } else if (view.getId() == R.id.tab02) {
            replace(R.id.content, yesRepairFragment);
        }
    }

    public void myAction(View view) {
        startActivity(InspMyActivity.class);

    }
}
