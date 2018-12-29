package com.glhd.tb.app.act.inspection;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.glhd.tb.app.R;
import com.glhd.tb.app.base.MyBaseFragment;

import java.util.ArrayList;

/**
 * 上刊施工
 */
public class UpAdsConstructionFragment extends MyBaseFragment implements View.OnClickListener {
    protected RadioButton tab01;
    protected RadioButton tab02;
    protected FrameLayout contentConstruction;
    private ArrayList<Fragment> fs=new ArrayList<>();

    @Override
    public int initViewId() {
        return R.layout.fragment_up_ads_construction;
    }

    @Override
    public void initView() {
        tab01 = (RadioButton) rootView.findViewById(R.id.tab01);
        tab01.setOnClickListener(UpAdsConstructionFragment.this);
        tab02 = (RadioButton) rootView.findViewById(R.id.tab02);
        tab02.setOnClickListener(UpAdsConstructionFragment.this);
        contentConstruction = (FrameLayout) rootView.findViewById(R.id.content_construction);
        initFragment();
        tab01.setChecked(true);
        onClick(tab01);

    }


    private void initFragment(){
        fs.clear();
        fs.add(new UpAdsConstructionFragmentNoYes("1"));
        fs.add(new UpAdsConstructionFragmentNoYes("0"));
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tab01) {
            replace(R.id.content_construction,fs.get(0));
        } else if (view.getId() == R.id.tab02) {
            replace(R.id.content_construction,fs.get(1));
        }
    }
}
