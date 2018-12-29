package com.glhd.tb.app.act.customer;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ast365.library.listview.AstListView;
import com.czp.library.ArcProgress;
import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.ItemCusUpAdsAdapter;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.BaseRes;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetCusUpAdvertList;
import com.glhd.tb.app.utils.MySp;

import java.util.ArrayList;

public class CusUpAdsListActivity extends BaseActivity {

    private LinearLayout container;
    private TextView title;
    private AstListView listview;

    private ArrayList<BeanAdvert> adverts = new ArrayList<>();
    private ItemCusUpAdsAdapter adapter;

    private int pageNum = 0;
    private String pageSize = "20";

    private View header;
    private ArcProgress progress;
    private TextView progressText;
    private TextView num1;
    private TextView num2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_up_ads_list);

        container = (LinearLayout) findViewById(R.id.container);
        title = (TextView) findViewById(R.id.title);
        listview = (AstListView) findViewById(R.id.listview);
        adapter = new ItemCusUpAdsAdapter(this, adverts);

        header = View.inflate(this, R.layout.header_cus_up_advert, null);
        progress = header.findViewById(R.id.progress);
        progressText = header.findViewById(R.id.progressText);
        num1=header.findViewById(R.id.num1);
        num2=header.findViewById(R.id.num2);

        progress.setMax(100);
        progress.setProgress(0);
        listview.addHeaderView(header);

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
        taskListHttpTJ();

    }

    /**
     * 获取数据
     *
     * @param num
     */
    private void taskListHttp( int num) {
        API.getCusUpAdvertList(MySp.getUser(this).getAccountId(), pageNum + "", pageSize, new MyHttp.ResultCallback<ResGetCusUpAdvertList>() {
            @Override
            public void onSuccess(ResGetCusUpAdvertList res) {
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

    /**
     * 获取数据
     *
     * @param num
     */
    private void taskListHttpTJ( ) {
        API.getCusUpAdvertTJ(MySp.getUser(this).getAccountId(), new MyHttp.ResultCallback<BaseRes>() {
            @Override
            public void onSuccess(BaseRes res) {
                if (res.getCode() == 0) {
                        num1.setText(res.getAvailableNum());
                        num2.setText(res.getNotUpAdsNum());
                        progressText.setText(res.getVacantPercent());
                        progress.setProgress(Integer.parseInt(res.getVacantPercent().replace("%","").trim()));
                }
            }

            @Override
            public void onError(String message) {
            }
        });

    }
}
