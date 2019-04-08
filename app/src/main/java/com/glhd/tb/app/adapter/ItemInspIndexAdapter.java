package com.glhd.tb.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.act.inspection.InspIndexActivity;
import com.glhd.tb.app.act.inspection.InspInfoActivity;
import com.glhd.tb.app.act.inspection.InspSubmitActivity;
import com.glhd.tb.app.act.inspection.InspSubmitMoreActivity;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.utils.MyImage;
import com.glhd.tb.app.utils.MyToast;

public class ItemInspIndexAdapter extends BaseAdapter {

    private ArrayList<BeanAdvert> objects = new ArrayList<BeanAdvert>();

    private Context context;
    private LayoutInflater layoutInflater;

    private boolean checkBoxIsShow=false;

    public ItemInspIndexAdapter(Context context, ArrayList<BeanAdvert> objects) {
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
            convertView = layoutInflater.inflate(R.layout.item_insp_index, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((BeanAdvert) getItem(position), (ViewHolder) convertView.getTag(), convertView);
        ViewHolder viewHolder = null;
        return convertView;
    }

    private void initializeViews(final BeanAdvert b,final ViewHolder h, View convertView) {
        MyImage.load(context, b.getImage(), h.advertIcon);
        h.coding.setText("编码:" + b.getCoding());
        h.location.setText(b.getLocationdescribe());
        h.stationName.setText(b.getProperystation());


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
            h.time.setText("待巡检时间 " + b.getNextTimeLatestDate());
            h.inspStatusIcon.setVisibility(View.GONE);
        }
        /*
        * 设置批量操作时复选框的值
        * */
        if (checkBoxIsShow) {
            h.avdent_checkbox.setVisibility(View.VISIBLE);
        } else {
            h.avdent_checkbox.setVisibility(View.GONE);
        }

        if(b.isChecked()){
            h.avdent_checkbox.setChecked(true);
        }else{
            h.avdent_checkbox.setChecked(false);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkBoxIsShow){
                    if (b.getInsp() != null) {
                        Intent intent = new Intent(context, InspInfoActivity.class);
                        intent.putExtra("bean", b);
                        context.startActivity(intent);
                    } else {
//                        Intent intent = new Intent(context, InspSubmitActivity.class);
//                        intent.putExtra("bean", b);
//                        context.startActivity(intent);

                        ArrayList<BeanAdvert> selectAds=new ArrayList<>();
                        selectAds.add(b);
                        Intent intent=new Intent(context,InspSubmitMoreActivity.class);
                        intent.putExtra("beans",selectAds);
                        context.startActivity(intent);


                    }
                }else{

                    if (b.isChecked()) {
                        b.setChecked(false);
                        h.avdent_checkbox.setChecked(false);
                    } else {
                        b.setChecked(true);
                        h.avdent_checkbox.setChecked(true);
                    }
                }
            }
        });

        /*
        *
        * 点击复选框时 设置对应的对象 选中状态
        * */
//        h.avdent_checkbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (b.isChecked()) {
//                    b.setChecked(false);
//                } else {
//                    b.setChecked(true);
//                }
//            }
//        });


    }

    static class ViewHolder {
        protected ImageView advertIcon;
        protected TextView coding;
        protected TextView stationName;
        protected TextView typeSize;
        protected TextView inspCycle;
        protected TextView location;
        protected TextView time;
        protected ImageView inspStatusIcon;
        protected CheckBox avdent_checkbox;


        ViewHolder(View rootView) {
            initView(rootView);
        }

        private void initView(View rootView) {
            avdent_checkbox=(CheckBox)rootView.findViewById(R.id.avdent_checkbox);
            advertIcon = (ImageView) rootView.findViewById(R.id.advert_icon);
            coding = (TextView) rootView.findViewById(R.id.coding);
            stationName = (TextView) rootView.findViewById(R.id.station_name);
            typeSize = (TextView) rootView.findViewById(R.id.type_size);
            inspCycle = (TextView) rootView.findViewById(R.id.insp_cycle);
            location = (TextView) rootView.findViewById(R.id.location);
            time = (TextView) rootView.findViewById(R.id.time);
            inspStatusIcon = (ImageView) rootView.findViewById(R.id.insp_status_icon);
        }
    }

    public ArrayList<BeanAdvert> getObjects() {
        return objects;
    }

    public void setCheckBoxIsShow(boolean checkBoxIsShow) {
        this.checkBoxIsShow = checkBoxIsShow;
    }

    public boolean isCheckBoxIsShow() {
        return checkBoxIsShow;
    }
}
