package com.glhd.tb.app.act;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.setting.a.BxCore;
import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.act.admin.AdminActivity;
import com.glhd.tb.app.act.customer.MainCustomerActivity;
import com.glhd.tb.app.act.inspection.InspIndexActivity;
import com.glhd.tb.app.act.inspection.MainInspectionActivity;
import com.glhd.tb.app.act.inspection.SelectStationActivity;
import com.glhd.tb.app.act.repair.RepairIndexActivity;
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
        BxCore.getInstance(this);
        initView();


        //自动登陆
        BeanUser user = MySp.getUser(this);
        if (user != null && user.isLogin()) {
//            classMap.put("U02", InspIndexActivity.class);//巡检端
            String ip = MySp.getString(this, "ip");
            String port = MySp.getString(this, "port");
            String projectname = MySp.getString(this, "projectname");
//            API.init(ip, port, projectname);
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

//        getIp(accountStr, passwordStr, phoneStr);
        loginHttp(accountStr, passwordStr, phoneStr);
    }

    /**
     * 网络登录
     *
     * @param account
     * @param password
     */
    private void loginHttp(final String account, String password, String phoneStr) {
        pd = new ProgressDialog(this);
        pd.setMessage("正在登录...");

        pd.show();
        API.login(account, MyMd5.md5(password), phoneStr, new MyHttp.ResultCallback<ResLogin>() {
            @Override
            public void onSuccess(ResLogin res) {
                pd.dismiss();
                if (res.getCode() == 0) {
                    BeanUser user = res.getData();
                    user.setAccount(account);
                    user.setLoginPhone(phone.getText().toString());
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
        classMap.put("U02", MainInspectionActivity.class);//巡检端
        classMap.put("U03", MainCustomerActivity.class);//客户端
        classMap.put("U04", RepairIndexActivity.class);//维修员
        classMap.put("U05", InspIndexActivity.class);//巡检端
        classMap.put("U06", SelectStationActivity.class);//巡检端


        accountE = (EditText) findViewById(R.id.account_e);
        passwordE = (EditText) findViewById(R.id.password_e);
        phone = (EditText) findViewById(R.id.phone);
        ipt = (EditText) findViewById(R.id.ip);

        BeanUser user = MySp.getUser(this);
        if (user != null && user.isLogin() == false) {
            accountE.setText(user.getAccount());
            phone.setText(user.getLoginPhone());
            passwordE.requestFocus();
        }

        permission();

    }

    /**
     * 检查权限
     */
    private void permission() {
        RxPermissions.getInstance(this).request(Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
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

            if (user.getType().contains(",")) {
                if (user.getCurType() == null) {
                    dialogChoice(user);
                } else {
                    Intent intent = new Intent(this, classMap.get(user.getCurType()));
                    intent.putExtra("fromActivity", getClass().getSimpleName());
                    intent.putExtra("nodate", true);
                    startActivity(intent);
                    user.setLogin(true);
                    MySp.setUser(LoginActivity.this, user);//缓存用户信息
                    finish();
                }

            } else {
                MyToast.showMessage(this, "登录异常：不存在该登录账号类型");
            }
        } else {
//            startActivity(classMap.get(user.getType()));

            if (user.getType().contains(",")) {
                dialogChoice(user);
            } else {
                Intent intent = new Intent(this, classMap.get(user.getType()));
                intent.putExtra("fromActivity", getClass().getSimpleName());
                intent.putExtra("nodate", true);
                startActivity(intent);
                user.setLogin(true);
                MySp.setUser(LoginActivity.this, user);//缓存用户信息
                finish();
            }


        }

    }

    private void getIp(final String account, final String pass, final String phone) {
//        API.init("192.168.6.132", "8080","advertmanageapp/admin");
////        loginHttp(account,pass);
        pd = new ProgressDialog(this);
        pd.setMessage("正在登录...");
        pd.show();
        API.getIp(account, MyMd5.md5(pass), phone, new MyHttp.ResultCallback<ResGetPi>() {
            @Override
            public void onSuccess(ResGetPi res) {
                if (res != null && res.getCode() == 0) {
                    String ip = res.getData().getService_ip();
                    String port = res.getData().getService_port();
                    String projectname = res.getData().getService_projectname();
                    MySp.putString(getApplicationContext(), "ip", ip);
                    MySp.putString(getApplicationContext(), "port", port);
                    MySp.putString(getApplicationContext(), "projectname", projectname);

                    API.init(ip, port, projectname);
                    loginHttp(account, pass, phone);
                } else {
                    MyToast.showMessage(getApplicationContext(), res.getMessage());
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


    public void dialogChoice(final BeanUser user) {
        HashMap<String, String> maps = new HashMap<>();
        maps.put("U01", "管理员");
        maps.put("U02", "巡检员");
        maps.put("U03", "客户");
        maps.put("U04", "维修员");
        maps.put("U05", "巡检员");
        maps.put("U06", "巡检员");


        final String[] ts = user.getType().split(",");


        String[] titles = new String[ts.length];

        for (int i = 0; i < ts.length; i++) {
            titles[i] = maps.get(ts[i]);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this, 0);
        TextView title = new TextView(this);
        title.setPadding(20,20,20,0);
        title.setText("您当前有" + user.getInspNum() + "项巡检任务，" + user.getRepairNum() + "项维修任务，请选择要完成的任务。\n");
        builder.setCustomTitle(title);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setSingleChoiceItems(titles, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(LoginActivity.this, classMap.get(ts[which]));
                intent.putExtra("fromActivity", getClass().getSimpleName());
                intent.putExtra("nodate", true);
                startActivity(intent);
                user.setLogin(true);
                user.setCurType(ts[which]);
                MySp.setUser(LoginActivity.this, user);//缓存用户信息
                finish();
            }
        });

        builder.create().show();
    }
}

