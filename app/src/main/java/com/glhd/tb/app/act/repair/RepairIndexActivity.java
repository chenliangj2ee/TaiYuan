package com.glhd.tb.app.act.repair;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.glhd.tb.app.R;
import com.glhd.tb.app.act.inspection.InspIndexActivity;
import com.glhd.tb.app.base.BaseActivity;

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
        initView();
        initFragment();
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
            myLayout.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.tab02) {
            replace(R.id.content, yesRepairFragment);
            myLayout.setVisibility(View.GONE);
        }
    }

}
