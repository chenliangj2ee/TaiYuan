package com.glhd.tb.app.act.repair;

import com.ast365.library.listview.AstListView;
import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.ItemInspHistoryAdapter;
import com.glhd.tb.app.adapter.ItemRepairListAdapter;
import com.glhd.tb.app.base.MyBaseFragment;
import com.glhd.tb.app.base.bean.BeanRepair;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetRepairList;
import com.glhd.tb.app.utils.MySp;

import java.util.ArrayList;

public class RepairListFragment extends MyBaseFragment {


    protected AstListView listview;
    private ArrayList<BeanRepair> datas = new ArrayList<>();
    private ItemRepairListAdapter adapter;
    int type;

    @Override
    public int initViewId() {
        return R.layout.fragment_repair_lsit;
    }


    public RepairListFragment(int type) {
        this.type = type;
    }

    @Override
    public void initView() {
        listview = (AstListView) findViewById(R.id.listview);
        adapter = new ItemRepairListAdapter(getContext(), datas);
        listview.setAdapter(adapter);
        listview.setPullLoadEnable(true);
        listview.setOnRefreshListener(new AstListView.RefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                getRepairList(page);
            }

            @Override
            public void onLoadMore() {
                getRepairList(page);
            }
        });
        getRepairList(page);

    }

    int page;
    int pageSize = 20;

    private void getRepairList(int p) {
        API.getRepariList(MySp.getUser(getContext()).getAccountId(), type + "", p + "", pageSize + "",
                new MyHttp.ResultCallback<ResGetRepairList>() {
                    @Override
                    public void onSuccess(ResGetRepairList res) {
                        listview.stop();
                        if (res.getCode() == 0) {
                            if (page == 0)
                                datas.clear();
                            datas.addAll(res.getData());
                            adapter.notifyDataSetChanged();
                            page++;
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