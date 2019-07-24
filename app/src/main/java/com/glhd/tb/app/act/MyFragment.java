package com.glhd.tb.app.act;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.base.MyBaseFragment;
import com.glhd.tb.app.base.bean.BeanUser;
import com.glhd.tb.app.event.EventExit;
import com.glhd.tb.app.utils.MySp;

import org.greenrobot.eventbus.EventBus;

public class MyFragment extends MyBaseFragment implements View.OnClickListener {
    protected Button info;
    protected Button passSet;
    protected TextView eixt;
    private TextView name;
    private TextView conpany;

    @Override
    public int initViewId() {
        return R.layout.fragment_insp_my;
    }

    @Override
    public void initView() {

        name = (TextView) findViewById(R.id.name);
        conpany = (TextView) findViewById(R.id.conpany);
        if (MySp.getUser(getActivity()) != null) {
            name.setText(MySp.getUser(getActivity()).getName());
            conpany.setText(MySp.getUser(getActivity()).getCompany());
        }
        info = (Button) rootView.findViewById(R.id.info);
        info.setOnClickListener(MyFragment.this);
        passSet = (Button) rootView.findViewById(R.id.pass_set);
        passSet.setOnClickListener(MyFragment.this);
        eixt = (TextView) rootView.findViewById(R.id.eixt);
        eixt.setOnClickListener(MyFragment.this);
    }



    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.info) {
            startActivity(UserInfoActivity.class);
        } else if (view.getId() == R.id.pass_set) {
            startActivity(ResetPasswordActivity.class);
        } else if (view.getId() == R.id.eixt) {
            BeanUser user = MySp.getUser(getContext());
            user.setLogin(false);
            MySp.setUser(getContext(), user);
            startActivity(LoginActivity.class);
            EventBus.getDefault().post(new EventExit());
        }
    }
}