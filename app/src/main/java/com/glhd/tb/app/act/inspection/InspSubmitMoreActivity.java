package com.glhd.tb.app.act.inspection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.ItemInspSubmitMoreAdapter;
import com.glhd.tb.app.adapter.ItemInspSubmitMoreGridviewAdapter;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.BaseRes;
import com.glhd.tb.app.base.bean.BeanAdapterType;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.event.EventInspSubmitMore;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.MyHttpManager;
import com.glhd.tb.app.http.res.ResGetRepair;
import com.glhd.tb.app.http.res.ResUpload;
import com.glhd.tb.app.utils.MyImage;
import com.glhd.tb.app.utils.MyLog;
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

import me.nereo.multi_image_selector.Multi;

/**
 * 巡检反馈
 */
public class InspSubmitMoreActivity extends BaseActivity implements View.OnClickListener {
    protected TextView addressTextview;

    private TextView adsSize;
    private GridView typeGridView;
    private GridView gridView;
    protected LinearLayout addIcon;
    protected EditText remarks;
    private ImageView uploadIcon;
    protected TextView repairUser;
    protected TextView notiUser;
    private ItemInspSubmitMoreAdapter adsAdapter;
    private ItemInspSubmitMoreGridviewAdapter iconsAdapter;

    private ArrayList<String> iconStrs = new ArrayList<>();
    private ArrayList<BeanAdvert> beans;
    private ProgressDialog dialog;
    public static boolean success = false;
    public static String id;
    protected LinearLayout repairLayout;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_insp_submit_more);
        address = getIntent().getStringExtra("address");
        beans = (ArrayList<BeanAdvert>) getIntent().getSerializableExtra("beans");
        InspSubmitMoreActivity.success = false;
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
                    File f = new File(file);
                    f.deleteOnExit();
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
            }, new MyHttpManager.Param("type", "inspection"));
        }


    }

    private void uploadFinish(int uploadCount) {
        if (uploadCount == iconStrs.size()) {
            dialog.dismiss();
            dialog = null;
            inspFeedback(uploadUrls);
        } else {

        }
    }

    /*
     *
     * 设置维修人员
     * */
    String repairPersonnel = null;
    String names = null;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setRepairUser(ResGetRepair.DataBean.RepairPersonnelBean event) {

        repairPersonnel = null;
        names = null;
        for (int i = 0; i < event.users.size(); i++) {
            if (repairPersonnel == null) {
                repairPersonnel = event.users.get(i).getId();
                names = event.users.get(i).getName();
            } else {
                repairPersonnel = repairPersonnel + "," + event.users.get(i).getId();
                names = names + "," + event.users.get(i).getName();
            }
        }
        if (names != null)
            repairUser.setText("维修人：" + names);
    }

    /*
     *
     * 设置通知人员
     * */
    ArrayList<ResGetRepair.DataBean.ViewStaffBean> viewStaffBeans;
    String viewStaff;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setNotiUser(ArrayList<ResGetRepair.DataBean.ViewStaffBean> event) {
        viewStaffBeans = event;

        String mess = "";
        String ids = "";
        for (int i = 0; i < event.size(); i++) {
            if (i == event.size() - 1) {
                mess = mess + event.get(i).getName();
                ids = ids + event.get(i).getId();
            } else {
                mess = mess + event.get(i).getName() + ",";
                ids = ids + event.get(i).getId() + ",";
            }
        }
        viewStaff = ids;
        notiUser.setText("通知人：" + mess);
    }

    private void inspFeedback(ArrayList<String> uploadUrls) {
        if (dialog == null)
            dialog = new ProgressDialog(this);
        dialog.setMessage("正在提交反馈....");
        dialog.show();
        String ids = "";
        for (int i = 0; i < beans.size(); i++) {
            if (i == beans.size() - 1) {
                ids = ids + beans.get(i).getId();
            } else {
                ids = ids + beans.get(i).getId() + ",";
            }
        }
        String fileName = "";
        for (int i = 0; i < uploadUrls.size(); i++) {
            if (i == uploadUrls.size() - 1) {
                fileName = fileName + uploadUrls.get(i);
            } else {
                fileName = fileName + uploadUrls.get(i) + ",";
            }
        }

        API.inspFeedbackBatch(MySp.getUser(this).getAccountId(),
                ids,
                fileName,
                remarks.getText().toString().trim(),
                "0",
                repairPersonnel,
                viewStaff,
                new MyHttp.ResultCallback<BaseRes>() {
                    @Override
                    public void onSuccess(BaseRes res) {
                        if (res.getCode() == 0) {
                            MyToast.showMessage(InspSubmitMoreActivity.this, "反馈成功");
                            InspSubmitMoreActivity.success = true;
                            finish();
                        } else {
                            MyToast.showMessage(InspSubmitMoreActivity.this, res.getMessage());
                        }
                        dialog.dismiss();

                        ArrayList<String> idlist = new ArrayList<>();
                        for (BeanAdvert b : beans) {
                            idlist.add(b.getId());
                        }
                        EventBus.getDefault().post(new EventInspSubmitMore(idlist));
                    }

                    @Override
                    public void onError(String message) {
                        dialog.dismiss();
                        MyToast.showMessage(InspSubmitMoreActivity.this, "反馈失败，请稍后再试");
                    }
                });
    }

    private void initView() {
        repairUser = (TextView) findViewById(R.id.repair_user);
        notiUser = (TextView) findViewById(R.id.noti_user);
        adsSize = findViewById(R.id.ads_size);
        gridView = findViewById(R.id.gridview);
        uploadIcon = (ImageView) findViewById(R.id.upload_icon);
        addIcon = (LinearLayout) findViewById(R.id.add_icon);
        addIcon.setOnClickListener(this);
        uploadIcon.setOnClickListener(this);
        remarks = (EditText) findViewById(R.id.remarks);
        repairLayout = (LinearLayout) findViewById(R.id.repair_layout);
        typeGridView = findViewById(R.id.type_gridview);
        if (beans != null) {
            adsAdapter = new ItemInspSubmitMoreAdapter(this, initData(), typeGridView);
            typeGridView.setAdapter(adsAdapter);
            adsSize.setText("共计" + beans.size() + "个媒体");

        }

        iconsAdapter = new ItemInspSubmitMoreGridviewAdapter(this, iconStrs);
        gridView.setAdapter(iconsAdapter);
        addressTextview = (TextView) findViewById(R.id.address_textview);
        addressTextview.setText(address);
    }

    public void submitAction(View view) {
        if (iconStrs.isEmpty()) {
//            MyToast.showMessage(this, "请添加照片");
            inspFeedback(uploadUrls);
            return;
        } else {
            uploadIcon();
        }


    }

    public void SelectNotiUserAction(View view) {
        startActivity(new Intent(this, NotiUserActivity.class));
    }

    public void SelectRepairUserAction(View view) {
        startActivity(new Intent(this, RepairUserActivity.class));
    }


    public ArrayList<BeanAdapterType> initData() {

        ArrayList<BeanAdapterType> data = new ArrayList<>();

        for (int i = 0; i < beans.size(); i++) {
            boolean has = false;
            for (int j = 0; j < data.size(); j++) {
                if (beans.get(i).getTypeTitle().equals(data.get(j).getType())) {
                    has = true;
                    data.get(j).setNum(data.get(j).getNum() + 1);
                    continue;
                }
            }
            if (has == false) {
                BeanAdapterType bat = new BeanAdapterType();
                bat.setType(beans.get(i).getTypeTitle());
                data.add(bat);
            }
        }
        return data;
    }


}
