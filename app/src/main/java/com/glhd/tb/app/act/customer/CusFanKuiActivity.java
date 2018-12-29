package com.glhd.tb.app.act.customer;

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
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;

public class CusFanKuiActivity extends BaseActivity {


    protected EditText title;
    protected EditText content;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_cus_fan_kui);
        initView();
    }

    public void fankuiAction(View view) {
        if (TextUtils.isEmpty(title.getText().toString())) {
            MyToast.showMessage(this, "请输入标题");
            title.requestFocus();

            return;
        }

        if (TextUtils.isEmpty(content.getText().toString())) {
            MyToast.showMessage(this, "请输入意见与建议");
            content.requestFocus();
            return;
        }

        httpSubmit(title.getText().toString(),content.getText().toString());
    }

    private void initView() {
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
    }


    /**
     * 提交反馈到网络
     * @param title
     * @param content
     */
    private void httpSubmit(String title,String content){
        dialog=new ProgressDialog(this);
        dialog.setMessage("正在提交");
        dialog.show();
        API.feedback(MySp.getUser(this).getAccountId(), title, content, new MyHttp.ResultCallback<BaseRes>() {
            @Override
            public void onSuccess(BaseRes res) {
                dialog.dismiss();
                if(res.getCode()==0){
                    MyToast.showMessage(CusFanKuiActivity.this,"反馈成功");
                    finish();
                }else{
                    MyToast.showMessage(CusFanKuiActivity.this,res.getMessage());
                }

            }

            @Override
            public void onError(String message) {
                dialog.dismiss();
                MyToast.showMessage(CusFanKuiActivity.this,"系统异常，请稍后再试");
            }
        });

    }
}
