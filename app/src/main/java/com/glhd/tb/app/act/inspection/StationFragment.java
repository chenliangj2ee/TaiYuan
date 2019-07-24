package com.glhd.tb.app.act.inspection;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.ItemInspRangeAdapter;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.GetInspRange;
import com.glhd.tb.app.utils.MyLog;
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 车站内容展示
 */

public class StationFragment extends Fragment {


    private ArrayList<GetInspRange.DataBean> data0 = new ArrayList<>();
    private ArrayList<GetInspRange.DataBean> data1 = new ArrayList<>();
    private ArrayList<GetInspRange.DataBean> data2 = new ArrayList<>();
    private ArrayList<GetInspRange.DataBean> data3 = new ArrayList<>();

    private ListView listView0;
    private ListView listView1;
    private ListView listView2;
    private ListView listView3;
    private ItemInspRangeAdapter adapter0;
    private ItemInspRangeAdapter adapter1;
    private ItemInspRangeAdapter adapter2;
    private ItemInspRangeAdapter adapter3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_station, null);
        initView(view);
        initEvent();
        return view;
    }

    private void initView(View view) {
        listView0 = view.findViewById(R.id.list_view0);
        listView1 = view.findViewById(R.id.list_view1);
        listView2 = view.findViewById(R.id.list_view2);
        listView3 = view.findViewById(R.id.list_view3);

        adapter0 = new ItemInspRangeAdapter(getActivity());
        adapter1 = new ItemInspRangeAdapter(getActivity());
        adapter2 = new ItemInspRangeAdapter(getActivity());
        adapter3 = new ItemInspRangeAdapter(getActivity());

        adapter0.setStringList(data0);
        listView0.setAdapter(adapter0);

        adapter1.setStringList(data1);
        listView1.setAdapter(adapter1);

        adapter2.setStringList(data2);
        listView2.setAdapter(adapter2);

        adapter3.setStringList(data3);
        listView3.setAdapter(adapter3);

        getData(0, "1");

    }

    private void initEvent() {
        listView0.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter0.setSelectPosition(position);
//                if ("".equals(data0.get(position).getId())) {
//
//                    floorId = "";
//                    floorName = "";
//                    locationId = "";
//                    locationName = "";
//                    regionId = "";
//                    regionName = "";
//                    adapter1.clearStringList();
//                    adapter2.clearStringList();
//                    adapter3.clearStringList();
//
//                    return;
//                }


                adapter1.clearStringList();
                adapter2.clearStringList();
                adapter3.clearStringList();

                stationId = data0.get(position).getId();
                stationName = data0.get(position).getTitle();
                floorId = "";
                locationId = "";
                regionId = "";

                floorName = "";
                locationName = "";
                regionName = "";

                getData(1, "2");

            }
        });

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                adapter1.setSelectPosition(position);
//                if ("".equals(data1.get(position).getId())) {
//                    floorId = "";
//                    locationId = "";
//                    regionId = "";
//
//                    floorName = "";
//                    locationName = "";
//                    regionName = "";
//                    adapter2.clearStringList();
//                    adapter3.clearStringList();
//
//                    return;
//                }

                adapter2.clearStringList();
                adapter3.clearStringList();

                floorId = data1.get(position).getId();
                floorName = data1.get(position).getTitle();

                locationId = "";
                regionId = "";
                locationName = "";
                regionName = "";
                getData(2, "3");


            }
        });

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter2.setSelectPosition(position);
//                if ("".equals(data2.get(position).getId())) {
//                    locationId = "";
//                    regionId = "";
//
//                    locationName = "";
//                    regionName = "";
//                    adapter3.clearStringList();
//
//                    return;
//                }

                adapter3.clearStringList();
                locationId = data2.get(position).getId();
                locationName = data2.get(position).getTitle();
                regionId = "";
                regionName = "";
                getData(3, "4");

            }
        });

        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                regionId = data3.get(position).getId();
                regionName = data3.get(position).getTitle();
                adapter3.setSelectPosition(position);
                adapter3.notifyDataSetChanged();
            }
        });
    }


    public String accountId = "";
    public String taskState = "0";
    public String taskType = "车站";
    public String stationId = "";
    public String floorId = "";
    public String locationId = "";
    public String regionId = "";
    public String stationName = "";
    public String floorName = "";
    public String locationName = "";
    public String regionName = "";
    public String train = "";

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    private void getData(final int listId, String selType) {

        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("加载中...");
        dialog.show();

        accountId = MySp.getUser(getActivity()).getAccountId();
        API.getMyInspRange(accountId,
                taskState,
                taskType,
                stationId,
                locationId,
                floorId,
                regionId,
                train,
                selType,
                new MyHttp.ResultCallback<GetInspRange>() {
                    @Override
                    public void onSuccess(GetInspRange res) {
                        dialog.dismiss();
                        if (res.getCode() == 0) {
                            if (0 == listId) {
                                data0.clear();
                                data0.addAll(res.getData());
                                adapter0.notifyDataSetChanged();

                            }
                            if (1 == listId) {
                                data1.clear();
                                data1.addAll(res.getData());
                                adapter1.notifyDataSetChanged();
                            }
                            if (2 == listId) {
                                data2.clear();
                                data2.addAll(res.getData());
                                adapter2.notifyDataSetChanged();
                            }
                            if (3 == listId) {
                                data3.clear();
                                data3.addAll(res.getData());
                                adapter3.notifyDataSetChanged();
                            }

//                            if(res.getData()!=null&&res.getData().size()==0){
//                                MyToast.showMessage(getContext(),"无数据");
//                            }
                        }


                    }

                    @Override
                    public void onError(String message) {
                        dialog.dismiss();
                    }
                });
    }


}
