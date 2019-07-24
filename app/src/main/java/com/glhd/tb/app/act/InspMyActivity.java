package com.glhd.tb.app.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.act.inspection.InspHistoryActivity;
import com.glhd.tb.app.act.inspection.InspRepairIListActivity;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.bean.BeanUser;
import com.glhd.tb.app.event.EventExit;
import com.glhd.tb.app.utils.MySp;

import org.greenrobot.eventbus.EventBus;

public class InspMyActivity extends BaseActivity {

    private TextView name;
    private TextView conpany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insp_my);

        name = (TextView) findViewById(R.id.name);
        conpany = (TextView) findViewById(R.id.conpany);
        if (MySp.getUser(this) != null) {
            name.setText(MySp.getUser(this).getName());
            conpany.setText(MySp.getUser(this).getCompany());

            if("U02".equals(MySp.getUser(this).getType())){
                findViewById(R.id.insp_history).setVisibility(View.VISIBLE);
                findViewById(R.id.repair_history).setVisibility(View.VISIBLE);
            }
        }

    }

    public void userInfoAction(View view) {
        startActivity(UserInfoActivity.class);
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


    public void inspHistoryAction(View view){
        startActivity(InspHistoryActivity.class);
    }
    public void repairHistoryAction(View view){
        startActivity(InspRepairIListActivity.class);
    }
}
