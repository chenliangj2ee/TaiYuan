package com.glhd.tb.app.act.inspection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.BaseRes;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.MyHttpManager;
import com.glhd.tb.app.http.res.ResUpload;
import com.glhd.tb.app.utils.MyImage;
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;

import java.io.File;
import java.util.ArrayList;

import me.nereo.multi_image_selector.Multi;

/**
 * 巡检反馈
 */
public class InspSubmitActivity extends BaseActivity implements View.OnClickListener {

    protected LinearLayout addIcon;
    protected EditText remarks;
    protected RadioButton yes;
    protected RadioButton no;
    protected TextView properyStation;
    private LinearLayout container;
    private ImageView advertIcon1;
    private ImageView advertIcon2;
    private TextView coding;
    private TextView location;
    private TextView sizeNum;
    private TextView advertTitle;
    private TextView time;
    private RadioGroup radiogroup;
    private ImageView uploadIcon;

    private ArrayList<String> iconStrs = new ArrayList<>();
    private BeanAdvert bean;
    private ProgressDialog dialog;
    public static boolean success = false;
    public static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_insp_submit);
        bean = (BeanAdvert) getIntent().getSerializableExtra("bean");
        id = bean.getId();
        InspSubmitActivity.success = false;
        initView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_icon:
            case R.id.upload_icon:
//                ArrayList<String> selectedIcon = new ArrayList<String>();//保存图片
//                Multi.openGallery(this, 1, selectedIcon, 0);
                Multi.openCamera(this, 0);
                break;
        }
    }


    private String uploadUrl;

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

    public void uploadIcon() {
        dialog = new ProgressDialog(this);
        if (iconStrs.isEmpty()) {
            inspFeedback("");
            return;
        }

        dialog.setMessage("正在上传图片....");
        dialog.show();
        API.upload(new File(iconStrs.get(0)), new MyHttp.ResultCallback<ResUpload>() {
            @Override
            public void onSuccess(ResUpload res) {
                if (res.getCode() == 0) {
                    uploadUrl = res.getUrl();
                    inspFeedback(uploadUrl);
                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onError(String message) {
                dialog.dismiss();
            }
        }, new MyHttpManager.Param("type", "inspection"));
    }

    private void inspFeedback(String url) {
        dialog.setMessage("正在提交反馈....");
        API.inspFeedback(MySp.getUser(this).getAccountId(), bean.getId(), url, remarks.getText().toString().trim(), yes.isChecked() ? "0" : "1", new MyHttp.ResultCallback<BaseRes>() {
            @Override
            public void onSuccess(BaseRes res) {
                if (res.getCode() == 0) {
                    MyToast.showMessage(InspSubmitActivity.this, "反馈成功");
                    InspSubmitActivity.success = true;
                    finish();
                } else {
                    MyToast.showMessage(InspSubmitActivity.this, res.getMessage());
                }
                dialog.dismiss();
            }

            @Override
            public void onError(String message) {
                dialog.dismiss();
                MyToast.showMessage(InspSubmitActivity.this, "反馈失败，请稍后再试");
            }
        });
    }

    private void initView() {
        advertIcon1 = (ImageView) findViewById(R.id.advert_icon1);
        advertIcon2 = (ImageView) findViewById(R.id.advert_icon2);
        coding = (TextView) findViewById(R.id.coding);
        sizeNum = (TextView) findViewById(R.id.size_num);
        location = (TextView) findViewById(R.id.location);
        advertTitle = (TextView) findViewById(R.id.advert_title);
        time = (TextView) findViewById(R.id.time);
        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        uploadIcon = (ImageView) findViewById(R.id.upload_icon);
        addIcon = (LinearLayout) findViewById(R.id.add_icon);
        addIcon.setOnClickListener(this);
        uploadIcon.setOnClickListener(this);
        remarks = (EditText) findViewById(R.id.remarks);
        container = (LinearLayout) findViewById(R.id.container);
        yes = (RadioButton) findViewById(R.id.yes);
        no = (RadioButton) findViewById(R.id.no);
        properyStation = (TextView) findViewById(R.id.propery_station);
        yes.setChecked(true);

        if (bean != null) {
            MyImage.load(this, bean.getDwImage(), advertIcon1);
            MyImage.load(this, bean.getMediaImage(), advertIcon2);
            coding.setText("编码：" + bean.getCoding());
            location.setText(bean.getLocation() + "——" + bean.getLocationdescribe());
            sizeNum.setText(bean.getTypeTitle() + "    " + bean.getWhSize() + "    " + bean.getNumber());
            time.setText(bean.getCurrentAdvertStartDate() + " ~ " + bean.getCurrentAdvertEndDate());
            properyStation.setText("所属车站："+bean.getProperystation());
            if (" ~ ".equals(time.getText().toString())) {
                time.setText("");
            }
        }
    }

    public void submitAction(View view) {
        if (yes.isChecked() == false && no.isChecked() == false) {
            MyToast.showMessage(this, "请选择巡检状态");
            return;
        }

        if (yes.isChecked() == false && no.isChecked() == false) {
            MyToast.showMessage(this, "请选择巡检状态");
            return;
        }
        if (iconStrs.isEmpty() && no.isChecked() == true) {
            MyToast.showMessage(this, "请添加照片");
            return;
        }
//        if ("".equals(remarks.getText().toString().trim())) {
//            MyToast.showMessage(this, "请输入巡检备注");
//            return;
//        }
        uploadIcon();


    }


}
