package com.glhd.tb.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.act.inspection.InspWayDetailsActivity;
import com.glhd.tb.app.http.res.WayList;

public class InspWayListAdapter extends RecyclerView.Adapter<InspWayListAdapter.MyViewHolder> {

    private List<WayList.DataBean> mlist =null;

    private Context context;
    private LayoutInflater layoutInflater;

    public InspWayListAdapter(Context context, List<WayList.DataBean> carlist) {
        this.context = context;
        this.mlist = carlist;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public InspWayListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.activity_insp_way_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(InspWayListAdapter.MyViewHolder holder, int position) {
        holder.carNumber.setText(mlist.get(position).getTrainsetName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InspWayDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView carNumber;

        public MyViewHolder(View itemView) {
            super(itemView);
            carNumber = (TextView) itemView.findViewById(R.id.car_number);
        }
    }
}
