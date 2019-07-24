package com.glhd.tb.app.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.PopupWindow;

public abstract class MyPopupWindow extends PopupWindow {

    public Context con;
    public int w, h;
    public View rootView;

    public MyPopupWindow(Context con, int w, int h) {
        this.con = con;
        this.w = w;
        this.h = h;
        init();
    }

    public abstract int layoutId();

    private void init() {
        rootView = LayoutInflater.from(con).inflate(layoutId(), null, false);
        setContentView(rootView);
        setWidth(w);
        setHeight(h);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setOutsideTouchable(true);
        setFocusable(true);
        setAnimationStyle(0);
        setTouchable(true);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public View findViewById(int id) {
        return rootView.findViewById(id);
    }

    public abstract void initView();


    /*弹出动画*/

    private void animatorShow() {
        View view = ((ViewGroup) rootView).getChildAt(0);
        ObjectAnimator y = ObjectAnimator.ofFloat(view, "translationY", -1000, 0);
        y.setDuration(300);
        y.setInterpolator(new DecelerateInterpolator());
        y.start();
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
        animatorShow();
    }


}
