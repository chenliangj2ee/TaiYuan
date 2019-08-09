package com.glhd.tb.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.glhd.tb.app.R;

public class InspWayListAdapter extends BaseAdapter {

    private List<Object> objects = new ArrayList<Object>();

    private Context context;
    private LayoutInflater layoutInflater;

    public InspWayListAdapter(Context context, List<Object> carlist) {
        this.context = context;
        this.objects = carlist;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_insp_way_list, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((Object) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(Object object, ViewHolder holder) {
        //TODO implement
    }

    protected class ViewHolder {
        private TextView carNumber;

        public ViewHolder(View view) {
            carNumber = (TextView) view.findViewById(R.id.car_number);
        }
    }
}
