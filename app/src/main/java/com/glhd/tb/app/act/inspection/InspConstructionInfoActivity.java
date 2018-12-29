package com.glhd.tb.app.act.inspection;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.http.res.ResAdsConstruction;
import com.glhd.tb.app.http.res.ResConstructionNoti;
import com.glhd.tb.app.utils.MyImage;

/**
 * 上刊施工》未完成》反馈
 */
public class InspConstructionInfoActivity extends BaseActivity {

    protected TextView dateName;
    protected TextView stationName;
    protected TextView adsName;
    protected TextView adsType1;
    protected TextView adsType2;
    protected TextView adsNum;
    protected TextView adsDayNun;
    protected TextView actionType;
    protected TextView upAdsContent;
    protected ImageView upAdsIcon;
    protected RadioButton yes;
    protected RadioButton no;
    protected RadioGroup radiogroup;
    protected TextView startDate;
    protected ImageView uploadIcon;
    protected EditText remarks;
    protected LinearLayout container;
    ResAdsConstruction.DataBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_admin_construction_noti_info);
        bean = (ResAdsConstruction.DataBean) getIntent().getSerializableExtra("data");
        initView();
    }


    private void initView() {
        dateName = (TextView) findViewById(R.id.date_name);
        stationName = (TextView) findViewById(R.id.station_name);
        adsName = (TextView) findViewById(R.id.ads_name);
        adsType1 = (TextView) findViewById(R.id.ads_type1);
        adsType2 = (TextView) findViewById(R.id.ads_type2);
        adsNum = (TextView) findViewById(R.id.ads_num);
        adsDayNun = (TextView) findViewById(R.id.ads_day_nun);
        actionType = (TextView) findViewById(R.id.action_type);
        upAdsContent = (TextView) findViewById(R.id.up_ads_content);
        upAdsIcon = (ImageView) findViewById(R.id.up_ads_icon);
        yes = (RadioButton) findViewById(R.id.yes);
        no = (RadioButton) findViewById(R.id.no);
        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        startDate = (TextView) findViewById(R.id.start_date);
        uploadIcon = (ImageView) findViewById(R.id.upload_icon);
        remarks = (EditText) findViewById(R.id.remarks);
        container = (LinearLayout) findViewById(R.id.container);

        dateName.setText(bean.getContractStartDate() + "/" + bean.getContractEndDate() + bean.getContractTtile());
        stationName.setText(bean.getProperystation());
        adsName.setText(bean.getContractCompany());
        adsType1.setText(bean.getMediatype());
        adsType2.setText(bean.getFirstclassification() + "/" + bean.getSecondclassification());
        adsNum.setText(bean.getNumber() + "块");
        adsDayNun.setText(bean.getIntervalday() + "天");
        actionType.setText(bean.getPublType());
        upAdsContent.setText(bean.getRemark());

        remarks.setText(bean.getTruction().getPublRemarks());
        startDate.setText(bean.getTruction().getPublDate());
        if (bean.getTruction().getPublStatus().equals("0")) {
            yes.setChecked(true);
        } else {
            no.setChecked(true);
        }
        MyImage.load(this, bean.getImage(), upAdsIcon);
        MyImage.load(this, bean.getTruction().getPublImageUrl(), uploadIcon);
    }


}
