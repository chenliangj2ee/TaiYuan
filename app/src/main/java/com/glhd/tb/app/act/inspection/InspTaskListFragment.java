package com.glhd.tb.app.act.inspection;

import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.NestedScrollView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ast365.library.listview.AstListView;
import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.ItemInspIndexAdapter;
import com.glhd.tb.app.base.MyBaseFragment;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.event.EventCancelMoreSelect;
import com.glhd.tb.app.event.EventInspFilter;
import com.glhd.tb.app.event.EventInspSubmitMore;
import com.glhd.tb.app.event.EventRefreshInspHistoryLayout;
import com.glhd.tb.app.event.EventRefreshInspList;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetInspList;
import com.glhd.tb.app.utils.MyLog;
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class InspTaskListFragment extends MyBaseFragment implements View.OnClickListener {


    protected ListView listview;
    protected Button allCheckBtn;
    protected Button confirmBtn;
    protected LinearLayout checkLin;
    protected FrameLayout container;
    protected PtrClassicFrameLayout refresh;
    private ItemInspIndexAdapter adapter;
    private ArrayList<BeanAdvert> beans = new ArrayList<>();

    private String stationId;
    private String locationId;
    private String carnoId;
    private String marshallingId;
    private String stationTitle;
    private String floorId;
    private String regionId;
    private String mediatypeId;
    private String trainType;


    private String searchKey;
    private String inspState;
    private int pageNum = 0;
    private String pageSize = "100";

    public InspTaskListFragment(String inspState) {
        this.inspState = inspState;
    }

    public ArrayList<BeanAdvert> getDatas() {
        return beans;
    }

    @Override
    public int initViewId() {
        return R.layout.insp_index_task_list;
    }
    int downY=0;
    int upY;
    @Override
    public void initView() {
        listview = (ListView) rootView.findViewById(R.id.listview);
        adapter = new ItemInspIndexAdapter(getContext(), beans);
        listview.setAdapter(adapter);
        allCheckBtn = (Button) rootView.findViewById(R.id.allCheckBtn);
        allCheckBtn.setOnClickListener(InspTaskListFragment.this);
        confirmBtn = (Button) rootView.findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(InspTaskListFragment.this);
        checkLin = (LinearLayout) rootView.findViewById(R.id.checkLin);
        container = (FrameLayout) rootView.findViewById(R.id.container);
        refresh = (PtrClassicFrameLayout) rootView.findViewById(R.id.refresh);
        refresh.disableWhenHorizontalMove(true);
        refresh.setPtrHandler(new PtrDefaultHandler2() {

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {

                taskListHttpYes(pageNum);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNum = 0;
                taskListHttpYes(pageNum);
            }
        });
//
//        listview.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent ev) {
//                if (ev.getAction() == KeyEvent.ACTION_DOWN) {
//                    downY= (int) ev.getY();
//                }
//
//                if (ev.getAction() == KeyEvent.ACTION_DOWN) {
//                    upY= (int) ev.getY();
//                }
//
//                if(upY-downY>100){
//                    EventRefreshInspHistoryLayout e=new EventRefreshInspHistoryLayout();
//                    e.show=true;
//                    EventBus.getDefault().post(e);
//                }
//                if(upY-downY<-100){
//                    EventRefreshInspHistoryLayout e=new EventRefreshInspHistoryLayout();
//                    e.show=false;
//                    EventBus.getDefault().post(e);
//                }
//
//                return false;
//            }
//        });


        listview.setSelection(0);
        if(stationId==null||"".equals(stationId))
            return ;
        refresh(refresh);
    }


    private void taskListHttpYes(int num) {

        if((stationId==null||"".equals(stationId))&&(trainType==null||"".equals(trainType)))
            return ;

        if (MySp.getUser(getContext()) == null)
            return;
        API.getMyInspList(MySp.getUser(getContext()).getAccountId(),
                inspState,
                num + "",
                pageSize,
                stationId,
                locationId,
                floorId,
                regionId,
                mediatypeId,
                marshallingId,
                trainType,
                new MyHttp.ResultCallback<ResGetInspList>() {
                    @Override
                    public void onSuccess(ResGetInspList res) {
                        refresh.refreshComplete();

                        if (res.getCode() == 0 || res.getCode() == 1) {

                            if (res.getCode() == 1) {
                                MyToast.showMessage(getContext(), res.getMessage());
                            }
                            if (pageNum == 0)
                                beans.clear();
                            pageNum++;
                            beans.addAll(res.getData());

                        }

//                        if(beans.size()==0)
//                            MyToast.showMessage(getContext(),"无数据");

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String message) {
                        refresh.refreshComplete();
                        MyToast.showMessage(getContext(), "系统异常");
                    }
                });

    }


    public int selectAll(boolean boo) {
        for (int i = 0; i < beans.size(); i++) {
            beans.get(i).setChecked(false);
        }
        adapter.setCheckBoxIsShow(boo);
        if (boo) {
            checkLin.setVisibility(View.VISIBLE);
        } else {
            checkLin.setVisibility(View.GONE);

        }
        return beans.size();
    }


    private boolean isAllSelect;

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.allCheckBtn) {
            isAllSelect = !isAllSelect;
            for (int i = 0; i < beans.size(); i++) {
                beans.get(i).setChecked(isAllSelect);
            }
            adapter.notifyDataSetChanged();
            if (isAllSelect) {
                allCheckBtn.setText("取消全选" + "(" + beans.size() + ")");
            } else {
                allCheckBtn.setText("全选");
            }
        } else if (view.getId() == R.id.confirmBtn) {
            ArrayList<BeanAdvert> selectAds = new ArrayList<>();
            for (int i = 0; i < beans.size(); i++) {
                if (beans.get(i).isChecked()) {
                    selectAds.add(beans.get(i));
                }
            }

            if (selectAds.size() == 0) {
                MyToast.showMessage(getContext(), "请选择项目后提交！");
                return;
            }

            EventBus.getDefault().post(new EventCancelMoreSelect());
            Intent intent = new Intent(getActivity(), InspSubmitMoreActivity.class);
            intent.putExtra("beans", selectAds);
            startActivity(intent);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateCheckListForBatch(EventInspSubmitMore event) {

        ArrayList<BeanAdvert> objs = adapter.getObjects();
        for (int i = 0; i < event.ids.size(); i++) {
            for (int j = 0; j < objs.size(); j++) {
                if (event.ids.get(i).equals(objs.get(j).getId())) {
                    objs.remove(j);
                }
            }

        }
        adapter.notifyDataSetChanged();

        if ("1".equals(inspState)) {
            pageNum = 0;
            taskListHttpYes(pageNum);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventInspFilter(EventInspFilter event) {

        if(event.isHistory)
            return ;

        stationId = event.getStationId();
        locationId = event.getLocationId();
        floorId = event.getFloorId();
        regionId = event.getRegionId();
        marshallingId = event.getTrainNo();
        trainType = event.getTrain();

//        pageNum = 0;
//        taskListHttpYes(pageNum);
        listview.setSelection(0);
        refresh(refresh);

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventRefreshInspList(EventRefreshInspList event) {
        pageNum = 0;
        taskListHttpYes(pageNum);
    }
    public void refresh(String mediatypeId){
        this.mediatypeId=mediatypeId;
        pageNum = 0;
        taskListHttpYes(pageNum);
    }

}