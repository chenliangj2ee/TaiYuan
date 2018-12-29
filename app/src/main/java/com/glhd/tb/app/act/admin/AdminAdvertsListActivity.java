package com.glhd.tb.app.act.admin;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ast365.library.listview.AstListView;
import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.ItemAdminAdsListAdapter;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetMyAdverts;
import com.glhd.tb.app.utils.MySp;

import java.util.ArrayList;

public class AdminAdvertsListActivity extends BaseActivity {

    private LinearLayout container;
    private TextView title;
    private AstListView listview;

    private ArrayList<BeanAdvert> adverts = new ArrayList<>();
    private ItemAdminAdsListAdapter adapter;


    private int pageNum = 0;
    private String pageSize = "20";

    private String stationId = "";
    private String typeId = "";
    private String locationId = "";
    private String directionId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ads_list);

        stationId=getIntent().getStringExtra("stationId");
        typeId=getIntent().getStringExtra("typeId");
        locationId=getIntent().getStringExtra("locationId");
        directionId=getIntent().getStringExtra("directionId");

        container = (LinearLayout) findViewById(R.id.container);
        title = (TextView) findViewById(R.id.title);
        listview = (AstListView) findViewById(R.id.listview);
        adapter = new ItemAdminAdsListAdapter(this, adverts);
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
    }


    /**
     * 获取数据
     *
     * @param num
     */
    private void taskListHttp(int num) {
        API.adminAdverts(MySp.getUser(this).getAccountId(), stationId, typeId, locationId,
                directionId, pageNum+"", pageSize, new MyHttp.ResultCallback<ResGetMyAdverts>() {
                    @Override
                    public void onSuccess(ResGetMyAdverts res) {
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
