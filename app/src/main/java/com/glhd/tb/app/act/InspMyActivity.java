package com.glhd.tb.app.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.act.inspection.InspHistoryActivity;
import com.glhd.tb.app.act.inspection.InspRepairIListActivity;
import com.glhd.tb.app.act.inspection.InspWayActivity;
import com.glhd.tb.app.act.inspection.MainInspectionActivity;
import com.glhd.tb.app.act.repair.RepairIndexActivity;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.BaseRes;
import com.glhd.tb.app.base.bean.BeanUser;
import com.glhd.tb.app.event.EventExit;
import com.glhd.tb.app.event.EventInspFilter;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.utils.MySp;

import org.greenrobot.eventbus.EventBus;

public class InspMyActivity extends BaseActivity {

    protected TextView type;
    protected TextView num;
    protected Button inspHistory;
    protected Button repairHistory;
    protected Button waySearch;
    protected LinearLayout moreType;
    private TextView name;
    private TextView conpany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_insp_my);


        initView();
        initInspNum();
    }

    public void userInfoAction(View view) {
        startActivity(UserInfoActivity.class);
    }

    /**
     * 交路查询
     * @param view
     */
    public void waySearchAction(View view) {
        startActivity(InspWayActivity.class);
    }

    public void resetPasswordAction(View view) {
        startActivity(ResetPasswordActivity.class);
    }

    public void exitAction(View view) {
        BeanUser user = MySp.getUser(this);
        user.setLogin(false);
        MySp.setUser(this, user);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(LoginActivity.class);
        EventBus.getDefault().post(new EventExit());
    }


    public void inspHistoryAction(View view) {
        startActivity(InspHistoryActivity.class);
    }

    public void repairHistoryAction(View view) {
        startActivity(InspRepairIListActivity.class);
    }

    private void initView() {
        name = (TextView) findViewById(R.id.name);
        conpany = (TextView) findViewById(R.id.conpany);
        type = (TextView) findViewById(R.id.type);
        num = (TextView) findViewById(R.id.num);
        inspHistory = (Button) findViewById(R.id.insp_history);
        waySearch = (Button) findViewById(R.id.way_search);//交路查询
        repairHistory = (Button) findViewById(R.id.repair_history);
        moreType = (LinearLayout) findViewById(R.id.more_type);
        final BeanUser user = MySp.getUser(this);

        if (user != null) {
            name.setText(user.getName());
            conpany.setText(user.getCompany());
            String curType = "";

            if (user.getType().contains(",")) {
                curType = user.getCurType();
            } else {
                curType = user.getType();
            }

            if ("U02".equals(curType)) {
                name.setText(user.getName() + "(巡检员)");
                findViewById(R.id.insp_history).setVisibility(View.VISIBLE);
                findViewById(R.id.repair_history).setVisibility(View.VISIBLE);
            }
            if ("U04".equals(curType)) {
                name.setText(user.getName() + "(维修员)");
            }


            if (user.getType().contains(",")) {
                moreType.setVisibility(View.VISIBLE);

                if ("U02".equals(user.getCurType())) {
                    type.setText("转维修");
                    num.setText("（共有" + user.getRepairNum() + "项维修任务待完成）");
                    type.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            EventBus.getDefault().post(new EventExit());

                            Intent intent = new Intent(getApplicationContext(), RepairIndexActivity.class);
                            intent.putExtra("fromActivity", getClass().getSimpleName());
                            intent.putExtra("nodate", true);
                            startActivity(intent);
                            user.setLogin(true);
                            user.setCurType("U04");
                            MySp.setUser(getApplicationContext(), user);//缓存用户信息
                            finish();
                        }
                    });
                }
                if ("U04".equals(user.getCurType())) {
                    type.setText("转巡检");
                    num.setText("（共有" + user.getInspNum() + "项巡检任务待完成）");
                    type.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            EventBus.getDefault().post(new EventExit());

                            Intent intent = new Intent(getApplicationContext(), MainInspectionActivity.class);
                            intent.putExtra("fromActivity", getClass().getSimpleName());
                            intent.putExtra("nodate", true);
                            intent.putExtra("isSelect", EventInspFilter.event == null ? false : true);
                            startActivity(intent);
                            user.setLogin(true);
                            user.setCurType("U02");
                            MySp.setUser(getApplicationContext(), user);//缓存用户信息
                            finish();
                        }
                    });
                }

            } else {
                moreType.setVisibility(View.GONE);
            }


        }


    }


    private void initInspNum() {
        final BeanUser user = MySp.getUser(this);
        API.getInspNum(user.getAccountId(), new MyHttp.ResultCallback<BaseRes>() {
            @Override
            public void onSuccess(BaseRes res) {
                if (res.getCode() == 0) {
                    user.setInspNum(res.getInspNum());
                    user.setRepairNum(res.getRepairNum());
                    MySp.setUser(InspMyActivity.this, user);//缓存用户信息
                    initView();
                }

            }

            @Override
            public void onError(String message) {

            }
        });
    }

}
