package com.glhd.tb.app.act;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.BaseRes;
import com.glhd.tb.app.base.bean.BeanUser;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;

public class UserInfoActivity extends BaseActivity {


    protected EditText phone;
    protected EditText remarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_user_info);
        initView();

    }


    private void initView() {
        phone = (EditText) findViewById(R.id.phone);
        remarks = (EditText) findViewById(R.id.remarks);

        phone.setText(MySp.getUser(this).getPhone());
        remarks.setText(MySp.getUser(this).getRemarks());

        phone.setSelection(phone.getText().length());
    }

    public void submitAction(View view) {

        if (phone.getText().toString().length() != 11) {
            MyToast.showMessage(this, "请输入正确的手机号");
            return;
        }
        final BeanUser user = MySp.getUser(this);
        user.setPhone(phone.getText().toString());
        user.setRemarks(remarks.getText().toString());

        API.updateUserInfo(user, new MyHttp.ResultCallback<BaseRes>() {
            @Override
            public void onSuccess(BaseRes res) {

                if (res.getCode() == 0) {
                    MyToast.showMessage(UserInfoActivity.this, "修改成功");
                    MySp.setUser(UserInfoActivity.this, user);
                } else {
                    MyToast.showMessage(UserInfoActivity.this, res.getMessage());
                }

            }

            @Override
            public void onError(String message) {
                MyToast.showMessage(UserInfoActivity.this, "提交失败，请稍后再试");
            }
        });
    }
}
