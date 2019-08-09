package com.glhd.tb.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.base.bean.BeanRepair;
import com.glhd.tb.app.utils.MyImage;

public class ItemRepairListAdapter extends BaseAdapter {

    private ArrayList<BeanRepair> objects = new ArrayList<BeanRepair>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemRepairListAdapter(Context context, ArrayList<BeanRepair> objects) {
        this.context = context;
        this.objects = objects;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public BeanRepair getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_repair_list, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((BeanRepair) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(BeanRepair b, ViewHolder h) {

//        if(b.getWhSize()==null||"".equals(b.getWhSize())){
//            h.coding.setText(""+b.getCoding());
//        }else{
//
//            h.coding.setText("编码："+b.getCoding());
//        }

//todo 保修历史 三种类型：车站单个保修 车站批量保修  列车巡检
//        h.stationName.setText(b.getProperystation());
////        h.typeSize.setText(b.getMediatype()+"  数量:"+b.getMediaNumber());
//        h.location.setText(b.getMedialocation()+"  "+b.getMedialocationdescribe());
//
//        h.time.setText("巡检时间："+b.getInspTime());

    }

    protected class ViewHolder {
        private CheckBox avdentCheckbox;
        private TextView stationPeople;
        private TextView stationName;
        private TextView location;
        private TextView time;
        private TextView sum;
        private TextView number;

        public ViewHolder(View view) {
            avdentCheckbox = (CheckBox) view.findViewById(R.id.avdent_checkbox);

            stationPeople = (TextView) view.findViewById(R.id.station_people);
            stationName = (TextView) view.findViewById(R.id.station_name);
            location = (TextView) view.findViewById(R.id.location);
            time = (TextView) view.findViewById(R.id.time);
            sum = (TextView) view.findViewById(R.id.sum);
            number = (TextView) view.findViewById(R.id.number);
        }
    }


}
