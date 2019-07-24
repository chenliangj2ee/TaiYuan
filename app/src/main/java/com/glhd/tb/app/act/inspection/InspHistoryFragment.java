package com.glhd.tb.app.act.inspection;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ast365.library.listview.AstListView;
import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.ItemInspHistoryAdapter;
import com.glhd.tb.app.base.MyBaseFragment;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetInspHistory;
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;

import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class InspHistoryFragment extends MyBaseFragment implements View.OnClickListener {

    protected ListView listview;
    protected TextView title;
    protected PtrClassicFrameLayout refresh;
    protected LinearLayout container;
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
    public int initViewId() {
        return R.layout.activity_insp_history;
    }

    @Override
    public void initView() {
        refresh = (PtrClassicFrameLayout)findViewById(R.id.refresh);
        listview = (ListView) findViewById(R.id.listview);
        adapter = new ItemInspHistoryAdapter(getContext(), beans);
        listview.setAdapter(adapter);

        refresh.disableWhenHorizontalMove(true);
        refresh.setPtrHandler(new PtrDefaultHandler2() {

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                pageNum = 0;
                taskListHttp(pageNum);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
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
        API.getMyInspHistory(MySp.getUser(getContext()).getAccountId(), num + "", pageSize,
                stationId, typeId, locationId, startDate, endDate, selectCarno, selectMarshalling, new MyHttp.ResultCallback<ResGetInspHistory>() {
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
                        MyToast.showMessage(getContext(), message);
                    }
                });

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.refresh) {

        }
    }
}