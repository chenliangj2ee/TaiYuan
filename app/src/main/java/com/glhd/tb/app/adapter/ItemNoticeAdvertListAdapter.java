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
import com.glhd.tb.app.base.bean.BeanAdvert;

public class ItemNoticeAdvertListAdapter extends BaseAdapter {

    private ArrayList<BeanAdvert> objects = new ArrayList<BeanAdvert>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemNoticeAdvertListAdapter(Context context, ArrayList<BeanAdvert> objects) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = objects;
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
            convertView = layoutInflater.inflate(R.layout.item_notice_advert_list, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((BeanAdvert) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(BeanAdvert object, ViewHolder holder) {
        holder.typeSize.setText(object.getTypeTitle() + " " + object.getWhSize());
        holder.number.setText(object.getNumber());
        holder.location.setText(object.getLocation());
    }

    protected class ViewHolder {
        private TextView typeSize;
        private TextView number;
        private TextView location;

        public ViewHolder(View view) {
            typeSize = (TextView) view.findViewById(R.id.type_size);
            number = (TextView) view.findViewById(R.id.number);
            location = (TextView) view.findViewById(R.id.location);
        }
    }
}
