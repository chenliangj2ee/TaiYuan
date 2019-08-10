package com.glhd.tb.app.act.inspection;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.act.InspMyActivity;
import com.glhd.tb.app.act.repair.RepairListFragment;
import com.glhd.tb.app.adapter.InspRepairListAdapter;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.bean.BeanUser;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.BeanBaoXiu;
import com.glhd.tb.app.http.res.ResGetRepairList;
import com.glhd.tb.app.utils.MyLocation;
import com.glhd.tb.app.utils.MyLog;
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;
import com.glhd.tb.app.view.datepicker.CustomDatePicker;
import com.glhd.tb.app.view.datepicker.DateFormatUtils;
import com.scwang.smartrefresh.layout.api.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

public class InspRepairIListActivity extends BaseActivity {

    private RepairListFragment noRepairFragment;//未修
    private TextView mTvSelectedDate, mTvSelectedDateLast;
    private CustomDatePicker mDatePicker, mDatePickerlast;
    private String first = null;
    private String last = null;
    private RecyclerView mRecyclerView;
    private RefreshLayout mRefreshLayout;
    private ArrayList<BeanBaoXiu> datas = new ArrayList<>();
    private InspRepairListAdapter inspRepairListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_insp_repair_list);
        initView();
        startLocation();
    }

    /**
     * 启动定位
     */
    private void startLocation() {
        final MyLocation location = new MyLocation(getApplicationContext());
        location.start(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {

                MyLog.i("", "------------------------------------------------");
                MyLocation.latitude = bdLocation.getLatitude() + "";
                MyLocation.longitude = bdLocation.getLongitude() + "";

                MyLog.i("", "latitude:" + MyLocation.latitude);
                MyLog.i("", "longitude:" + MyLocation.longitude);
                location.stop();
            }
        });
    }

    private void initView() {
        replace(R.id.content, noRepairFragment);

        mTvSelectedDate = findViewById(R.id.tv_selected_date);
        mTvSelectedDateLast = findViewById(R.id.tv_selected_date_last);

        mRecyclerView = findViewById(R.id.insp_list_recycler);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        inspRepairListAdapter = new InspRepairListAdapter(this, datas);
        mRecyclerView.setAdapter(inspRepairListAdapter);
        getRepairList(page,"","");
        mRefreshLayout.setEnableRefresh(true);//启用刷新
        mRefreshLayout.setEnableLoadMore(true);//启用加载
        //刷新
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page=0;
                datas.clear();
                if (mTvSelectedDate.getText().equals("请选择开始时间") || mTvSelectedDateLast.getText().equals("请选择结束时间")) {
                    getRepairList(page,"","");
                } else {
                    getRepairList(page,mTvSelectedDate.getText().toString(), mTvSelectedDateLast.getText().toString());
//            noRepairFragment = new RepairListFragment(mTvSelectedDate.getText().toString(), mTvSelectedDateLast.getText().toString());
                }

                refreshlayout.finishRefresh();
            }
        });
        //加载更多
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                if (mTvSelectedDate.getText().equals("请选择开始时间") || mTvSelectedDateLast.getText().equals("请选择结束时间")) {
                    getRepairList(page,"","");
                } else {
                    getRepairList(page,mTvSelectedDate.getText().toString(), mTvSelectedDateLast.getText().toString());
//            noRepairFragment = new RepairListFragment(mTvSelectedDate.getText().toString(), mTvSelectedDateLast.getText().toString());
                }
                refreshlayout.finishLoadmore();
            }
        });
        initDatePicker();

    }
    int page;
    int pageSize = 20;
    private String accountId;
    private void getRepairList(final int p,String first,String last) {

        API.getRepariList(accountId, first, last, p + "", pageSize + "",
                new MyHttp.ResultCallback<ResGetRepairList>() {
                    @Override
                    public void onSuccess(ResGetRepairList res) {
                        if (res.getCode() == 0) {
//                            if (p == 0)
//                                datas.clear();
                            datas.addAll(res.getData());
                            inspRepairListAdapter.notifyDataSetChanged();
//                            page++;
                        } else {

                        }
                    }

                    @Override
                    public void onError(String message) {
                    }
                });
    }
    public void firstTime(View view) {
        // 日期格式为yyyy-MM-dd
        mDatePicker.show(mTvSelectedDate.getText().toString());

    }

    public void lastTime(View view) {
        mDatePickerlast.show(mTvSelectedDateLast.getText().toString());
    }

    public void searchAction(View view) {
        if (mTvSelectedDate.getText().equals("请选择开始时间") || mTvSelectedDateLast.getText().equals("请选择结束时间")) {
            MyToast.showMessage(InspRepairIListActivity.this, "请选择开始时间或结束时间");
        } else {
            datas.clear();
            getRepairList(page,mTvSelectedDate.getText().toString(), mTvSelectedDateLast.getText().toString());
//            noRepairFragment = new RepairListFragment(mTvSelectedDate.getText().toString(), mTvSelectedDateLast.getText().toString());
        }
    }

    private void initDatePicker() {
        long beginTimestamp = DateFormatUtils.str2Long("2000-01-01", false);
        long endTimestamp = System.currentTimeMillis();
        // 通过时间戳初始化日期，毫秒级别
        mDatePicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                last = mTvSelectedDateLast.getText().toString();
                if (last.equals("请选择结束时间")) {
                    mTvSelectedDate.setText(DateFormatUtils.long2Str(timestamp, false));
                } else {
                    long lasttime = DateFormatUtils.str2Long(last, false);
                    if (timestamp > lasttime) {
                        MyToast.showMessage(InspRepairIListActivity.this, "开始时间不能大于结束时间");
                        mTvSelectedDate.setText(DateFormatUtils.long2Str(lasttime, false));
                    } else {
                        mTvSelectedDate.setText(DateFormatUtils.long2Str(timestamp, false));

                    }
                }

//                mTvSelectedDate.setText(DateFormatUtils.long2Str(timestamp, false));
            }
        }, beginTimestamp, endTimestamp);
        // 不允许点击屏幕或物理返回键关闭
        mDatePicker.setCancelable(false);
        // 不显示时和分
        mDatePicker.setCanShowPreciseTime(false);
        // 不允许循环滚动
        mDatePicker.setScrollLoop(false);
        // 不允许滚动动画
        mDatePicker.setCanShowAnim(false);

        mDatePickerlast = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                first = mTvSelectedDate.getText().toString();
                if (first.equals("请选择开始时间")) {
                    mTvSelectedDateLast.setText(DateFormatUtils.long2Str(timestamp, false));
                } else {
                    long firsttime = DateFormatUtils.str2Long(first, false);
                    if (timestamp < firsttime) {
                        MyToast.showMessage(InspRepairIListActivity.this, "开结束时间不能小于开始时间");
                        mTvSelectedDateLast.setText(DateFormatUtils.long2Str(firsttime, false));
                    } else {
                        mTvSelectedDateLast.setText(DateFormatUtils.long2Str(timestamp, false));
                    }
                }
            }
        }, beginTimestamp, endTimestamp);

        // 不允许点击屏幕或物理返回键关闭
        mDatePickerlast.setCancelable(false);
        // 不显示时和分
        mDatePickerlast.setCanShowPreciseTime(false);
        // 不允许循环滚动
        mDatePickerlast.setScrollLoop(false);
        // 不允许滚动动画
        mDatePickerlast.setCanShowAnim(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatePicker.onDestroy();
        mDatePickerlast.onDestroy();
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadmore();
    }

}
