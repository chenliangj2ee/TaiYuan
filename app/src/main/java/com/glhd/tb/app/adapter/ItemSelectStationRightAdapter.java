package com.glhd.tb.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.base.bean.BeanAasLocation;

import java.util.ArrayList;

public class ItemSelectStationRightAdapter extends BaseAdapter {

    private ArrayList<BeanAasLocation> objects = new ArrayList<BeanAasLocation>();

    private Context context;
    private LayoutInflater layoutInflater;
    private int selectIndex=0;
    public ItemSelectStationRightAdapter(Context context, ArrayList<BeanAasLocation> objects) {
        this.context = context;
        this.objects=objects;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public BeanAasLocation getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_select_station_right, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((BeanAasLocation)getItem(position), (ViewHolder) convertView.getTag(),position);
        return convertView;
    }

    private void initializeViews(BeanAasLocation object, ViewHolder holder,int posi) {
        holder.title.setText(object.getTitle());
//        if(selectIndex==posi){
//            holder.title.setBackgroundResource(R.drawable.selector_selet_station_right_selected);
//        }else{
//            holder.title.setBackgroundResource(R.drawable.selector_selet_station_right);
//        }
    }

    protected class ViewHolder {
        private TextView title;

        public ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.title);
        }
    }
}
