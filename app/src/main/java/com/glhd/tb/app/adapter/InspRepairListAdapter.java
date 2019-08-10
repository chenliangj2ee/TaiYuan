package com.glhd.tb.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.http.res.BeanBaoXiu;

import java.util.ArrayList;

public class InspRepairListAdapter extends RecyclerView.Adapter<InspRepairListAdapter.MyViewHolder> {
    private ArrayList<BeanBaoXiu> mlist = new ArrayList<BeanBaoXiu>();
    private Context mContext;
    private LayoutInflater inflater;

    public InspRepairListAdapter(Context context, ArrayList<BeanBaoXiu> objects) {
        this.mContext = context;
        this.mlist = objects;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_repair_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BeanBaoXiu beanBaoXiu = mlist.get(position);
        String repairState = mlist.get(position).getRepairState();
        if (repairState.equals("0")) {
            holder.time.setVisibility(View.GONE);
            holder.repairState.setTextColor(Color.RED);
            holder.repairState.setVisibility(View.VISIBLE);
            holder.repairState.setText("未维修");
        } else {
            holder.time.setVisibility(View.VISIBLE);
            holder.time.setText("维修时间："+beanBaoXiu.getRepairDate());
            holder.repairState.setTextColor(Color.BLUE);
            holder.repairState.setVisibility(View.GONE);
            holder.repairState.setText("已维修");

        }
        holder.stationName.setText(beanBaoXiu.getStation());
        holder.sum.setText(beanBaoXiu.getTotalNumber() + "个");
        holder.location.setText(beanBaoXiu.getTrajectory());
        holder.stationName.setText(beanBaoXiu.getStation());
        holder.number.setText(beanBaoXiu.getMediatype());
        holder.stationPeople.setText("维修负责人："+beanBaoXiu.getResponsible());
        holder.presentationDate.setText(beanBaoXiu.getPresentationDate());


    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CheckBox avdentCheckbox;
        private TextView stationPeople;
        private TextView stationName;
        private TextView location;
        private TextView time;
        private TextView sum;
        private TextView number;
        private TextView repairState;
        private TextView presentationDate;

        public MyViewHolder(View view) {
            super(view);
            avdentCheckbox = (CheckBox) view.findViewById(R.id.avdent_checkbox);

            stationPeople = (TextView) view.findViewById(R.id.station_people);
            stationName = (TextView) view.findViewById(R.id.station_name);
            location = (TextView) view.findViewById(R.id.location);
            time = (TextView) view.findViewById(R.id.time);
            sum = (TextView) view.findViewById(R.id.sum);
            number = (TextView) view.findViewById(R.id.number);
            repairState = (TextView) view.findViewById(R.id.repairState);
            presentationDate = (TextView) view.findViewById(R.id.presentationDate);
        }
    }
}
