package com.glhd.tb.app.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glhd.tb.app.R;


public class MenuItem extends LinearLayout {
    protected View line;
    protected TextView text;
    protected ImageView icon;

    private boolean selected;

    public MenuItem(Context context) {
        super(context);
        init(context);

    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    public void setText(String title) {
        text.setText(title);
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private void init(Context context) {
        initView(View.inflate(context, R.layout.layout_menu_item, this));
    }

    private void initView(View rootView) {
        text = (TextView) rootView.findViewById(R.id.text);
        icon = (ImageView) rootView.findViewById(R.id.icon);
        line=rootView.findViewById(R.id.line);
    }

    public void reset(boolean selected) {
        if (selected) {
            text.setTextColor(getResources().getColor(R.color.menuitem_selected));
            icon.setImageResource(R.drawable.hotel_menuitem_up);
            line.setBackgroundResource(R.color.titleColor);
        } else {
            text.setTextColor(getResources().getColor(R.color.menuitem_default));
            icon.setImageResource(R.drawable.hotel_menuitem_down);
            line.setBackgroundColor(Color.TRANSPARENT);
        }

    }
}
