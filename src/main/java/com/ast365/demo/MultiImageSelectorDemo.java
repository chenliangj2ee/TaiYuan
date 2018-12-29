package com.ast365.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

import me.nereo.multi_image_selector.Multi;

/**
 * 图片选择器使用说明：
 * <p>
 * 直接copy以下代码到开发者自己的activity里即可。
 * <p>
 * Created by chenliangj2ee on 2016/6/26.
 */
public class MultiImageSelectorDemo extends Activity {

    ArrayList<String> selectedIcon = new ArrayList<String>();//保存图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * 打开图库
         */
        private void openGallery () {
            Multi.openGallery(this, 9, selectedIcon, 0);
        }

        /**
         * 打开相机
         */
        private void openCamera () {
            Multi.openCamera(this, 1);
        }?number >

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case 0:
                        selectedIcon = Multi.GalleryResult(data);//来自图库
                        break;
                    case 1:
                        selectedIcon = Multi.cameraResult();//来自相机
                        break;
                }
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
