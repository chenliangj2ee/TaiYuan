package com.glhd.tb.app.act.customer;

import android.os.Bundle;
import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ast365.library.listview.FullListView;
import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.ItemNoticeAdvertListAdapter;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.base.bean.BeanNotice;

import java.util.ArrayList;

public class NoticeInfoActivity extends BaseActivity {

    private TextView title;
    private TextView startDate;
    private TextView endDate;
    private TextView content;
    private FullListView listview;

    private ItemNoticeAdvertListAdapter adapter;
    private ArrayList<BeanAdvert> beans = new ArrayList<>();

    private BeanNotice notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_info);

        notice = (BeanNotice) getIntent().getSerializableExtra("data");

        title = (TextView) findViewById(R.id.title);
        startDate = (TextView) findViewById(R.id.start_date);
        endDate = (TextView) findViewById(R.id.end_date);
        content = (TextView) findViewById(R.id.content);
        listview = (FullListView) findViewById(R.id.listview);

        title.setText(notice.getTitle());
        startDate.setText(notice.getStartDate());
        endDate.setText(notice.getEndDate());
        beans.addAll(notice.getAds());
        adapter = new ItemNoticeAdvertListAdapter(this, beans);
        listview.setAdapter(adapter);

    }

}
