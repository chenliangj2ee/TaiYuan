package com.glhd.tb.app.act.repair;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ast365.library.listview.AstListView;
import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.ItemInspHistoryAdapter;
import com.glhd.tb.app.adapter.ItemRepairListAdapter;
import com.glhd.tb.app.base.MyBaseFragment;
import com.glhd.tb.app.base.bean.BeanRepair;
import com.glhd.tb.app.base.bean.BeanUser;
import com.glhd.tb.app.event.EventRefreshRepairList;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.BeanBaoXiu;
import com.glhd.tb.app.http.res.ResGetRepairList;
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

@SuppressLint("ValidFragment")
public class RepairListFragment extends MyBaseFragment {

    protected ListView listview;
    protected PtrClassicFrameLayout refresh;
    private ArrayList<BeanBaoXiu> datas = new ArrayList<>();
    private ItemRepairListAdapter adapter;
    String first,last;
    int type;
    private String accountId;

    @Override
    public int initViewId() {
        return R.layout.fragment_repair_lsit;
    }


    @SuppressLint("ValidFragment")
    public RepairListFragment(String first, String last) {
        this.first = first;
        this.last = last;
    }
    @SuppressLint("ValidFragment")
    public RepairListFragment(int type) {
        this.type = type;
    }

    @Override
    public void initView() {
        accountId = MySp.getUser(getActivity()).getAccountId();
        listview = (ListView) findViewById(R.id.listview);
        adapter = new ItemRepairListAdapter(getContext(), datas);
        listview.setAdapter(adapter);
        refresh = (PtrClassicFrameLayout) rootView.findViewById(R.id.refresh);
        refresh.disableWhenHorizontalMove(true);
        refresh.setPtrHandler(new PtrDefaultHandler2() {

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                getRepairList(page);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 0;
                getRepairList(page);
            }
        });
        listview.setSelection(0);
        refresh(refresh);
    }

    int page;
    int pageSize = 20;

    private void getRepairList(final int p) {


        String userType="";
        BeanUser user=MySp.getUser(getContext());
        if(user.getType().contains(",")){
            userType=user.getCurType();
        }else{
            userType=user.getType();
        }

        API.getRepariList(accountId, first, last, p + "", pageSize + "",
                new MyHttp.ResultCallback<ResGetRepairList>() {
                    @Override
                    public void onSuccess(ResGetRepairList res) {
                        refresh.refreshComplete();
                        if (res.getCode() == 0) {
                            if (p == 0)
                                datas.clear();
                            datas.addAll(res.getData());
                            Log.i(TAG, "baoxiu"+datas.toString());
                            adapter.notifyDataSetChanged();
                            page++;
                        } else {

                        }
                    }

                    @Override
                    public void onError(String message) {
                        refresh.refreshComplete();
                    }
                });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventRefreshRepairList(EventRefreshRepairList event) {
        page = 0;
        getRepairList(page);

    }

}