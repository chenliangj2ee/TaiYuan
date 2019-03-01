package me.nereo.multi_image_selector;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import me.nereo.multi_image_selector.utils.FileUtils;

/**
 * Created by chenliangj2ee on 2016/6/26.
 */
public class Multi {

    /**
     * 选择相机
     */
    private static File mTmpFile;

    /**
     * @param act
     * @param maxSelectNum:最大可选择图片数量
     * @param selectedPath:默认选择
     * @param requestCode
     */
    public static void openGallery(Activity act, int maxSelectNum, ArrayList<String> selectedPath, int requestCode) {
        int selectedMode;
        if (maxSelectNum <= 1) {//单选
            selectedMode = MultiImageSelectorActivity.MODE_SINGLE;
        } else {//多选
            selectedMode = MultiImageSelectorActivity.MODE_MULTI;
        }

        Intent intent = new Intent(act, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxSelectNum);
        // 选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, selectedMode);
        // 默认选择
        if (selectedPath != null && selectedPath.size() > 0) {
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, selectedPath);
        }
        act.startActivityForResult(intent, requestCode);
    }

    /**
     * @param act
     * @param maxSelectNum:最大可选择图片数量
     * @param selectedPath:默认选择
     * @param showCamera:是否显示拍摄图片
     * @param requestCode
     */
    public static void openGallery(Activity act, int maxSelectNum, ArrayList<String> selectedPath, boolean showCamera, int requestCode) {
        int selectedMode;
        if (maxSelectNum <= 1) {//单选
            selectedMode = MultiImageSelectorActivity.MODE_SINGLE;
        } else {//多选
            selectedMode = MultiImageSelectorActivity.MODE_MULTI;
        }

        Intent intent = new Intent(act, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, showCamera);
        // 最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxSelectNum);
        // 选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, selectedMode);
        // 默认选择
        if (selectedPath != null && selectedPath.size() > 0) {
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, selectedPath);
        }
        act.startActivityForResult(intent, requestCode);
    }


    public static void openVideo(Activity act,int requestCode){
        Intent _video_intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        _video_intent.putExtra(MediaStore.EXTRA_OUTPUT, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/");
        _video_intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
        act.startActivityForResult(_video_intent, requestCode);
    }
    public static void openCamera(Activity act, int requestCode) {
//        // 跳转到系统照相机
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (cameraIntent.resolveActivity(act.getPackageManager()) != null) {
//            // 设置系统相机拍照后的输出路径
//            // 创建临时文件
//            mTmpFile = FileUtils.createTmpFile(act);
//            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
//            act.startActivityForResult(cameraIntent, requestCode);
//        } else {
//            Toast.makeText(act, R.string.msg_no_camera, Toast.LENGTH_SHORT).show();
//        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(act.getPackageManager()) != null) {
            mTmpFile = FileUtils.createTmpFile(act);
            if (mTmpFile != null && mTmpFile.exists()) {
                 /*获取当前系统的android版本号*/
                int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                if (currentapiVersion < 24) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
                    act.startActivityForResult(intent, requestCode);
                } else {
                    ContentValues contentValues = new ContentValues(1);
                    contentValues.put(MediaStore.Images.Media.DATA, mTmpFile.getAbsolutePath());
                    Uri uri = act.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    act.startActivityForResult(intent, requestCode);
                }
            } else {
            }

        }
    }

    public static ArrayList<String> GalleryResult(Intent data) {
        ArrayList<String> selectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
        if (selectPath == null)
            selectPath = new ArrayList<String>();
        return selectPath;
    }

    public static String cameraResult() {
//            ArrayList<String> selectPath = new ArrayList<String>();
//            if (mTmpFile != null) {
//                selectPath.add(mTmpFile.getAbsolutePath());
//                mTmpFile = null;
//            }
//            return selectPath;
        return mTmpFile.getPath();
    }


}
