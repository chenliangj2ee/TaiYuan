package com.glhd.tb.app.act.repair;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

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

public class RepairListFragment extends MyBaseFragment {


    protected AstListView listview;
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
        accountId=MySp.getUser(getActivity()).getAccountId();
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
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    i = i - 1;
                    Intent intent = new Intent(getContext(), RepariInfoActivity.class);
                    intent.putExtra("bean", datas.get(i));
                    intent.putExtra("edit", type==0?true:false);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
//        getRepairList(page);
    }

    int page;
    int pageSize = 20;

    private void getRepairList(final int p) {

        API.getRepariList(accountId, type + "", p + "", pageSize + "",
                new MyHttp.ResultCallback<ResGetRepairList>() {
                    @Override
                    public void onSuccess(ResGetRepairList res) {
                        listview.stop();
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
                        listview.stop();
                    }
                });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventRefreshRepairList(EventRefreshRepairList event) {
        page = 0;
        getRepairList(page);

    }

}