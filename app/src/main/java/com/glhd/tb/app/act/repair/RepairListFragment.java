package com.glhd.tb.app.act.repair;

import android.content.Intent;
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
import com.glhd.tb.app.event.EventRefreshRepairList;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetRepairList;
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class RepairListFragment extends MyBaseFragment {

    protected ListView listview;
    protected PtrClassicFrameLayout refresh;
    private ArrayList<BeanRepair> datas = new ArrayList<>();
    private ItemRepairListAdapter adapter;
    int type;
    private String accountId;

    @Override
    public int initViewId() {
        return R.layout.fragment_repair_lsit;
    }


    public RepairListFragment(int type) {
        this.type = type;
    }

    @Override
    public void initView() {
        accountId = MySp.getUser(getActivity()).getAccountId();
        listview = (ListView) findViewById(R.id.listview);
        adapter = new ItemRepairListAdapter(getContext(), datas);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Intent intent = new Intent(getContext(), RepariInfoActivity.class);
                    intent.putExtra("bean", datas.get(i));
                    intent.putExtra("edit", type == 0 ? true : false);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

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

        API.getRepariList(accountId, MySp.getUser(getContext()).getType(), type == -1 ? null : type + "", p + "", pageSize + "",
                new MyHttp.ResultCallback<ResGetRepairList>() {
                    @Override
                    public void onSuccess(ResGetRepairList res) {
                        refresh.refreshComplete();
                        if (res.getCode() == 0) {
                            if (p == 0)
                                datas.clear();
                            datas.addAll(res.getData());
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