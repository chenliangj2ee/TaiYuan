package com.glhd.tb.app.act.inspection;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetRepair;
import com.glhd.tb.app.utils.MyLog;
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;
import com.glhd.tb.app.view.TreeViewLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * 巡检反馈  维修人选项
 */
public class NotiUserActivity extends BaseActivity {


    protected TreeViewLayout tree;
    ArrayList<ResGetRepair.DataBean.ViewStaffBean> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_insp_noti_user);
        initView();
    }


    private void initView() {
        tree = (TreeViewLayout) findViewById(R.id.tree);

        API.getRepair(MySp.getUser(this).getAccountId(),new MyHttp.ResultCallback<ResGetRepair>() {
            @Override
            public void onSuccess(ResGetRepair res) {
                if (res.getCode() == 0) {
                    datas = res.getData().getViewStaff();
                    tree.setData(datas);
                }
            }

            @Override
            public void onError(String message) {

            }
        });
        tree.setOnClick(new TreeViewLayout.OnClickListener() {
            @Override
            public void click(ResGetRepair.DataBean.ViewStaffBean data) {
              if(data.isCheck()==false) {
                  for (int i = 0; i < result.size(); i++) {
                      if (data.getId().equals(result.get(i).getId())) {
                          result.remove(i);
                          break;
                      }
                  }
              }else{
                  result.add(data);
              }

            }
        });

    }
    ArrayList<ResGetRepair.DataBean.ViewStaffBean> result = new ArrayList<>();
    public void ok(View view) {


        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).isCheck()) {
                result.add(datas.get(i));
            }
        }

        if (result.size() > 0) {
            EventBus.getDefault().post(result);
            finish();
        }
    }
}
