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

        if(b.getWhSize()==null||"".equals(b.getWhSize())){
            h.coding.setText(""+b.getCoding());
        }else{

            h.coding.setText("编码："+b.getCoding());
        }


        h.stationName.setText(b.getProperystation());
        h.typeSize.setText(b.getMediatype()+"  数量:"+b.getMediaNumber());
        h.location.setText(b.getMedialocation()+"  "+b.getMedialocationdescribe());

        h.time.setText("巡检时间："+b.getInspTime());
        MyImage.load(context,b.getImage(),h.advertIcon);

    }

    protected class ViewHolder {
        private CheckBox avdentCheckbox;
        private ImageView advertIcon;
        private TextView coding;
        private TextView stationName;
        private TextView typeSize;
        private TextView location;
        private TextView time;

        public ViewHolder(View view) {
            avdentCheckbox = (CheckBox) view.findViewById(R.id.avdent_checkbox);
            advertIcon = (ImageView) view.findViewById(R.id.advert_icon);
            coding = (TextView) view.findViewById(R.id.coding);
            stationName = (TextView) view.findViewById(R.id.station_name);
            typeSize = (TextView) view.findViewById(R.id.type_size);
            location = (TextView) view.findViewById(R.id.location);
            time = (TextView) view.findViewById(R.id.time);
        }
    }


}
