package com.glhd.tb.app.act.customer;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.act.InspMyActivity;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetNoticeList;
import com.glhd.tb.app.utils.MySp;

public class MainCustomerActivity extends BaseActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    protected SwipeRefreshLayout refresh;
    protected TextView noticeTitle;
    protected TextView noticeDate;
    protected LinearLayout adsAction;
    protected LinearLayout inspAction;
    protected LinearLayout upAdsAction;
    protected LinearLayout contractAction;
    protected LinearLayout noticeAction;
    protected LinearLayout noticeLayout;
    protected LinearLayout fukuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_customer);
        initView();
        getNotice();
        upgrade();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ads_action) {
            startActivity(CusAdsListActivity.class);
        } else if (view.getId() == R.id.insp_action) {
            startActivity(CusInspActivity.class);

        } else if (view.getId() == R.id.up_ads_action) {
            startActivity(CusUpAdsListActivity.class);

        } else if (view.getId() == R.id.contract_action) {
            startActivity(ContractListActivity.class);

        } else if (view.getId() == R.id.notice_action) {
            startActivity(CusNoticeListActivity.class);

        } else if (view.getId() == R.id.fukuan) {
            startActivity(CusFuKuanListActivity.class);
        }
    }

    private void initView() {
        noticeTitle = (TextView) findViewById(R.id.noticeTitle);
        noticeDate = (TextView) findViewById(R.id.noticeDate);
        adsAction = (LinearLayout) findViewById(R.id.ads_action);
        adsAction.setOnClickListener(MainCustomerActivity.this);
        inspAction = (LinearLayout) findViewById(R.id.insp_action);
        inspAction.setOnClickListener(MainCustomerActivity.this);
        upAdsAction = (LinearLayout) findViewById(R.id.up_ads_action);
        upAdsAction.setOnClickListener(MainCustomerActivity.this);
        contractAction = (LinearLayout) findViewById(R.id.contract_action);
        contractAction.setOnClickListener(MainCustomerActivity.this);
        noticeAction = (LinearLayout) findViewById(R.id.notice_action);
        noticeAction.setOnClickListener(MainCustomerActivity.this);
        noticeLayout = (LinearLayout) findViewById(R.id.notice_layout);
        fukuan = (LinearLayout) findViewById(R.id.fukuan);
        fukuan.setOnClickListener(MainCustomerActivity.this);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refresh.setOnRefreshListener(this);
    }
    @Override
    public void onRefresh() {
        getNotice();
    }

    public void fankuiAction(View view) {
        startActivity(CusFanKuiActivity.class);
    }

    public void myAction(View view) {
        startActivity(InspMyActivity.class);
    }

    public void searchAction(View view) {
        startActivity(SearchActivity.class);
    }


    /**
     * 获取公告
     *
     * @param num
     */
    private void getNotice() {
        API.getNoticeList(MySp.getUser(this).getAccountId(), "0", "5", new MyHttp.ResultCallback<ResGetNoticeList>() {
            @Override
            public void onSuccess(ResGetNoticeList res) {
                refresh.setRefreshing(false);
                if (res.getCode() == 0) {
                    if (res.getData().size() > 0) {
                        noticeLayout.setVisibility(View.VISIBLE);
                        noticeTitle.setText(res.getData().get(0).getTitle());
                        noticeDate.setText(res.getData().get(0).getReleaseDate());
                        noticeLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(CusNoticeListActivity.class);
                            }
                        });
                    }

                } else {

                }
            }

            @Override
            public void onError(String message) {
                refresh.setRefreshing(false);
            }
        });

    }
}
