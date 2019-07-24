package com.glhd.tb.app.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuFilter extends LinearLayout {

    private ArrayList<MenuItem> menus=new ArrayList<>();


    public MenuFilter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        this.setOrientation(LinearLayout.HORIZONTAL);


    }

    public void setData(String[] datas){

        menus.clear();
        removeAllViews();

        for(int i=0;i<datas.length;i++){
            final MenuItem item=new MenuItem(getContext());
            item.setText(datas[i]);
            LayoutParams params=new LayoutParams(0,LayoutParams.WRAP_CONTENT);
            params.weight=1;
            this.addView(item,params);
            menus.add(item);
            final int index=i;
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    reset();
                    item.reset(true);
                    if(itemClickListener!=null){
                        itemClickListener.onClick(index);
                    }
                }
            });

            if(i<datas.length-1) {
                TextView line = new TextView(getContext());
                line.setBackgroundColor(Color.parseColor("#eeeeee"));
                LayoutParams lineP = new LayoutParams(2, LayoutParams.MATCH_PARENT);
                lineP.topMargin=30;
                lineP.bottomMargin=30;
                this.addView(line, lineP);
            }
        }
    }

    public void reset(){
        for(int i=0;i<menus.size();i++){
            menus.get(i).reset(false);
        }
    }

    private void init(Context context) {
    }

    public void performItemClick(final int index){
        if(itemClickListener!=null){
            itemClickListener.onClick(index);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    menus.get(index).performClick();
                }
            },100);
        }
    }

    public void setTitle(int index,String title){
        menus.get(index).setText(title);
    }

    private ItemClickListener itemClickListener;
    public void setOnItemClickListener( ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }

    public interface  ItemClickListener{
        public void onClick(int index);
    }
}
