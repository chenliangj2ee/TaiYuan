package com.glhd.tb.app.act;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.ImageAdapter;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.utils.MyImage;
import com.glhd.tb.app.utils.MyToast;
import com.glhd.tb.app.utils.MyVideo;

import java.util.ArrayList;

/**
 * Created by scx on 2017/12/10.
 */

public class ImageActivity extends BaseActivity {


    protected ViewPager vpGuide;
    protected TextView index;
    private ImageAdapter adapter;
    private ArrayList<View> views = new ArrayList<>();
    private ArrayList<String> urls;
    private int posi;
    private String defaultUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_image);
        posi = getIntent().getIntExtra("posi", 0);
        urls = getIntent().getStringArrayListExtra("bean");
        defaultUrl = getIntent().getStringExtra("defaultUrl");
        initView();
        for (int i = 0; i < urls.size(); i++) {
            ImageView iv = new ImageView(getApplication());
            iv.setBackgroundColor(Color.parseColor("#eeeeee"));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            iv.setLayoutParams(lp);
            final String url = urls.get(i);
            if (url.endsWith(".mp4")) {
                MyImage.load(this, defaultUrl + "", iv);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MyVideo.open(getApplicationContext(), url.substring(0, url.length() - 4));
                    }
                });
            } else {
                MyImage.load(this, url, iv);
            }


            views.add(iv);

        }

        index.setText("1/" + urls.size());
        adapter = new ImageAdapter(views);
        vpGuide.setAdapter(adapter);
        vpGuide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index.setText(( position + 1) + "/" + urls.size());
                if (urls.get(position).endsWith(".mp4")) {
                    MyToast.showMessage(getApplicationContext(), "点击可以播放视频");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpGuide.setCurrentItem(posi);


    }


    private void initView() {
        vpGuide = (ViewPager) findViewById(R.id.vp_guide);
        index = (TextView) findViewById(R.id.index);
    }
}
