package com.glhd.tb.app.act.inspection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ast365.library.listview.FullGridView;
import com.glhd.tb.app.R;
import com.glhd.tb.app.act.ImageActivity;
import com.glhd.tb.app.adapter.ItemInspSubmitMoreGridviewAdapter;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.utils.MyImage;

import java.util.ArrayList;

public class InspInfoActivity extends BaseActivity {

    protected ImageView inspStatusIcon;
    private LinearLayout container;
    private ImageView advertIcon;
    private TextView coding;
    private TextView typeSize;
    private TextView inspCycle;
    private TextView location;
    private TextView time;
    private TextView inspTime;
    private ImageView inspIcon;
    private TextView remarks;
    private BeanAdvert bean;
    private FullGridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_insp_info);
        bean = (BeanAdvert) getIntent().getSerializableExtra("bean");
        initView();
    }

    private ItemInspSubmitMoreGridviewAdapter adapter;
    private ArrayList<String> icons = new ArrayList<>();
    private void initView() {
        advertIcon = (ImageView) findViewById(R.id.advert_icon);
        coding = (TextView) findViewById(R.id.coding);
        typeSize = (TextView) findViewById(R.id.type_size);
        inspCycle = (TextView) findViewById(R.id.insp_cycle);
        location = (TextView) findViewById(R.id.location);
        time = (TextView) findViewById(R.id.time);
        inspTime = (TextView) findViewById(R.id.insp_time);
        inspStatusIcon = (ImageView) findViewById(R.id.insp_status_icon);
        inspIcon = (ImageView) findViewById(R.id.insp_icon);
        remarks = (TextView) findViewById(R.id.remarks);
        container = (LinearLayout) findViewById(R.id.container);
        gridview=findViewById(R.id.gridview);


        if (bean != null) {
            MyImage.load(this, bean.getImage(), advertIcon);
            coding.setText("编码：" + bean.getCoding());
            typeSize.setText(bean.getTypeTitle() + "  " + bean.getWhSize() + "   " + bean.getNumber());
            location.setText(bean.getLocation());
            time.setText(bean.getCurrentAdvertStartDate() + " ~ " + bean.getCurrentAdvertEndDate());
            if(" ~ ".equals(time.getText().toString())){
                time.setText("");
            }


            if (bean.getInsp() != null) {
                MyImage.load(this, bean.getInsp().getInspImageUrl(), inspIcon);
                remarks.setText(bean.getInsp().getInspRemarks());
                inspTime.setText("巡检时间:" + bean.getInsp().getInspDate());
                if ("0".equals(bean.getInspCycle()) || "日检".equals(bean.getInspCycle())) {
                    inspCycle.setText("日检 ");
                    inspCycle.setBackgroundResource(R.drawable.rijian_bg);
                } else if ("1".equals(bean.getInspCycle()) || "周检".equals(bean.getInspCycle())) {
                    inspCycle.setText("周检 ");
                    inspCycle.setBackgroundResource(R.drawable.zhoujian_bg);
                } else if ("2".equals(bean.getInspCycle()) || "月检".equals(bean.getInspCycle())) {
                    inspCycle.setText("月检 ");
                    inspCycle.setBackgroundResource(R.drawable.yuejian_bg);
                }

                if ("0".equals(bean.getInsp().getInspStatus())) {
                    inspStatusIcon.setImageResource(R.drawable.zhengchang);
                }
                if ("1".equals(bean.getInsp().getInspStatus())) {
                    inspStatusIcon.setImageResource(R.drawable.yichang);
                }
            }
            adapter = new ItemInspSubmitMoreGridviewAdapter(this, icons);
            adapter.setDefaultIcon(bean.getImage());
            gridview.setAdapter(adapter);
            if (bean.getInsp()!=null) {
                String[] images = bean.getInsp().getInspImageUrl().split(",");
                if (images != null && images.length > 0) {
                    for (int i = 0; i < images.length; i++) {
                        icons.add(images[i]);
                    }
                    adapter.setShowDelete(false);
                    adapter.notifyDataSetChanged();
                }


                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(InspInfoActivity.this, ImageActivity.class);
                        intent.putExtra("bean", icons);
                        intent.putExtra("posi", i);
                        intent.putExtra("defaultUrl", bean.getImage());
                        startActivity(intent);
                    }
                });
            }

        }
    }
}
