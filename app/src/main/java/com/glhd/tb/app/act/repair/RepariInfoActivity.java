package com.glhd.tb.app.act.repair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ast365.library.listview.FullGridView;
import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.act.ImageActivity;
import com.glhd.tb.app.adapter.ItemInspSubmitMoreGridviewAdapter;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.BaseRes;
import com.glhd.tb.app.base.bean.BeanRepair;
import com.glhd.tb.app.event.EventRefreshRepairList;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.MyHttpManager;
import com.glhd.tb.app.http.res.ResUpload;
import com.glhd.tb.app.utils.MyImage;
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;

import me.nereo.multi_image_selector.Multi;

/**
 * 巡检反馈
 */
public class RepariInfoActivity extends BaseActivity implements View.OnClickListener {


    protected LinearLayout addIcon;
    protected EditText remarks;
    protected ImageView advertIcon1;
    protected ImageView advertIcon2;
    protected CheckBox avdentCheckbox;
    protected ImageView advertIcon;
    protected TextView coding;
    protected TextView stationName;
    protected TextView typeSize;
    protected TextView location;
    protected TextView time;
    protected FullGridView gridview1;
    protected FullGridView gridview2;
    protected LinearLayout container;
    protected Button submit;
    private ImageView uploadIcon;
    private Boolean edit;



