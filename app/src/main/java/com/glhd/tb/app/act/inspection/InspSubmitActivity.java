package com.glhd.tb.app.act.inspection;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.danikula.videocache.HttpProxyCacheServer;
import com.glhd.tb.app.API;
import com.glhd.tb.app.MyApp;
import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.ItemInspSubmitMoreGridviewAdapter;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.BaseRes;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.camera.CameraActivity;
import com.glhd.tb.app.camera.activity.CaptureImageVideoActivity;
import com.glhd.tb.app.camera.activity.VideoCameraActivity;
import com.glhd.tb.app.camera.jcamera.util.CheckPermission;
import com.glhd.tb.app.camera.jcamera.util.FileUtil;
import com.glhd.tb.app.event.EventRefreshInspList;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.MyHttpManager;
import com.glhd.tb.app.http.res.ResGetRepair;
import com.glhd.tb.app.http.res.ResUpload;
import com.glhd.tb.app.utils.MyImage;
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;
import com.glhd.tb.app.view.TreeViewLayout;
import com.glhd.tb.app.view.banner.Banner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.Multi;

/**
 * 巡检反馈 车站
 */
public class InspSubmitActivity extends BaseActivity implements View.OnClickListener {

    protected LinearLayout addIcon;
    protected EditText remarks;
    protected RadioButton yes;
    protected RadioButton no;
    protected TextView properyStation;
    protected TextView repairUser;
    protected TextView notiUser;
    protected LinearLayout repairLayout;
    private LinearLayout container;
    private ImageView advertIcon1;
    //    private ImageView advertIcon2;
    private Banner advert_banner;

    private TextView coding;
    private TextView location;
    private TextView sizeNum;
    private TextView advertTitle;
    private TextView time;
    private RadioGroup radiogroup;
    private ImageView uploadIcon;
    private GridView gridView;
    private ArrayList<String> iconStrs = new ArrayList<>();
    private ItemInspSubmitMoreGridviewAdapter iconsAdapter;

    private BeanAdvert bean;
    private ProgressDialog dialog;
    public static boolean success = false;
    public static String id;

    private List<String> list;


