package com.glhd.tb.app.act.customer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.bean.BeanContract;
import com.glhd.tb.app.customer.fg.AdvertInfoFragment;
import com.glhd.tb.app.customer.fg.PayInfoFragment;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetContractInfo;
import com.glhd.tb.app.utils.MyToast;

public class ContractInfoActivity extends BaseActivity {

    protected TextView id;
    protected TextView contractDate;
    protected TextView totalPrice;
    protected TextView payBaiFenBi;
    protected TextView company;
    protected TextView startEndDate;
    protected TextView infoTitle;
    protected TextView payTitle;
    protected TextView fukuanlv;
    private TextView name;
    private TextView date;
    private TextView number;
    private TextView payPrice;
    private TextView progressValue;
    private TextView payType;
    private TextView infoAction;
    private PayInfoFragment paymentInfofragment;
    private AdvertInfoFragment advertInfofragment;

    private BeanContract bean;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_he_tong_xiang_qing);

        bean = (BeanContract) getIntent().getSerializableExtra("data");

        initView();
    }

    private void initView() {
        payType = (TextView) findViewById(R.id.pay_type);
        name = (TextView) findViewById(R.id.name);
        id = (TextView) findViewById(R.id.id);
        contractDate = (TextView) findViewById(R.id.contract_date);
        totalPrice = (TextView) findViewById(R.id.total_price);
        payPrice = (TextView) findViewById(R.id.pay_price);
        payBaiFenBi = (TextView) findViewById(R.id.pay_bai_fen_bi);
        company = (TextView) findViewById(R.id.company);
        startEndDate = (TextView) findViewById(R.id.start_end_date);
        infoTitle = (TextView) findViewById(R.id.info_title);
        payTitle = (TextView) findViewById(R.id.pay_title);
        paymentInfofragment = (PayInfoFragment) getSupportFragmentManager().findFragmentById(R.id.paymentInfofragment);
        advertInfofragment = (AdvertInfoFragment) getSupportFragmentManager().findFragmentById(R.id.advertInfofragment);

        payType.setText("实际付款");
        payTitle.setText("付款详情");
        initViewData();
        getContractInfo();
        name.setFocusableInTouchMode(true);
        name.requestFocus();
        fukuanlv = (TextView) findViewById(R.id.fukuanlv);
        fukuanlv.setText("合同付款率:");

    }

    private void initViewData() {
        id.setText(bean.getCoding());
        name.setText(bean.getContractTitle());
        contractDate.setText(bean.getContractDate());
        totalPrice.setText(bean.getTotalAmount());
        payPrice.setText(bean.getPayment());
        payBaiFenBi.setText("(" + bean.getPaymentPercent() + ")");
        company.setText(bean.getCompany());
        startEndDate.setText(bean.getStartDate() + "~" + bean.getEndDate());
        if ("~".equals(startEndDate.getText().toString())) {
            startEndDate.setText("");
        }
        name.setFocusableInTouchMode(true);
        name.requestFocus();
    }

    private void getContractInfo() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载...");
        API.getCusContractInfo(bean.getId(), new MyHttp.ResultCallback<ResGetContractInfo>() {
            @Override
            public void onSuccess(ResGetContractInfo res) {
                if (res.getCode() == 0) {
                    bean = res.getData();
                    paymentInfofragment.setDatas(res.getData().getPaymentInfos());
                    advertInfofragment.setDatas(res.getData().getAds());
                    infoTitle.setText("媒体详情(" + res.getData().getAds().size() + ")");
                    initViewData();
                } else {

                }
                progressDialog.dismiss();
            }

            @Override
            public void onError(String message) {
                progressDialog.dismiss();
                MyToast.showMessage(ContractInfoActivity.this, "网络异常，请稍后再试");
            }
        });
    }
}
