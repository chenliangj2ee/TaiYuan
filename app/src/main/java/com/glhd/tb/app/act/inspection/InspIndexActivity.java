package com.glhd.tb.app.act.inspection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.glhd.tb.app.R;
import com.glhd.tb.app.act.InspMyActivity;
import com.glhd.tb.app.base.BaseActivity;

public class InspIndexActivity extends BaseActivity implements View.OnClickListener {


    protected RadioButton tab01;
    protected RadioButton tab02;
    protected LinearLayout container;
    protected LinearLayout myLayout;
    protected LinearLayout historyLayout;

    private TodayInspFragment todayInspFragment;//今日巡检
    private UpAdsConstructionFragment upAdsConstructionFragment;//上刊施工

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_insp_index);
        initFragment();
        initView();
        upgrade();
    }

    private void initView() {
        tab01 = (RadioButton) findViewById(R.id.tab01);
        tab01.setOnClickListener(InspIndexActivity.this);
        tab02 = (RadioButton) findViewById(R.id.tab02);
        tab02.setOnClickListener(InspIndexActivity.this);
        container = (LinearLayout) findViewById(R.id.container);
        myLayout = (LinearLayout) findViewById(R.id.my_Layout);
        historyLayout = (LinearLayout) findViewById(R.id.history_layout);
        tab01.setChecked(true);
        onClick(tab01);

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
        Intent intent = new Intent(this, InspFilterActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        todayInspFragment.onActivityResult(requestCode, resultCode, data);
    }
}