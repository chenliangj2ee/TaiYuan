package com.glhd.tb.app.act.inspection;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.glhd.tb.app.R;
import com.glhd.tb.app.act.InspMyActivity;
import com.glhd.tb.app.act.repair.RepairListFragment;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.utils.MyLocation;
import com.glhd.tb.app.utils.MyLog;

public class InspRepairIListActivity extends BaseActivity   {

    private RepairListFragment noRepairFragment;//未修

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_insp_repair_list);
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
        replace(R.id.content, noRepairFragment);

    }

    private void initFragment() {
        noRepairFragment = new RepairListFragment(-1);
    }


}
