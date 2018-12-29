package com.glhd.tb.app.act.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ast365.library.listview.AstListView;
import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.ItemAdminUpAdsAdapter;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResConstructionNoti;
import com.glhd.tb.app.http.res.ResGetCusUpAdvertList;
import com.glhd.tb.app.utils.MySp;

import java.util.ArrayList;

/**
 * 上刊列表/施工提醒列表
 */
public class AdminUpAdsListActivity extends BaseActivity {

    private LinearLayout container;
    private TextView title;
    private AstListView listview;

    private ArrayList<ResConstructionNoti.DataBean> adverts = new ArrayList<>();
    private ItemAdminUpAdsAdapter adapter;

    private int pageNum = 0;
    private String pageSize = "20";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_up_ads_list);

        container = (LinearLayout) findViewById(R.id.container);
        title = (TextView) findViewById(R.id.title);
        listview = (AstListView) findViewById(R.id.listview);
        adapter = new ItemAdminUpAdsAdapter(this, adverts);

        listview.setAdapter(adapter);

        listview.setPullLoadEnable(true);
        listview.setOnRefreshListener(new AstListView.RefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 0;
                taskListHttp(pageNum);

            }

            @Override
            public void onLoadMore() {
                taskListHttp(pageNum);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getBaseContext(),AdminConstructionInfoActivity.class);
                intent.putExtra("data",adverts.get(i-1));
                startActivity(intent);
            }
        });

    }

    /**
     * 获取数据
     *
     * @param num
     */
    private void taskListHttp( int num) {
        API.getAdminUpAdvertList(MySp.getUser(this).getAccountId(), pageNum + "", pageSize, new MyHttp.ResultCallback<ResConstructionNoti>() {
            @Override
            public void onSuccess(ResConstructionNoti res) {
                listview.stop();
                if (res.getCode() == 0) {
                    if (pageNum == 0)
                        adverts.clear();
                    adverts.addAll(res.getData());
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
