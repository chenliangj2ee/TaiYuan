package com.glhd.tb.app.act.inspection;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.BaseRes;
import com.glhd.tb.app.base.bean.BeanUser;
import com.glhd.tb.app.event.EventConstructionSubmit;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.MyHttpManager;
import com.glhd.tb.app.http.res.ResAdsConstruction;
import com.glhd.tb.app.http.res.ResUpload;
import com.glhd.tb.app.utils.MyImage;
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;

import me.nereo.multi_image_selector.Multi;

/**
 * 上刊施工》未完成》反馈
 */
public class InspConstructionSubmitActivity extends BaseActivity implements View.OnClickListener {

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
    protected LinearLayout addIcon;
    protected EditText remarks;
    protected LinearLayout container;
    ResAdsConstruction.DataBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_insp_construction_submit);
        bean = (ResAdsConstruction.DataBean) getIntent().getSerializableExtra("data");
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_icon || view.getId() == R.id.upload_icon) {
            Multi.openCamera(this, 0);
        }
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
        uploadIcon.setOnClickListener(InspConstructionSubmitActivity.this);
        addIcon = (LinearLayout) findViewById(R.id.add_icon);
        addIcon.setOnClickListener(InspConstructionSubmitActivity.this);
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
        MyImage.load(this, bean.getImage(), upAdsIcon);
    }


    private String uploadUrl;
    private ArrayList<String> iconStrs = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            if (resultCode == Activity.RESULT_OK) {
                iconStrs.clear();
                iconStrs.add(Multi.cameraResult());
                if (iconStrs != null && iconStrs.size() > 0) {

                    String file = Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg";// 获取跟目录
                    file = MyImage.compressImage(iconStrs.get(0), file, 70);
                    log(file);
                    MyImage.loadFile(this, file, uploadIcon);
                    addIcon.setVisibility(View.GONE);

                    iconStrs.clear();
                    iconStrs.add(file);

                }
            }


        }
    }


    public void submitAction(View view) {
        if (yes.isChecked() == false && no.isChecked() == false) {
            MyToast.showMessage(this, "请选择施工结果");
            return;
        }
        if (startDateS == null) {
            MyToast.showMessage(this, "请选择日期");
            return;
        }
        if (startTimeS == null) {
            MyToast.showMessage(this, "请选择时间");
            return;
        }
        if (iconStrs.isEmpty()) {
            MyToast.showMessage(this, "请添加照片");
            return;
        }
        uploadIcon();


    }

    private ProgressDialog dialog;

    public void uploadIcon() {
        dialog = new ProgressDialog(this);

        dialog.setMessage("正在上传图片....");
        dialog.show();
        API.upload(new File(iconStrs.get(0)), new MyHttp.ResultCallback<ResUpload>() {
            @Override
            public void onSuccess(ResUpload res) {
                if (res.getCode() == 0) {
                    uploadUrl = res.getData();
                    inspFeedback(uploadUrl);
                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onError(String message) {
                dialog.dismiss();
            }
        }, new MyHttpManager.Param("type", "construction"));
    }


    private String startDateS;
    private String startTimeS;

    private void inspFeedback(String url) {
        dialog.setMessage("正在提交登记....");
        API.cunstructionSubmit(
                bean.getId(),
                MySp.getUser(this).getAccountId(),
                bean.getResourceid(),
                yes.isChecked() ? "0" : "1",
                remarks.getText().toString(),
                url,
                startDateS+startTimeS, new MyHttp.ResultCallback<BaseRes>() {
                    @Override
                    public void onSuccess(BaseRes res) {
                        dialog.dismiss();
                        if (res.getCode() == 0) {
                            MyToast.showMessage(InspConstructionSubmitActivity.this, "登记成功");
                            EventBus.getDefault().post(new EventConstructionSubmit());
                            finish();
                        } else {
                            MyToast.showMessage(InspConstructionSubmitActivity.this, res.getMessage());
                        }

                    }

                    @Override
                    public void onError(String message) {
                        dialog.dismiss();
                        MyToast.showMessage(InspConstructionSubmitActivity.this, "登记失败，请稍后再试");
                    }
                });
    }


    public void startDateAction(View view) {
        final TextView date = (TextView) view;
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("选择日期");

        final DatePicker datePicker = new DatePicker(this);

        datePicker.setCalendarViewShown(false);
        dialog.setView(datePicker);


        if (date.getText().toString().contains("-")) {
            String[] dateStrs = date.getText().toString().split("-");
            datePicker.init(Integer.parseInt(dateStrs[0]), Integer.parseInt(dateStrs[1]) - 1, Integer.parseInt(dateStrs[2]), null);
        }

        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startDateS = datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth();
                startDate.setText(startDateS);
            }
        });
        dialog.setNegativeButton("取消", null);
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startTimeAction(View view) {
        final TextView time = (TextView) view;
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("选择时间");

        final TimePicker timePicker = new TimePicker(this);

        dialog.setView(timePicker);


        if (time.getText().toString().contains(":")) {
            String[] dateStrs = time.getText().toString().split(":");
            timePicker.setHour(Integer.parseInt(dateStrs[0]));
            timePicker.setMinute(Integer.parseInt(dateStrs[1]));
        }

        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                time.setText(timePicker.getHour() + ":" + timePicker.getMinute());
                startTimeS = " " + timePicker.getHour() + ":" + timePicker.getMinute() + ":00";
            }
        });
        dialog.setNegativeButton("取消", null);
        dialog.show();
    }
}
