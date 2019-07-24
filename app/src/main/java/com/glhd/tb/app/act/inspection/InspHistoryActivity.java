package com.glhd.tb.app.act.inspection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.ItemInspHistoryAdapter;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.event.EventInspFilter;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetInspHistory;
import com.glhd.tb.app.utils.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class InspHistoryActivity extends BaseActivity implements View.OnClickListener {
    protected PtrClassicFrameLayout refresh;
    protected ListView listview;
    protected TextView title;
    protected ImageView filterImage;
    protected LinearLayout container;
    private ItemInspHistoryAdapter adapter;
    private ArrayList<BeanAdvert> beans = new ArrayList<>();
    private int pageNum = 0;
    private String pageSize = "20";
    private ProgressDialog dialog;
    private String stationId;
    private String locationId;
    private String marshallingId;
    private String floorId;
    private String regionId;
    private String trainType;
    private String startDate;
    private String endDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_insp_history);
//        stationId = getIntent().getStringExtra("selectStation");
//        typeId = getIntent().getStringExtra("selectType");
//        locationId = getIntent().getStringExtra("selectLocation");
//        startDate = getIntent().getStringExtra("startDate");
//        endDate = getIntent().getStringExtra("endDate");
//        selectCarno = getIntent().getStringExtra("selectCarno");
//        selectMarshalling = getIntent().getStringExtra("selectMarshalling");


        initView();

    }

    private void initView() {
        refresh = findViewById(R.id.refresh);
        listview = findViewById(R.id.listview);
        title = (TextView) findViewById(R.id.title);
        filterImage = (ImageView) findViewById(R.id.filterImage);
        filterImage.setOnClickListener(InspHistoryActivity.this);
        container = (LinearLayout) findViewById(R.id.container);
        adapter = new ItemInspHistoryAdapter(this, beans);
        listview.setAdapter(adapter);

        refresh.disableWhenHorizontalMove(true);
        refresh.setPtrHandler(new PtrDefaultHandler2() {

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {

                taskListHttp(pageNum);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNum = 0;
                taskListHttp(pageNum);
            }
        });

        listview.setSelection(0);
        refresh(refresh);

    }


    /**
     * 获取数据
     *
     * @param num
     */
    private void taskListHttp(int num) {
        if (MySp.getUser(this) == null)
            return;
        API.getMyInspHistory(MySp.getUser(this).getAccountId(),
                 stationId,
                 locationId,
                 floorId,
                 regionId,
                 marshallingId,
                 startDate,
                 endDate,
                num+"",
                 pageSize,
                new MyHttp.ResultCallback<ResGetInspHistory>() {
                    @Override
                    public void onSuccess(ResGetInspHistory res) {
                        refresh.refreshComplete();
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
                        refresh.refreshComplete();
                    }
                });

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.filterImage) {
            Intent intent = new Intent(this, InspRangeActivity.class);
            intent.putExtra("showDate", true);
            intent.putExtra("taskState", "1");
            startActivity(intent);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventInspFilter(EventInspFilter event) {

        if(event.isHistory==false)
            return ;

        stationId = event.getStationId();
        locationId = event.getLocationId();
        floorId = event.getFloorId();
        regionId = event.getRegionId();
        marshallingId = event.getTrainNo();
        trainType = event.getTrain();
        startDate=event.getStartDate();
        endDate=event.getEndDate();
        pageNum = 0;
        taskListHttp(pageNum);


    }

}