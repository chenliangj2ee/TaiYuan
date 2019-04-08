package com.glhd.tb.app.act.inspection;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.utils.MyImage;

import java.util.ArrayList;

/**
 * 巡检反馈
 */
public class InspSubmitInfoActivity extends BaseActivity {

    protected TextView properyStation;
    protected TextView repairUser;
    protected TextView notiUser;
    protected LinearLayout repairLayout;
    private ImageView advertIcon1;
    private ImageView advertIcon2;
    private TextView coding;
    private TextView location;
    private TextView sizeNum;
    private TextView advertTitle;
    private TextView time;

    private ArrayList<String> iconStrs = new ArrayList<>();
    private BeanAdvert bean;
    private ProgressDialog dialog;
    public static boolean success = false;
    public static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_insp_submit_info);
        bean = (BeanAdvert) getIntent().getSerializableExtra("bean");
        id = bean.getId();
        InspSubmitInfoActivity.success = false;
        initView();
    }


    private void initView() {
        advertIcon1 = (ImageView) findViewById(R.id.advert_icon1);
        advertIcon2 = (ImageView) findViewById(R.id.advert_icon2);
        coding = (TextView) findViewById(R.id.coding);
        sizeNum = (TextView) findViewById(R.id.size_num);
        location = (TextView) findViewById(R.id.location);
        advertTitle = (TextView) findViewById(R.id.advert_title);
        time = (TextView) findViewById(R.id.time);
        properyStation = (TextView) findViewById(R.id.propery_station);
        repairUser = (TextView) findViewById(R.id.repair_user);
        notiUser = (TextView) findViewById(R.id.noti_user);
        repairLayout = (LinearLayout) findViewById(R.id.repair_layout);
        if (bean != null) {
            MyImage.load(this, bean.getDwImage(), advertIcon1);
            MyImage.load(this, bean.getMediaImage(), advertIcon2);
            coding.setText("编码：" + bean.getCoding());
            location.setText(bean.getLocation() + "  " + bean.getLocationdescribe());
            sizeNum.setText(bean.getTypeTitle() + "    " + bean.getWhSize() + "    " + bean.getNumber());
            time.setText("带巡检时间："+bean.getNextTimeLatestDate());
            properyStation.setText("所属车站：" + bean.getProperystation());
            if (" ~ ".equals(time.getText().toString())) {
                time.setText("");
            }
        }

    }


}
