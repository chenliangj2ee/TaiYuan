package com.glhd.tb.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.http.res.ResConstructionNoti;
import com.glhd.tb.app.utils.MyImage;

import java.util.ArrayList;

public class ItemAdminUpAdsAdapter extends BaseAdapter {

    private ArrayList<ResConstructionNoti.DataBean> objects = new ArrayList<>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemAdminUpAdsAdapter(Context context, ArrayList<ResConstructionNoti.DataBean> data) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects=data;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public ResConstructionNoti.DataBean getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_admin_up_ads_noti, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((ResConstructionNoti.DataBean)getItem(position), (ViewHolder) convertView.getTag());
        ViewHolder viewHolder = null;
        return convertView;
    }

    private void initializeViews(ResConstructionNoti.DataBean object, ViewHolder h) {
        MyImage.load(context,object.getImage(),h.advertIcon);
        h.coding.setText("0".equals(object.getConstrucType())?"上刊编码":"媒体编码"+object.getMediaid());
        h.stationName.setText(object.getProperystation());
        h.typeSize.setText(object.getMediaid()+" "+object.getSpecification()+" "+object.getFirstclassification());
        h.adsNum.setText(object.getNumber()+"块");
        h.contructCode.setText(object.getContractCode());
        h.contructName.setText(object.getContractName());
        h.adsName.setText(object.getContractCompany());
        h.adsDate.setText(object.getContractStartDate()+"/"+object.getContractEndDate());
        h.adsDay.setText(object.getIntervalday()+"天");

    }

    static

    class ViewHolder {
        protected ImageView advertIcon;
        protected TextView coding;
        protected TextView stationName;
        protected TextView typeSize;
        protected TextView adsNum;
        protected TextView contructCode;
        protected TextView contructName;
        protected TextView adsName;
        protected TextView adsDate;
        protected TextView adsDay;
        protected ImageView inspStatusIcon;

        ViewHolder(View rootView) {
            initView(rootView);
        }

        private void initView(View rootView) {
            advertIcon = (ImageView) rootView.findViewById(R.id.advert_icon);
            coding = (TextView) rootView.findViewById(R.id.coding);
            stationName = (TextView) rootView.findViewById(R.id.station_name);
            typeSize = (TextView) rootView.findViewById(R.id.type_size);
            adsNum = (TextView) rootView.findViewById(R.id.ads_num);
            contructCode = (TextView) rootView.findViewById(R.id.contruct_code);
            contructName = (TextView) rootView.findViewById(R.id.contruct_name);
            adsName = (TextView) rootView.findViewById(R.id.ads_name);
            adsDate = (TextView) rootView.findViewById(R.id.ads_date);
            adsDay = (TextView) rootView.findViewById(R.id.ads_day);
            inspStatusIcon = (ImageView) rootView.findViewById(R.id.insp_status_icon);
        }
    }
}
