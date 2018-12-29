package com.glhd.tb.app.act.customer;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ast365.library.listview.AstListView;
import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.ItemCusFukuanTixingAdapter;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.bean.BeanNotice;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResFuKuanList;
import com.glhd.tb.app.http.res.ResGetNoticeList;
import com.glhd.tb.app.utils.MySp;

import java.util.ArrayList;

public class CusFuKuanListActivity extends BaseActivity {

    private AstListView listview;
    private ArrayList<ResFuKuanList.DataBean> beans=new ArrayList<>();
    private ItemCusFukuanTixingAdapter adapter;
    private int pageNum = 0;
    private String pageSize = "20";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_duikuan_list);

        listview = (AstListView) findViewById(R.id.listview);
        adapter=new ItemCusFukuanTixingAdapter(this,beans);
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
        API.getFuKuanList(MySp.getUser(this).getAccountId(), pageNum + "", pageSize, new MyHttp.ResultCallback<ResFuKuanList>() {
            @Override
            public void onSuccess(ResFuKuanList res) {
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
