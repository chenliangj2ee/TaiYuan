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
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;

import java.util.ArrayList;

/**
 * 列车内容展示
 */
public class TrainFragment extends Fragment {


    private ArrayList<GetInspRange.DataBean> data0 = new ArrayList<>();
    private ArrayList<GetInspRange.DataBean> data1 = new ArrayList<>();

    private ListView listView0;
    private ListView listView1;
    private ItemInspRangeAdapter adapter0;
    private ItemInspRangeAdapter adapter1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_train, null);
        initView(view);
        initEvent();
        return view;
    }

    private void initView(View view) {
        listView0 = view.findViewById(R.id.list_view0);
        listView1 = view.findViewById(R.id.list_view1);

        adapter0 = new ItemInspRangeAdapter(getActivity());
        adapter1 = new ItemInspRangeAdapter(getActivity());

        adapter0.setStringList(data0);
        listView0.setAdapter(adapter0);

        adapter1.setStringList(data1);
        listView1.setAdapter(adapter1);

        getData(0,"");

    }

    private void initEvent() {
        listView0.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter0.setSelectPosition(position);
                trainNo = "";
//                if ("".equals(data0.get(position).getId())) {
//
//                    adapter1.clearStringList();
//                    return;
//                }

                adapter1.clearStringList();

                train = data0.get(position).getId();
                trainName=data0.get(position).getTitle();

                if("DCZ".equals(data0.get(position).getId())){
                    getData(1,"5");
                }else{
                    getData(1,"6");
                }


            }
        });

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                adapter1.setSelectPosition(position);
//                if ("".equals(data1.get(position).getId())) {
//
//                    return;
//                }
                trainNo = data1.get(position).getId();
                trainNoName = data1.get(position).getTitle();

            }
        });

    }


    public String accountId = "";
    public String taskState = "0";
    public String taskType = "列车";
    public String stationId = "";
    public String floorId = "";
    public String locationId = "";
    public String regionId = "";
    public String train = "";
    public String trainNo = "";

    public String trainName = "";
    public String trainNoName = "";

    private void getData(final int listId,String posi) {

        final ProgressDialog dialog=new ProgressDialog(getContext());
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
                posi,
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

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }
}
