package com.glhd.tb.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.utils.MyImage;

public class ItemCusUpAdsAdapter extends BaseAdapter {

    private ArrayList<BeanAdvert> objects = new ArrayList<BeanAdvert>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemCusUpAdsAdapter(Context context,ArrayList<BeanAdvert> data) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects=data;
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
            convertView = layoutInflater.inflate(R.layout.item_cus_up_ads, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((BeanAdvert)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(BeanAdvert object, ViewHolder holder) {
        MyImage.load(context,object.getImage(),holder.advertIcon);
        holder.coding.setText("编码："+object.getCoding());
        holder.location.setText(object.getLocation());
        holder.typeSize.setText(object.getTypeTitle()+object.getWhSize());
        if( object.getUpAds()!=null){
            holder.upDate.setText("上刊时间："+ object.getUpAds().getUpAdvertDate());
            holder.adsType.setText(object.getUpAds().getUpAdvertTypeTitle());
            holder.company.setText(object.getUpAds().getCurrentAdvertTitle());
            holder.startEndDate.setText(object.getUpAds().getCurrentAdvertStartDate()+"~"+object.getUpAds().getCurrentAdvertEndDate());
        }

    }

    protected class ViewHolder {
        private ImageView advertIcon;
    private TextView coding;
    private TextView upDate;
    private TextView adsType;
    private TextView location;
    private TextView typeSize;
    private TextView company;
    private TextView startEndDate;
    private ImageView inspStatusIcon;

        public ViewHolder(View view) {
            advertIcon = (ImageView) view.findViewById(R.id.advert_icon);
            coding = (TextView) view.findViewById(R.id.coding);
            upDate = (TextView) view.findViewById(R.id.up_date);
            adsType = (TextView) view.findViewById(R.id.ads_type);
            location = (TextView) view.findViewById(R.id.location);
            typeSize = (TextView) view.findViewById(R.id.type_size);
            company = (TextView) view.findViewById(R.id.company);
            startEndDate = (TextView) view.findViewById(R.id.start_end_date);
            inspStatusIcon = (ImageView) view.findViewById(R.id.insp_status_icon);
        }
    }
}