    private ItemInspSubmitMoreGridviewAdapter iconsAdapter;
    private ItemInspSubmitMoreGridviewAdapter loadAdapter;
    private ArrayList<String> iconStrs = new ArrayList<>();
    private BeanRepair bean;
    private ProgressDialog dialog;
    public static boolean success = false;
    public static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_repair_submit_more);
        bean = (BeanRepair) getIntent().getSerializableExtra("bean");
        edit = getIntent().getBooleanExtra("edit", false);
        RepariInfoActivity.success = false;
        initView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_icon:
            case R.id.upload_icon:
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
                String path = Multi.cameraResult();
                if (path != null) {

                    String file = Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg";// 获取跟目录
                    file = MyImage.compressImage(path, file, 70);
                    log(file);

                    iconStrs.add(file);
                    iconsAdapter.notifyDataSetChanged();

                }
            }


        }
    }

    int uploadCount;
    ArrayList<String> uploadUrls = new ArrayList<>();

    public void uploadIcon() {
        dialog = new ProgressDialog(this);
        if (iconStrs.isEmpty()) {
//            inspFeedback(uploadUrls);
            MyToast.showMessage(this, "请添加图片");
            return;
        }

        dialog.setMessage("正在上传图片....");
        dialog.show();

        for (int i = 0; i < iconStrs.size(); i++) {
            final String url = iconStrs.get(i);
            API.upload(new File(url), new MyHttp.ResultCallback<ResUpload>() {
                @Override
                public void onSuccess(ResUpload res) {
                    uploadCount++;
                    if (res.getCode() == 0) {
                        File file = new File(url);
                        file.deleteOnExit();
                        uploadUrls.add(res.getData());
                    } else {

                    }
                    uploadFinish(uploadCount);
                }

                @Override
                public void onError(String message) {
                    uploadCount++;

                    uploadFinish(uploadCount);
                }
            }, new MyHttpManager.Param("type", "repair"));
        }


    }

    private void uploadFinish(int uploadCount) {
        if (uploadCount == iconStrs.size()) {
            dialog.dismiss();
            inspFeedback(uploadUrls);
        } else {

        }
    }

    private void initializeViews() {

        coding.setText("编码：" + bean.getCoding());
        stationName.setText(bean.getProperystation());
        typeSize.setText(bean.getWhSize());
        if (bean.getMarshalling() != null) {
            location.setText(bean.getMarshalling());
        } else {
            location.setText(bean.getLocation() + "  " + bean.getLocationdescribe());
        }

        time.setText("巡检时间：" + bean.getInspTime());
        MyImage.load(this, bean.getImage(), advertIcon);
        MyImage.load(this, bean.getMediaImage(), advertIcon1);
        MyImage.load(this, bean.getDwImage(), advertIcon1);
    }

    private void inspFeedback(ArrayList<String> uploadUrls) {
        dialog.setMessage("正在提交反馈....");


        String fileName = "";
        for (int i = 0; i < uploadUrls.size(); i++) {
            if (i == uploadUrls.size() - 1) {
                fileName = fileName + uploadUrls.get(i);
            } else {
                fileName = fileName + uploadUrls.get(i) + ",";
            }
        }

        API.repairFeedbackBatch(MySp.getUser(this).getAccountId(), bean.getId(), fileName, remarks.getText().toString().trim(), new MyHttp.ResultCallback<BaseRes>() {
            @Override
            public void onSuccess(BaseRes res) {
                if (res.getCode() == 0) {
                    MyToast.showMessage(RepariInfoActivity.this, "提交成功");
                    RepariInfoActivity.success = true;
                    finish();
                } else {
                    MyToast.showMessage(RepariInfoActivity.this, res.getMessage());
                }
                dialog.dismiss();

                EventBus.getDefault().post(new EventRefreshRepairList());
            }

            @Override
            public void onError(String message) {
                dialog.dismiss();
                MyToast.showMessage(RepariInfoActivity.this, "反馈失败，请稍后再试");
            }
        });
    }

    private void initView() {
        advertIcon1 = (ImageView) findViewById(R.id.advert_icon1);
        advertIcon2 = (ImageView) findViewById(R.id.advert_icon2);
        avdentCheckbox = (CheckBox) findViewById(R.id.avdent_checkbox);
        advertIcon = (ImageView) findViewById(R.id.advert_icon);
        coding = (TextView) findViewById(R.id.coding);
        stationName = (TextView) findViewById(R.id.station_name);
        typeSize = (TextView) findViewById(R.id.type_size);
        location = (TextView) findViewById(R.id.location);
        time = (TextView) findViewById(R.id.time);
        gridview1 = (FullGridView) findViewById(R.id.gridview1);
        gridview2 = (FullGridView) findViewById(R.id.gridview2);
        container = (LinearLayout) findViewById(R.id.container);
        uploadIcon = (ImageView) findViewById(R.id.upload_icon);
        addIcon = (LinearLayout) findViewById(R.id.add_icon);
        addIcon.setOnClickListener(this);
        uploadIcon.setOnClickListener(this);
        remarks = (EditText) findViewById(R.id.remarks);


        String[] images = bean.getTaskImage().split(",");
        final ArrayList<String> icons01 = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            icons01.add(images[i]);
        }
        initializeViews();
        iconsAdapter = new ItemInspSubmitMoreGridviewAdapter(this, icons01);
        iconsAdapter.setShowDelete(false);
        gridview1.setAdapter(iconsAdapter);

        gridview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(RepariInfoActivity.this,ImageActivity.class);
                intent.putExtra("bean",icons01);
                intent.putExtra("posi",i);
                startActivity(intent);
            }
        });

        loadAdapter = new ItemInspSubmitMoreGridviewAdapter(this, iconStrs);
        gridview2.setAdapter(loadAdapter);


        gridview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(RepariInfoActivity.this,ImageActivity.class);
                intent.putExtra("bean",iconStrs);
                intent.putExtra("posi",i);
                startActivity(intent);
            }
        });


        submit = (Button) findViewById(R.id.submit);
        if (edit == false) {
            submit.setVisibility(View.GONE);
            addIcon.setVisibility(View.GONE);
            remarks.setEnabled(false);
            remarks.setText(bean.getRemark());


             images = bean.getRepairImage().split(",");
            for (int i = 0; i < images.length; i++) {
                iconStrs.add(images[i]);
            }
            loadAdapter.setShowDelete(false);
            loadAdapter.notifyDataSetChanged();

        }

    }

    public void submitAction(View view) {
        if (iconStrs.isEmpty()) {
            MyToast.showMessage(this, "请添加照片");
            return;
        }
        uploadIcon();


    }

}
