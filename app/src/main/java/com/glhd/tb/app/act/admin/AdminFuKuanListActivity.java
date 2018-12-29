package com.glhd.tb.app.act.admin;

import android.os.Bundle;
import android.view.View;

import com.ast365.library.listview.AstListView;
import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.ItemAdminFukuanTixingAdapter;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.BaseRes;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResFuKuanList;
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;
import com.google.gson.Gson;

import java.util.ArrayList;

public class AdminFuKuanListActivity extends BaseActivity {

    private AstListView listview;
    private ArrayList<ResFuKuanList.DataBean> beans = new ArrayList<>();
    private ItemAdminFukuanTixingAdapter adapter;
    private int pageNum = 0;
    private String pageSize = "20";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_duikuan_list);

        listview = (AstListView) findViewById(R.id.listview);
        adapter = new ItemAdminFukuanTixingAdapter(this, beans);
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
        API.getAdminFuKuanList(MySp.getUser(this).getAccountId(), pageNum + "", pageSize, new MyHttp.ResultCallback<ResFuKuanList>() {
            @Override
            public void onSuccess(ResFuKuanList res) {
                listview.stop();
                if (res != null && res.getCode() == 0) {
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

    public void allSelectAction(View v) {

        for (int i = 0; i < beans.size(); i++) {
            beans.get(i).setCheck(true);
        }
        adapter.notifyDataSetChanged();
    }

    public void clearAction(View v) {
        for (int i = 0; i < beans.size(); i++) {
            beans.get(i).setCheck(false);
        }
        adapter.notifyDataSetChanged();
    }

    public void cuikuanAction(View v) {
        ArrayList<String> ss = new ArrayList<>();
        for (int i = 0; i < beans.size(); i++) {
            if (beans.get(i).isCheck())
                ss.add(beans.get(i).getPlanId());
        }
        if(ss.isEmpty())
            return ;

        API.getAdminCuikuan(MySp.getUser(this).getAccountId(), new Gson().toJson(ss), new MyHttp.ResultCallback<BaseRes>() {
            @Override
            public void onSuccess(BaseRes res) {
                listview.stop();
                if (res != null && res.getCode() == 0) {

                    pageNum = 0;
                    taskListHttp(pageNum);
                } else {
                    MyToast.showMessage(getApplicationContext(), "催款失败，请重试");
                }
            }

            @Override
            public void onError(String message) {
                listview.stop();
            }
        });
    }
}
