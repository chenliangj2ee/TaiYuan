package com.glhd.tb.app.act.inspection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ast365.library.listview.AstListView;
import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.act.InspMyActivity;
import com.glhd.tb.app.adapter.ItemInspHistoryAdapter;
import com.glhd.tb.app.adapter.ItemInspIndexAdapter;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.base.bean.BeanSpinner;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetInspHistory;
import com.glhd.tb.app.http.res.ResGetInspList;
import com.glhd.tb.app.http.res.ResSearchOne;
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.ArrayList;

public class InspHistoryActivity extends BaseActivity {

    protected AstListView listview;
    private ItemInspHistoryAdapter adapter;
    private ArrayList<BeanAdvert> beans = new ArrayList<>();
    private int pageNum = 0;
    private String pageSize = "20";
    private ProgressDialog dialog;
    private String stationId;
    private String typeId;
    private String locationId;
    private String startDate;
    private String endDate;
    private String selectCarno;
    private String selectMarshalling;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_insp_history);
        stationId = getIntent().getStringExtra("selectStation");
        typeId = getIntent().getStringExtra("selectType");
        locationId = getIntent().getStringExtra("selectLocation");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        selectCarno = getIntent().getStringExtra("selectCarno");
        selectMarshalling = getIntent().getStringExtra("selectMarshalling");



        initView();

    }

    private void initView() {
        listview = (AstListView) findViewById(R.id.listview);
        adapter = new ItemInspHistoryAdapter(this, beans);
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
        API.getMyInspHistory(MySp.getUser(this).getAccountId(), num + "", pageSize,
                stationId, typeId, locationId, startDate, endDate, selectCarno,selectMarshalling,new MyHttp.ResultCallback<ResGetInspHistory>() {
                    @Override
                    public void onSuccess(ResGetInspHistory res) {
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