package com.glhd.tb.app.act.inspection;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetRepair;
import com.glhd.tb.app.utils.MySp;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * 巡检反馈 通知人选项
 */
public class RepairUserActivity extends BaseActivity {


    private RadioGroup layout;
    private ArrayList<ResGetRepair.DataBean.RepairPersonnelBean> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_repair_user);
        layout = findViewById(R.id.layout);
        initView();
    }

    private ArrayList< ResGetRepair.DataBean.RepairPersonnelBean> results=new ArrayList<>();

    private void setData(final ArrayList<ResGetRepair.DataBean.RepairPersonnelBean> datas) {
    this.datas=datas;
        for (int i = 0; i < datas.size(); i++) {
            CheckBox button = new CheckBox(this);
            button.setPadding(0, 50, 0, 50);
            button.setText(datas.get(i).getName());
            layout.addView(button);
        }

    }

    private void initView() {

        API.getRepair(MySp.getUser(this).getAccountId(),new MyHttp.ResultCallback<ResGetRepair>() {
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

            for(int i=0;i<layout.getChildCount();i++){
                CheckBox box= (CheckBox) layout.getChildAt(i);
                if(box.isChecked()){
                    results.add(datas.get(i));
                }
            }

            if(results.size()>0) {
                ResGetRepair.DataBean.RepairPersonnelBean bean=new ResGetRepair.DataBean.RepairPersonnelBean();
                bean.users=results;
                EventBus.getDefault().post(bean);
            }
            finish();

    }
}
