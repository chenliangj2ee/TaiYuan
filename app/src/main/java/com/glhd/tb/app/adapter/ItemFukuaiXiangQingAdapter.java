package com.glhd.tb.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.base.bean.BeanPaymentInfo;
import com.glhd.tb.app.utils.MyDate;

public class ItemFukuaiXiangQingAdapter extends BaseAdapter {

    private ArrayList<BeanPaymentInfo> beans = new ArrayList<BeanPaymentInfo>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemFukuaiXiangQingAdapter(Context context, ArrayList<BeanPaymentInfo> beans) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.beans = beans;
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public BeanPaymentInfo getItem(int position) {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_fukuai_xiang_qing, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((BeanPaymentInfo) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(BeanPaymentInfo b, ViewHolder holder) {
        holder.name.setText(b.getPaymentTitle());
        holder.baiFenBi.setText(b.getPaymentPercent());
        holder.price.setText(b.getAmount());
        holder.status.setText(b.getStatusTitle());
        holder.date.setText(b.getPaymentDate());
    }

    protected class ViewHolder {
        private LinearLayout container;
        private TextView name;
        private TextView baiFenBi;
        private TextView price;
        private TextView status;
        private TextView date;

        public ViewHolder(View view) {
            container = (LinearLayout) view.findViewById(R.id.container);
            name = (TextView) view.findViewById(R.id.name);
            baiFenBi = (TextView) view.findViewById(R.id.bai_fen_bi);
            price = (TextView) view.findViewById(R.id.price);
            status = (TextView) view.findViewById(R.id.status);
            date = (TextView) view.findViewById(R.id.date);
        }
    }
}
