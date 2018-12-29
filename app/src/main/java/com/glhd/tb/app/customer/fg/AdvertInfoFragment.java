package com.glhd.tb.app.customer.fg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ast365.library.listview.FullListView;
import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.ItemFukuaiXiangQingAdapter;
import com.glhd.tb.app.adapter.ItemMeiTiInfoAdapter;
import com.glhd.tb.app.base.bean.BeanAdvert;

import java.util.ArrayList;

public class AdvertInfoFragment extends Fragment  {

    private LinearLayout container;
    private FullListView listview;
    private ItemMeiTiInfoAdapter adapter;
    private ArrayList<BeanAdvert> beans=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listview_advert_info, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        container = (LinearLayout) view.findViewById(R.id.container);
        listview = (FullListView) view.findViewById(R.id.listview);
        adapter=new ItemMeiTiInfoAdapter(getContext(),beans);
        listview.setAdapter(adapter);
    }
    public void setDatas(ArrayList<BeanAdvert> beans){
        this.beans.clear();
        this.beans.addAll(beans);
        adapter.notifyDataSetChanged();
    }

}
