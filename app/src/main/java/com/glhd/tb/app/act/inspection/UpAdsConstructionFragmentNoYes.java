package com.glhd.tb.app.act.inspection;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

import com.ast365.library.listview.AstListView;
import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.act.LoginActivity;
import com.glhd.tb.app.adapter.ItemUpAdsCusNoYesAdapter;
import com.glhd.tb.app.base.MyBaseFragment;
import com.glhd.tb.app.event.EventConstructionSubmit;
import com.glhd.tb.app.event.EventExit;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResAdsConstruction;
import com.glhd.tb.app.http.res.ResUpgrade;
import com.glhd.tb.app.utils.MyEvent;
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * 上刊施工>已完成，未完成
 */
@SuppressLint("ValidFragment")
public class UpAdsConstructionFragmentNoYes extends MyBaseFragment {

    protected AstListView listview;
    private ArrayList<ResAdsConstruction.DataBean> datas = new ArrayList<>();
    private ItemUpAdsCusNoYesAdapter adapter;

    private String accountId = "";//(用户主键)
    private String type = "1";//(施工状态;0-已施工1-未施工)
    private int pageNo = 0;//(页数)
    private int pageSize = 20;//(每页加载数量)

    public UpAdsConstructionFragmentNoYes(String type) {
        this.type = type;
    }

    @Override
    public int initViewId() {
        return R.layout.fragment_up_ads_construction_no_yes_list;
    }

    @Override
    public void initView() {

        EventBus.getDefault().register(this);
        accountId = MySp.getUser(getContext()).getAccountId();


        adapter = new ItemUpAdsCusNoYesAdapter(getContext(), datas);
        listview = (AstListView) rootView.findViewById(R.id.listview);
        listview.setAdapter(adapter);
        listview.setOnRefreshListener(new AstListView.RefreshListener() {
            @Override
            public void onRefresh() {
                listview.reset();
                pageNo = 0;
                getData();
            }

            @Override
            public void onLoadMore() {
                getData();
            }
        });
        listview.setPullLoadEnable(false);

        if ("1".equals(type)) {
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent=new Intent(getContext(),InspConstructionSubmitActivity.class);
                    intent.putExtra("data",datas.get(i-1));
                    startActivity(intent);
                }
            });
        }else{
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent=new Intent(getContext(),InspConstructionInfoActivity.class);
                    intent.putExtra("data",datas.get(i-1));
                    startActivity(intent);
                }
            });
        }


        autoRefresh();
    }

    public void autoRefresh(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                MyEvent.touch(listview);
            }
        }, 200);
    }


    private void getData() {
        API.getCunstructionByType(accountId, type, pageNo, pageSize, new MyHttp.ResultCallback<ResAdsConstruction>() {
            @Override
            public void onSuccess(ResAdsConstruction res) {
                listview.stop();
                if (res != null && res.getData() != null) {
                    if (pageNo == 0)
                        datas.clear();
                    datas.addAll(res.getData());
                    adapter.notifyDataSetChanged();
                    pageNo++;
                    if(res.getData().size()<pageSize){
                        listview.setPullLoadEnable(false);
                    }else{
                        listview.setPullLoadEnable(true);
                    }

                }
            }

            @Override
            public void onError(String message) {
                listview.stop();
                MyToast.showMessage(getContext(), "数据加载失败");
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eixt(EventConstructionSubmit event) {
        listview.reset();
        pageNo = 0;
        getData();
    }

}
