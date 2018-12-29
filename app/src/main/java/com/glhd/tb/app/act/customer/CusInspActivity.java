package com.glhd.tb.app.act.customer;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.ast365.library.listview.AstListView;
import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.ItemCusInspAdapter;
import com.glhd.tb.app.adapter.ItemInspHistoryAdapter;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetCusInspHistory;
import com.glhd.tb.app.http.res.ResGetCusSearchBaseData;
import com.glhd.tb.app.http.res.ResGetInspHistory;
import com.glhd.tb.app.http.res.ResGetMyAdverts;
import com.glhd.tb.app.utils.MySp;

import java.util.ArrayList;

public class CusInspActivity extends BaseActivity {

    protected AstListView listview;
    private ItemCusInspAdapter adapter;
    private ArrayList<BeanAdvert> beans = new ArrayList<>();
    private int pageNum = 0;
    private String pageSize = "20";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_insp_history);
        initView();
    }

    private void initView() {
        listview = (AstListView) findViewById(R.id.listview);
        adapter = new ItemCusInspAdapter(this, beans);
        listview.setAdapter(adapter);
        listview.setPullLoadEnable(true);
        listview.setOnRefreshListener(new AstListView.RefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 0;
                getHistory(pageNum);

            }

            @Override
            public void onLoadMore() {
                getHistory(pageNum);
            }
        });


    }



    /**
     * 获取数据
     *
     * @param num
     */
    private void getHistory(int num) {
        API.getCusInspHistory(MySp.getUser(this).getAccountId(), pageNum + "", pageSize, new MyHttp.ResultCallback<ResGetCusInspHistory>() {
            @Override
            public void onSuccess(ResGetCusInspHistory res) {
                listview.stop();
                if (res.getCode() == 0) {
                    if (pageNum == 0)
                        beans.clear();
                    beans.addAll(res.getData());
                    adapter.notifyDataSetChanged();
                    pageNum++;
                } else {

                }
            }

            @Override
            public void onError(String message) {
                listview.stop();
            }
        });

    }

}