package com.glhd.tb.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.http.res.ResAdsConstruction;

import java.util.ArrayList;

public class ItemUpAdsCusNoYesAdapter extends BaseAdapter {

    private ArrayList<ResAdsConstruction.DataBean> objects = new ArrayList<ResAdsConstruction.DataBean>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemUpAdsCusNoYesAdapter(Context context, ArrayList<ResAdsConstruction.DataBean> objects) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public ResAdsConstruction.DataBean getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_up_ads_cus_no_yes, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((ResAdsConstruction.DataBean) getItem(position), (ViewHolder) convertView.getTag());
        ViewHolder viewHolder = null;
        return convertView;
    }

    private void initializeViews(ResAdsConstruction.DataBean b, ViewHolder holder) {
        holder.dateName.setText(b.getContractStartDate() + "/" + b.getContractEndDate() + b.getContractTtile());
        holder.stationName.setText(b.getProperystation());
        holder.adsName.setText(b.getContractCompany());
        holder.adsType1.setText(b.getMediatype());
        holder.adsType2.setText(b.getFirstclassification() + "/" + b.getSecondclassification());
        holder.adsNum.setText(b.getNumber()+"块");
        holder.adsDayNun.setText(b.getIntervalday()+"天");
        holder.actionType.setText(b.getPublType());
    }

    static class ViewHolder {
        protected TextView dateName;
        protected TextView stationName;
        protected TextView adsName;
        protected TextView adsType1;
        protected TextView adsType2;
        protected TextView adsNum;
        protected TextView adsDayNun;
        protected TextView actionType;

        ViewHolder(View rootView) {
            initView(rootView);
        }

        private void initView(View rootView) {
            dateName = (TextView) rootView.findViewById(R.id.date_name);
            stationName = (TextView) rootView.findViewById(R.id.station_name);
            adsName = (TextView) rootView.findViewById(R.id.ads_name);
            adsType1 = (TextView) rootView.findViewById(R.id.ads_type1);
            adsType2 = (TextView) rootView.findViewById(R.id.ads_type2);
            adsNum = (TextView) rootView.findViewById(R.id.ads_num);
            adsDayNun = (TextView) rootView.findViewById(R.id.ads_day_nun);
            actionType = (TextView) rootView.findViewById(R.id.action_type);
        }
    }
}
