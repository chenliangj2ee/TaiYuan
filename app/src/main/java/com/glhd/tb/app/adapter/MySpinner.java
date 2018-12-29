package com.glhd.tb.app.adapter;

import android.app.Activity;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.base.bean.BeanSpinner;

import java.util.ArrayList;

/**
 * Created by chenliangj2ee on 2018/5/11.
 */

public class MySpinner implements SpinnerAdapter {

    private ArrayList<BeanSpinner> datas = new ArrayList<>();
    private Activity con;

    public MySpinner(Activity con, ArrayList<BeanSpinner> datas) {
        this.datas = datas;
        this.con = con;
    }

    @Override
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {

        LinearLayout layout = new LinearLayout(con);
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(con);
        textView.setTextSize(20);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(Color.WHITE);
        textView.setPadding(50, 40, 10, 40);
        if (datas.get(i) != null)
            textView.setText(datas.get(i).getTitle());
        layout.addView(textView);

        View line = new View(con);
        line.setBackgroundColor(Color.parseColor("#dddddd"));
        layout.addView(line, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        return layout;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        TextView textView = new TextView(con);
        textView.setTextSize(16);
        textView.setPadding(50, 40, 10, 40);
        textView.setBackgroundColor(Color.WHITE);
        if (datas.get(i) != null)
            textView.setText(datas.get(i).getTitle());
        return textView;
    }

    @Override
    public int getItemViewType(int i) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
