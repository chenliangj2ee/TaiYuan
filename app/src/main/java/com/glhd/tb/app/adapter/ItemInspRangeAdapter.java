package com.glhd.tb.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.http.res.GetInspRange;

import java.util.ArrayList;
import java.util.List;

public class ItemInspRangeAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<GetInspRange.DataBean> mStringList = new ArrayList<>();
    private int selectPosition=-1;

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    public void setStringList(ArrayList<GetInspRange.DataBean> mStringList) {
        clearStringList();
        this.mStringList = mStringList;
    }

    public void clearStringList() {
        selectPosition = -1;
        if (mStringList != null && mStringList.size() != 0) {
            mStringList.clear();
        }
        notifyDataSetChanged();
    }


    public ItemInspRangeAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mStringList.size() == 0 ? 0 : mStringList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_insp_range, null);
        TextView textView = view.findViewById(R.id.tv_item);
        textView.setText(mStringList.get(position).getTitle());
        if (selectPosition == position) {
            textView.setTextColor(mContext.getResources().getColor(R.color.tv_select));
        } else {
            textView.setTextColor(mContext.getResources().getColor(R.color.tv_unselect));
        }
        return view;
    }
}
