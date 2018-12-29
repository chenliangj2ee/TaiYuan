package com.glhd.tb.app.act.customer;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.utils.MyImage;

public class CusInspInfoActivity extends BaseActivity {

    private ImageView advertIcon;
    private TextView coding;
    private TextView typeSize;
    private TextView location;
    private ImageView inspStatusIcon;
    private ImageView inspIcon;
    private TextView remarks;

    private BeanAdvert bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_insp_info);

        bean = (BeanAdvert) getIntent().getSerializableExtra("data");

        advertIcon = (ImageView) findViewById(R.id.advert_icon);
        coding = (TextView) findViewById(R.id.coding);
        typeSize = (TextView) findViewById(R.id.type_size);
        location = (TextView) findViewById(R.id.location);
        inspStatusIcon = (ImageView) findViewById(R.id.insp_status_icon);
        inspIcon = (ImageView) findViewById(R.id.insp_icon);
        remarks = (TextView) findViewById(R.id.remarks);

        if (bean != null) {
            MyImage.load(this, bean.getImage(), advertIcon);
            coding.setText("编码：" + bean.getCoding());
            typeSize.setText(bean.getTypeTitle() + "  " + bean.getWhSize() + "  " + bean.getNumber());
            location.setText(bean.getLocation());


            if (bean.getInsp() != null) {
                MyImage.load(this, bean.getInsp().getInspImageUrl(), inspIcon);
                if ("0".equals(bean.getInsp().getInspStatus())) {
                    inspStatusIcon.setImageResource(R.drawable.zhengchang);
                } else {
                    inspStatusIcon.setImageResource(R.drawable.yichang);
                }
            } else {
                inspStatusIcon.setImageResource(R.drawable.zhengchang);
            }
        }
    }

}
