package com.glhd.tb.app.act;

import android.Manifest;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.act.admin.AdminActivity;
import com.glhd.tb.app.act.customer.MainCustomerActivity;
import com.glhd.tb.app.act.inspection.InspIndexActivity;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.bean.BeanUser;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetPi;
import com.glhd.tb.app.http.res.ResLogin;
import com.glhd.tb.app.utils.MyMd5;
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.HashMap;

import rx.functions.Action1;

public class LoginActivity extends BaseActivity {

    protected EditText accountE;
    protected EditText passwordE;
    protected EditText phone;
    private EditText ipt;
    protected ProgressDialog pd;
    private HashMap<String, Class<?>> classMap = new HashMap<String, Class<?>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);

        initView();


        //自动登陆
        BeanUser user = MySp.getUser(this);
        if (user != null && user.isLogin()) {
            String ip = MySp.getString(this, "ip");
            String port = MySp.getString(this, "port");
            String projectname = MySp.getString(this, "projectname");
            API.init(ip, port,projectname);
            toMain(user);
        }

    }

    /**
     * 登录按钮事件
     *
     * @param view
     */
    public void loginAction(View view) {

        String accountStr = accountE.getText().toString();
        String passwordStr = passwordE.getText().toString();
        String phoneStr = phone.getText().toString();

        if (TextUtils.isEmpty(accountStr)) {
            MyToast.showMessage(this, "请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(passwordStr)) {
            MyToast.showMessage(this, "请输入密码");
            return;
        }

        if (TextUtils.isEmpty(phoneStr)) {
            MyToast.showMessage(this, "请输入手机号");
            return;
        }

        getIp(accountStr, passwordStr, phoneStr);
    }

    /**
     * 网络登录
     *
     * @param account
     * @param password
     */
    private void loginHttp(final String account, String password) {

        API.login(account, MyMd5.md5(password), new MyHttp.ResultCallback<ResLogin>() {
            @Override
            public void onSuccess(ResLogin res) {
                pd.dismiss();
                if (res.getCode() == 0) {
                    BeanUser user = res.getData();
                    user.setAccount(account);
                    toMain(user);
                } else {
                    MyToast.showMessage(LoginActivity.this, res.getMessage());
                }
            }

            @Override
            public void onError(String message) {
                pd.dismiss();
                MyToast.showMessage(LoginActivity.this, "系统异常，稍后再试");
            }
        });


    }

    private void initView() {
        classMap.put("U01", AdminActivity.class);//管理端
        classMap.put("U02", InspIndexActivity.class);//巡检端
        classMap.put("U03", MainCustomerActivity.class);//客户端

        accountE = (EditText) findViewById(R.id.account_e);
        passwordE = (EditText) findViewById(R.id.password_e);
        ipt = (EditText) findViewById(R.id.ip);

        BeanUser user = MySp.getUser(this);
        if (user != null && user.isLogin() == false) {
            accountE.setText(user.getAccount());
            passwordE.requestFocus();
        }

        permission();
        phone = (EditText) findViewById(R.id.phone);
    }

    /**
     * 检查权限
     */
    private void permission() {
        RxPermissions.getInstance(this).request(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean granted) {
            }
        });

    }


    /**
     * 根据用户类型提跳转到具体的页面
     *
     * @param user
     */


    private void toMain(BeanUser user) {
        if (classMap.get(user.getType()) == null) {
            MyToast.showMessage(this, "登录异常：不存在该登录账号类型");
        } else {
            startActivity(classMap.get(user.getType()));
            user.setLogin(true);
            MySp.setUser(LoginActivity.this, user);//缓存用户信息
            finish();
        }

    }

    private void getIp(final String account, final String pass, String phone) {
        pd = new ProgressDialog(this);
        pd.setMessage("正在登录...");
        pd.show();
        API.getIp(account, pass, phone, new MyHttp.ResultCallback<ResGetPi>() {
            @Override
            public void onSuccess(ResGetPi res) {
                if (res != null && res.getResult().equals("success")) {
                    String ip = res.getResponse().getService_ip();
                    String port = res.getResponse().getService_port();
                    String projectname=res.getResponse().getService_projectname();
                    MySp.putString(getApplicationContext(), "ip", ip);
                    MySp.putString(getApplicationContext(), "port", port);
                    MySp.putString(getApplicationContext(), "projectname", projectname);

                    API.init(ip, port,projectname);
                    loginHttp(account, pass);
                } else {
                    MyToast.showMessage(getApplicationContext(), res.getResponse().getErrorText());
                    pd.dismiss();
                }
            }

            @Override
            public void onError(String message) {
                pd.dismiss();
                MyToast.showMessage(getApplicationContext(), "登录异常");
            }
        });
    }

}
