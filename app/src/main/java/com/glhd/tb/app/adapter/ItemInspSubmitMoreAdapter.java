package com.glhd.tb.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.utils.MyToast;

public class ItemInspSubmitMoreAdapter extends BaseAdapter {

    private ArrayList<BeanAdvert> objects = new ArrayList<BeanAdvert>();

    private Context context;
    private LayoutInflater layoutInflater;
    private ListView listView;
    public ItemInspSubmitMoreAdapter(Context context, ArrayList<BeanAdvert> objects,ListView listView) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = objects;
        this.listView=listView;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public BeanAdvert getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_insp_submit_more, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((BeanAdvert) getItem(position), (ViewHolder) convertView.getTag());

        return convertView;
    }

    private void initializeViews(BeanAdvert b, ViewHolder h) {
        h.coding.setText("编码:" + b.getCoding());
        h.location.setText(b.getLocationdescribe());
        h.typeSize.setText(b.getTypeTitle()+"  "+b.getWhSize());
        h.stationName.setText(b.getProperystation());
    }

    protected class ViewHolder {
        private TextView coding;
        private TextView stationName;
        private TextView typeSize;
        private TextView location;

        public ViewHolder(View view) {
            coding = (TextView) view.findViewById(R.id.coding);
            stationName = (TextView) view.findViewById(R.id.station_name);
            typeSize = (TextView) view.findViewById(R.id.type_size);
            location = (TextView) view.findViewById(R.id.location);
        }
    }
}