    private static final int REQUEST_VIDEO_PERMISSIONS = 1;
    private static final String TAG = "InspSubmitActivity";
    //视频权限
    private static final String[] VIDEO_PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.WRITE_EXTERNAL_STORAGE};


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
//                Multi.openCamera(this, 0);
//                Multi.openVideo(this, 0);
                break;
        }
    }


    private String uploadUrl;

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 0) {
//
//            if (resultCode == Activity.RESULT_OK) {
//                String path = Multi.cameraResult();
//                if (path != null) {
//
//                    String file = Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg";// 获取跟目录
//                    file = MyImage.compressImage(path, file, 70);
////                    log(file);
////
////                    iconStrs.add(file);
////                    iconsAdapter.notifyDataSetChanged();
//
//                }
//            }
//
//
//        }
//    }

    int uploadCount;
    ArrayList<String> uploadUrls = new ArrayList<>();

    public void uploadIcon(final View view) {
        dialog = new ProgressDialog(this);
        if (iconStrs.isEmpty()) {
//            inspFeedback(uploadUrls);
            MyToast.showMessage(this, "请添加图片");
            view.setEnabled(true);
            return;
        }

        dialog.setMessage("正在上传图片....");
        //未上传成功 不可操作
        dialog.setCanceledOnTouchOutside(false);
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
                    uploadFinish(uploadCount, view);
                }

                @Override
                public void onError(String message) {
                    uploadCount++;
                    uploadFinish(uploadCount, view);
                }
            }, new MyHttpManager.Param("type", "inspection"));
        }

    }


    private void uploadFinish(int uploadCount, View view) {
        if (uploadCount == iconStrs.size()) {
            dialog.dismiss();
            inspFeedback(uploadUrls);
            view.setEnabled(true);
        } else {

        }
    }

    private void inspFeedback(ArrayList<String> uploadUrls) {
        if (dialog == null) dialog = new ProgressDialog(this);
        dialog.setMessage("正在提交反馈....");

        String fileName = "";
        for (int i = 0; i < uploadUrls.size(); i++) {
            if (i == uploadUrls.size() - 1) {
                fileName = fileName + uploadUrls.get(i);
            } else {
                fileName = fileName + uploadUrls.get(i) + ",";
            }
        }

        API.inspFeedback(MySp.getUser(this).getAccountId(), bean.getId(), fileName, remarks.getText().toString().trim(), yes.isChecked() ? "0" : "1", repairPersonnel, viewStaff, new MyHttp.ResultCallback<BaseRes>() {
            @Override
            public void onSuccess(BaseRes res) {
                if (res.getCode() == 0) {
                    MyToast.showMessage(InspSubmitActivity.this, "反馈成功");
                    InspSubmitActivity.success = true;
                    EventBus.getDefault().post(new EventRefreshInspList());
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
        gridView = findViewById(R.id.gridview);
        advertIcon1 = (ImageView) findViewById(R.id.advert_icon1);
//        advertIcon2 = (ImageView) findViewById(R.id.advert_icon2);
        advert_banner = (Banner) findViewById(R.id.advert_banner);
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
        repairUser = (TextView) findViewById(R.id.repair_user);
        notiUser = (TextView) findViewById(R.id.noti_user);
        repairLayout = (LinearLayout) findViewById(R.id.repair_layout);
        if (bean != null) {
            MyImage.load(this, bean.getDwImage(), advertIcon1);
            //todo banner
//            MyImage.load(this, bean.getMediaImage(), advertIcon2);
            Log.i("mybean", bean.getMediaImage());
            if (bean.getMediaImage() != null) {
                advert_banner.setVisibility(View.VISIBLE);
                initData(bean);
                initBannerView();
            }

            coding.setText(" " + bean.getCoding());
            location.setText(bean.getLocation() + "   " + bean.getLocationdescribe());//到达层
//            sizeNum.setText(bean.getTypeTitle() + "    " + bean.getWhSize() + "    " + bean.getNumber());
            sizeNum.setText(bean.getTypeTitle() + "    " + bean.getWhSize());
            time.setText(bean.getCurrentAdvertStartDate() + " ~ " + bean.getCurrentAdvertEndDate());
            properyStation.setText("  " + bean.getProperystation());//车站
            if (" ~ ".equals(time.getText().toString())) {
                time.setText("");
            }
        }
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                repairLayout.setVisibility(no.isChecked() ? View.VISIBLE : View.GONE);
            }
        });
        no.setChecked(true);

        iconsAdapter = new ItemInspSubmitMoreGridviewAdapter(this, iconStrs);
        gridView.setAdapter(iconsAdapter);
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

        if ("".equals(repairPersonnel) && no.isChecked()) {
            MyToast.showMessage(this, "请选择维修人");
            return;
        }
        if ("".equals(viewStaff) && no.isChecked()) {
            MyToast.showMessage(this, "请选择通知人");
            return;
        }

        view.setEnabled(false);
        uploadIcon(view);


    }


    public void SelectNotiUserAction(View view) {
        startActivity(new Intent(this, NotiUserActivity.class));
    }

    public void SelectRepairUserAction(View view) {
        startActivity(new Intent(this, RepairUserActivity.class));
    }


    /*
     *
     * 设置维修人员
     * */ String repairPersonnel = null;
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

        repairUser.setText("维修人：" + names);
    }

    /*
     *
     * 设置通知人员
     * */ ArrayList<ResGetRepair.DataBean.ViewStaffBean> viewStaffBeans;
    String viewStaff = "";

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

    private void initData(BeanAdvert bean) {
        HttpProxyCacheServer proxy = MyApp.getProxy(getApplicationContext());
        String proxyUrl = proxy.getProxyUrl("http://www.w3school.com.cn/example/html5/mov_bbb.mp4");
        String proxyUrl2 = proxy.getProxyUrl("http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4");
        //添加视频
//        for (int i = 0; i < bean.getMediaImage(); i++) {
//
//        }
        list = new ArrayList<>();
        list.add(bean.getMediaImage());
//        list.add(proxyUrl);
//        list.add(proxyUrl2);
//        list.add("http://img2.imgtn.bdimg.com/it/u=3817131034,1038857558&fm=27&gp=0.jpg");
//        list.add("http://img1.imgtn.bdimg.com/it/u=4194723123,4160931506&fm=200&gp=0.jpg");
//        list.add("http://img5.imgtn.bdimg.com/it/u=1812408136,1922560783&fm=27&gp=0.jpg");
    }

    private void initBannerView() {
        advert_banner.setDataList(list);
        advert_banner.setImgDelyed(5000);
        advert_banner.startBanner();
//        advert_banner.startAutoPlay();
    }


    @Override
    public void onDestroy() {
        advert_banner.destroy();

        RepairUserActivity.repairPersonnels = null;
        TreeViewLayout.cache = null;
        super.onDestroy();
    }

    public void startCapture(View view) {
        if (!hasPermissionsGranted(VIDEO_PERMISSIONS) && CheckPermission.getRecordState() != CheckPermission.STATE_SUCCESS) {
            requestVideoPermissions();
            return;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            Intent intent = new Intent(this, VideoCameraActivity.class);
            startActivityForResult(intent, 1);
        } else {
            Intent intent = new Intent();
            intent.setClass(this, CaptureImageVideoActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            boolean isPhoto = data.getBooleanExtra("isPhoto", false);
            if (!isPhoto) {

                String videoPath = data.getStringExtra("videoPath");

                long totalTime = data.getLongExtra("totalTime", 0);
                int videoWidth = data.getIntExtra("videoWidth", 0);
                int videoHeight = data.getIntExtra("videoHeight", 0);
                if (FileUtil.isNotEmpty(videoPath)) {
                    //TODO 获取视频
//                    Toast.makeText(this, "视频文件：" + videoPath, Toast.LENGTH_LONG).show();
                    log(videoPath);
                    iconStrs.add(videoPath);
                    iconsAdapter.notifyDataSetChanged();
                }
            } else {
                String imageFile = data.getStringExtra("imagefile");
                String type = data.getStringExtra("type");
//                 file = Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg";// 获取跟目录
//                String  file = MyImage.compressImage(path, imageUrl, 70);

                //TODO 获取到图片
                log(imageFile);
                iconStrs.add(imageFile);
                iconsAdapter.notifyDataSetChanged();

            }
        }
    }

    /********************** 权限相关********************************/

    /**
     * Gets whether you should show UI with rationale for requesting permissions.
     *
     * @param permissions The permissions your app wants to request.
     * @return Whether you can show permission rationale UI.
     */
    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Requests permissions needed for recording video.
     */
    private void requestVideoPermissions() {
        if (shouldShowRequestPermissionRationale(VIDEO_PERMISSIONS)) {
            Toast.makeText(this, "您没有相机和录音权限，请到系统设置里授权", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            ActivityCompat.requestPermissions(this, VIDEO_PERMISSIONS, REQUEST_VIDEO_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult");
        if (requestCode == REQUEST_VIDEO_PERMISSIONS) {
            if (grantResults.length == VIDEO_PERMISSIONS.length) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "您没有相机和录音权限，请到系统设置里授权", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            } else {
                Toast.makeText(this, "您没有相机和录音权限，请到系统设置里授权", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean hasPermissionsGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


}
