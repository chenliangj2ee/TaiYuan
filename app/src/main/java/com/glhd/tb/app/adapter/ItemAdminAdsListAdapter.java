package com.glhd.tb.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.act.admin.AdminAdvertInfoActivity;
import com.glhd.tb.app.act.admin.AdminInspInfoActivity;
import com.glhd.tb.app.act.customer.CusInspInfoActivity;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.utils.MyImage;

import java.util.ArrayList;

public class ItemAdminAdsListAdapter extends BaseAdapter {

    private ArrayList<BeanAdvert> objects = new ArrayList<BeanAdvert>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemAdminAdsListAdapter(Context context, ArrayList<BeanAdvert> objects) {
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
            convertView = layoutInflater.inflate(R.layout.item_admin_ads_list, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((BeanAdvert) getItem(position), (ViewHolder) convertView.getTag(),convertView);
        return convertView;
    }

    private void initializeViews(final BeanAdvert object, ViewHolder holder, View view) {

        MyImage.load(context,object.getImage(), holder.advertIcon);
        holder.coding.setText("编码:"+object.getCoding());
        holder.typeSize.setText(object.getTypeTitle()+"  "+object.getWhSize()+"  "+object.getNumber());
        holder.location.setText(object.getLocation());


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,AdminAdvertInfoActivity.class);
                intent.putExtra("data",object);
                context.startActivity(intent);
            }
        });
    }

    protected class ViewHolder {
        private ImageView advertIcon;
        private TextView coding;
        private TextView typeSize;
        private TextView location;
        private ImageView inspStatusIcon;

        public ViewHolder(View view) {
            advertIcon = (ImageView) view.findViewById(R.id.advert_icon);
            coding = (TextView) view.findViewById(R.id.coding);
            typeSize = (TextView) view.findViewById(R.id.type_size);
            location = (TextView) view.findViewById(R.id.location);
            inspStatusIcon = (ImageView) view.findViewById(R.id.insp_status_icon);
        }
    }
}
