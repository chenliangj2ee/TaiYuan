package com.glhd.tb.app.act.inspection;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.SearchView;
import android.widget.CheckBox;
import android.view.View;
import android.widget.ListView;

import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.InspRepairListAdapter;
import com.glhd.tb.app.adapter.InspWayListAdapter;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetRepairList;
import com.glhd.tb.app.http.res.WayList;
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;
import com.scwang.smartrefresh.layout.api.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class InspWayActivity extends BaseActivity {

    private TextView title;
    private EditText searchContent;
    private TextView searchNum;
    private View searchLine;
    private InspWayListAdapter inspWayListAdapter;
    private List<WayList.DataBean> carlist = new ArrayList<WayList.DataBean>();
    private RecyclerView searchList;
    private RefreshLayout mRefreshLayout;
    private String searchtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insp_way);
        initview();

    }

    private void initview() {
        title = (TextView) findViewById(R.id.title);
        searchContent = (EditText) findViewById(R.id.search_content);
        searchNum = (TextView) findViewById(R.id.search_num);
        searchLine = (View) findViewById(R.id.search_line);

        searchList = findViewById(R.id.search_list);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        searchList.setLayoutManager(new LinearLayoutManager(this));

        inspWayListAdapter = new InspWayListAdapter(this, carlist);
        searchList.setAdapter(inspWayListAdapter);

        getWayList(page, "");
        mRefrshLoad();
    }

    public void searchAction(View view) {
        searchtext = searchContent.getText().toString();
        if (TextUtils.isEmpty(searchContent.getText().toString())) {
            MyToast.showMessage(InspWayActivity.this, "请输入正确的车底号");
        } else {
            carlist.clear();
            getWayList(page, searchContent.getText().toString());
        }
    }

    private void mRefrshLoad() {
        mRefreshLayout.setEnableRefresh(true);//启用刷新
        mRefreshLayout.setEnableLoadMore(true);//启用加载
        //刷新
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 0;
                carlist.clear();
                if (TextUtils.isEmpty(searchContent.getText().toString())) {
                    getWayList(page, "");
                } else {
                    getWayList(page, searchContent.getText().toString());
                }
                refreshlayout.finishRefresh();
            }
        });
        //加载更多
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                if (TextUtils.isEmpty(searchContent.getText().toString())) {
                    getWayList(page, "");
                } else {
                    getWayList(page, searchContent.getText().toString());
                }
                refreshlayout.finishLoadmore();
            }
        });
    }

    int page;
    int pageSize = 20;

    private void getWayList(final int p, final String carnumber) {
        final String accountId = MySp.getUser(this).getAccountId();
        API.getWayList(accountId, carnumber, p + "", pageSize + "",
                new MyHttp.ResultCallback<WayList>() {
                    @Override
                    public void onSuccess(WayList res) {
                        if (res.getCode() == 0) {
                            Log.i(TAG, "searchAction: " + accountId + "----" + carnumber + "-----" + p + "----" + pageSize + "");
                            if (res.getData() != null) {
                                searchLine.setVisibility(View.VISIBLE);
                                searchNum.setVisibility(View.VISIBLE);
                                searchNum.setText("搜索结果：共"+res.getData().size()+"条 ");
                           carlist.addAll(res.getData());
                            }else{
                                searchLine.setVisibility(View.GONE);
                                searchNum.setVisibility(View.GONE);
                            }
                            inspWayListAdapter.notifyDataSetChanged();
                        } else {
                        }
                    }

                    @Override
                    public void onError(String message) {
                    }
                });
    }

}
