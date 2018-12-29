package com.glhd.tb.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.act.inspection.InspHistoryActivity;
import com.glhd.tb.app.act.inspection.InspInfoActivity;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.utils.MyImage;

public class ItemInspHistoryAdapter extends BaseAdapter {

    private ArrayList<BeanAdvert> objects = new ArrayList<>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemInspHistoryAdapter(Context context, ArrayList<BeanAdvert> objects) {
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
            convertView = layoutInflater.inflate(R.layout.item_insp_history, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((BeanAdvert) getItem(position), (ViewHolder) convertView.getTag(), convertView);
        return convertView;
    }

    private void initializeViews(final BeanAdvert b, ViewHolder h, View convertView) {

        h.time.setText("");
        h.inspCycle.setBackgroundColor(Color.TRANSPARENT);


        MyImage.load(context, b.getImage(), h.advertIcon);
        h.coding.setText("编码:" + b.getCoding());
        h.location.setText(b.getLocation());

        h.typeSize.setText(b.getTypeTitle() + "   " + b.getWhSize());
        if ("0".equals(b.getInspCycle()) || "日检".equals(b.getInspCycle())) {
            h.inspCycle.setText("日检 ");
            h.inspCycle.setBackgroundResource(R.drawable.rijian_bg);
        } else if ("1".equals(b.getInspCycle()) || "周检".equals(b.getInspCycle())) {
            h.inspCycle.setText("周检 ");
            h.inspCycle.setBackgroundResource(R.drawable.zhoujian_bg);
        } else if ("2".equals(b.getInspCycle()) || "月检".equals(b.getInspCycle())) {
            h.inspCycle.setText("月检 ");
            h.inspCycle.setBackgroundResource(R.drawable.yuejian_bg);
        }


        if (b.getInsp() != null) {
            h.time.setText("巡检时间 " + b.getInsp().getInspDate());
            if ("0".equals(b.getInsp().getInspStatus())) {
                h.inspStatusIcon.setImageResource(R.drawable.zhengchang);
                h.inspStatusIcon.setVisibility(View.VISIBLE);
            }
            if ("1".equals(b.getInsp().getInspStatus())) {
                h.inspStatusIcon.setImageResource(R.drawable.yichang);
                h.inspStatusIcon.setVisibility(View.VISIBLE);
            }
        } else {
            h.inspStatusIcon.setVisibility(View.GONE);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InspInfoActivity.class);
                intent.putExtra("bean",b);
                context.startActivity(intent);
            }
        });
    }

    static class ViewHolder {
        protected ImageView advertIcon;
        protected TextView coding;
        protected TextView typeSize;
        protected TextView inspCycle;
        protected TextView location;
        protected TextView time;
        protected ImageView inspStatusIcon;

        ViewHolder(View rootView) {
            initView(rootView);
        }

        private void initView(View rootView) {
            advertIcon = (ImageView) rootView.findViewById(R.id.advert_icon);
            coding = (TextView) rootView.findViewById(R.id.coding);
            typeSize = (TextView) rootView.findViewById(R.id.type_size);
            inspCycle = (TextView) rootView.findViewById(R.id.insp_cycle);
            location = (TextView) rootView.findViewById(R.id.location);
            time = (TextView) rootView.findViewById(R.id.time);
            inspStatusIcon = (ImageView) rootView.findViewById(R.id.insp_status_icon);
        }
    }
}
