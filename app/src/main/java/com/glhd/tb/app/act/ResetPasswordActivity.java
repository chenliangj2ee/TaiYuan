package com.glhd.tb.app.act;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.BaseRes;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.utils.MyMd5;
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;

public class ResetPasswordActivity extends BaseActivity {
    protected EditText oldPassword;
    protected EditText newPassword1;
    protected EditText newPassword2;

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_reset_password);
        initView();

    }

    /**
     * 登录按钮事件
     *
     * @param view
     */
    public void saveAction(View view) {

        String oldPass = oldPassword.getText().toString();
        String passwordStr1 = newPassword1.getText().toString();
        String passwordStr2 = newPassword2.getText().toString();
        if (TextUtils.isEmpty(oldPass)) {
            MyToast.showMessage(this, "原始密码");
            oldPassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(passwordStr1)) {
            MyToast.showMessage(this, "请输入新密码");
            newPassword1.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(passwordStr2)) {
            MyToast.showMessage(this, "请输入确认密码");
            newPassword2.requestFocus();
            return;
        }
        if (passwordStr1.equals(passwordStr2) == false) {
            MyToast.showMessage(this, "确认密码有误，请重新输入");
            newPassword2.setText("");
            newPassword2.requestFocus();
            return;
        }
        if (passwordStr1.length() < 6 || passwordStr1.length() > 20) {
            MyToast.showMessage(this, "密码必须是6-20位数字或字符");
            return;
        }

        saveHttp(oldPass, passwordStr1);
    }

    /**
     * 网络修改密码
     *
     * @param oldPass
     * @param newPassword2
     */
    private void saveHttp(String oldPass, String newPassword2) {
        pd = new ProgressDialog(this);
        pd.setMessage("正在提交...");
        pd.show();
        API.resetPassword(getUserId(),
                MyMd5.md5(oldPass),
                MyMd5.md5(newPassword2),
                new MyHttp.ResultCallback<BaseRes>() {
                    @Override
                    public void onSuccess(BaseRes res) {
                        pd.dismiss();
                        if (res.getCode() == 0) {
                            MyToast.showMessage(ResetPasswordActivity.this, "修改成功");
                            finish();
                        } else {
                            MyToast.showMessage(ResetPasswordActivity.this, res.getMessage());
                        }
                    }

                    @Override
                    public void onError(String message) {
                        pd.dismiss();
                        MyToast.showMessage(ResetPasswordActivity.this, "修改失败，请重试");
                    }
                });

    }

    private void initView() {
        oldPassword = (EditText) findViewById(R.id.old_password);
        newPassword1 = (EditText) findViewById(R.id.new_password1);
        newPassword2 = (EditText) findViewById(R.id.new_password2);
    }


}
