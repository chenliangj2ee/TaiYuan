package com.glhd.tb.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.utils.MyImage;

public class ItemMeiTiInfoAdapter extends BaseAdapter {

    private ArrayList<BeanAdvert> beans = new ArrayList<BeanAdvert>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemMeiTiInfoAdapter(Context context, ArrayList<BeanAdvert> beans) {
        this.context = context;
        this.beans = beans;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public BeanAdvert getItem(int position) {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_mei_ti_info, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((BeanAdvert) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(BeanAdvert b, ViewHolder holder) {
        holder.id.setText("编码:" +b.getCoding());
        holder.name.setText(b.getTypeTitle()+"  "+b.getWhSize()+"  "+b.getNumber());
        holder.location.setText(b.getLocation());
        MyImage.load(context, b.getImage(), holder.icon);
    }

    protected class ViewHolder {
        private LinearLayout container;
        private ImageView icon;
        private TextView id;
        private TextView name;
        private TextView location;

        public ViewHolder(View view) {
            container = (LinearLayout) view.findViewById(R.id.container);
            icon = (ImageView) view.findViewById(R.id.icon);
            id = (TextView) view.findViewById(R.id.id);
            name = (TextView) view.findViewById(R.id.name);
            location = (TextView) view.findViewById(R.id.location);
        }
    }
}
