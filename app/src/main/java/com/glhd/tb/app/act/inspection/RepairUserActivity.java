package com.glhd.tb.app.act.inspection;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetRepair;
import com.glhd.tb.app.view.TreeViewLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * 巡检反馈
 */
public class RepairUserActivity extends BaseActivity {


    private RadioGroup layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_repair_user);
        layout = findViewById(R.id.layout);
        initView();
    }

    private ResGetRepair.DataBean.RepairPersonnelBean result;

    private void setData(final ArrayList<ResGetRepair.DataBean.RepairPersonnelBean> datas) {

        for (int i = 0; i < datas.size(); i++) {
            if (i == 0)
                result = datas.get(i);
            RadioButton button = new RadioButton(this);
            button.setPadding(0, 50, 0, 50);
            button.setText(datas.get(i).getName());
            layout.addView(button);
            final int index=i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    result=datas.get(index);
                }
            });
        }

    }

    private void initView() {

        API.getRepair(new MyHttp.ResultCallback<ResGetRepair>() {
            @Override
            public void onSuccess(ResGetRepair res) {
                if (res.getCode() == 0) {
                    setData(res.getData().getRepairPersonnel());
                }
            }

            @Override
            public void onError(String message) {

            }
        });

    }

    public void ok(View view) {
        if (result != null) {
            EventBus.getDefault().post(result);
            finish();
        }

    }
}
