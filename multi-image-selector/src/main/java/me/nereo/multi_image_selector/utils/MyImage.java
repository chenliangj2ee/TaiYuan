package me.nereo.multi_image_selector.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import me.nereo.multi_image_selector.R;

/**
 * Created by chenliangj2ee on 2018/5/8.
 */

public class MyImage {
    public static void load(Context con, String url, ImageView imageView,int ... wh){
        RequestOptions options=new RequestOptions();
        options.centerCrop();
        options.placeholder(R.drawable.default_error);
        options.error(R.drawable.default_error);
        if(wh.length>1)

        options.override(wh[0],wh[1]);
        Glide.with(con).applyDefaultRequestOptions(options).load(url).into(imageView);
    }
}
