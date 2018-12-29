package com.glhd.tb.app.customer.fg;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.ast365.library.listview.FullListView;
import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.ItemFukuaiXiangQingAdapter;
import com.glhd.tb.app.base.bean.BeanPaymentInfo;

import java.util.ArrayList;

public class PayInfoFragment extends Fragment  {

    private FullListView listview;
    private ItemFukuaiXiangQingAdapter adapter;
    private ArrayList<BeanPaymentInfo> beans=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listview_pay_info, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listview = (FullListView) view.findViewById(R.id.listview);
        adapter=new ItemFukuaiXiangQingAdapter(getContext(),beans);
        listview.setAdapter(adapter);
    }
    public void setDatas(ArrayList<BeanPaymentInfo> beans){
        this.beans.clear();
        this.beans.addAll(beans);
        adapter.notifyDataSetChanged();
    }

}
