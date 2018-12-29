package com.glhd.tb.app.act.customer;

import android.os.Bundle;
import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ast365.library.listview.AstListView;
import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.ItemCusNoticeAdapter;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.bean.BeanNotice;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetCusUpAdvertList;
import com.glhd.tb.app.http.res.ResGetNoticeList;
import com.glhd.tb.app.utils.MySp;

import java.util.ArrayList;

public class CusNoticeListActivity extends BaseActivity {

    private LinearLayout container;
    private TextView title;
    private AstListView listview;
    private ArrayList<BeanNotice> beans=new ArrayList<>();
    private ItemCusNoticeAdapter adapter;
    private int pageNum = 0;
    private String pageSize = "20";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_notice_list);

        container = (LinearLayout) findViewById(R.id.container);
        title = (TextView) findViewById(R.id.title);
        listview = (AstListView) findViewById(R.id.listview);
        adapter=new ItemCusNoticeAdapter(this,beans);
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
    private void taskListHttp( int num) {
        API.getNoticeList(MySp.getUser(this).getAccountId(), pageNum + "", pageSize, new MyHttp.ResultCallback<ResGetNoticeList>() {
            @Override
            public void onSuccess(ResGetNoticeList res) {
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
