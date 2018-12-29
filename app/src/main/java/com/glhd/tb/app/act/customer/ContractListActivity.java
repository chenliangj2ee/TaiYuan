package com.glhd.tb.app.act.customer;

import android.os.Bundle;

import com.ast365.library.listview.AstListView;
import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.ItemCusContractListAdapter;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.bean.BeanContract;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetCustommerContract;
import com.glhd.tb.app.utils.MySp;

import java.util.ArrayList;

public class ContractListActivity extends BaseActivity {

    private AstListView listview;
    private ArrayList<BeanContract> beans = new ArrayList<>();
    private ItemCusContractListAdapter adapter;
    private int pageNum = 0;
    private String pageSize = "20";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_contract);
        initView();
    }

    private void initView() {
        listview = (AstListView) findViewById(R.id.listview);
        adapter = new ItemCusContractListAdapter(this, beans);
        listview.setAdapter(adapter);
        listview.setOnRefreshListener(new AstListView.RefreshListener() {
            @Override
            public void onRefresh() {
                pageNum=0;
                contractListHttp(pageNum);
            }

            @Override
            public void onLoadMore() {
                contractListHttp(pageNum);
            }
        });

    }

    private void contractListHttp(int num) {
        API.getContractList(MySp.getUserId(this), num + "", pageSize, new MyHttp.ResultCallback<ResGetCustommerContract>() {
            @Override
            public void onSuccess(ResGetCustommerContract res) {
                listview.stop();
                if (res.getCode() == 0) {
                    if(pageNum==0)
                        beans.clear();
                    beans.addAll(res.getDatas());
                    pageNum++;
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {
                listview.stop();
            }
        });

    }
}
