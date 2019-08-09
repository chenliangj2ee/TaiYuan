package com.glhd.tb.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.base.bean.BeanAdapterType;

import java.util.ArrayList;

public class ItemInspSubmitMoreAdapter extends BaseAdapter {

    private ArrayList<BeanAdapterType> objects = new ArrayList<BeanAdapterType>();

    private Context context;
    private LayoutInflater layoutInflater;
    private GridView typeGridView;

    public ItemInspSubmitMoreAdapter(Context context, ArrayList<BeanAdapterType> objects, GridView typeGridView) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = objects;
        this.typeGridView = typeGridView;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public BeanAdapterType getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_insp_submit_more, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((BeanAdapterType) getItem(position), (ViewHolder) convertView.getTag());

        return convertView;
    }

    private void initializeViews(BeanAdapterType b, ViewHolder h) {
        h.text.setChecked(true);
        h.text.setText(b.getType()+" "+ b.getNum()+"(个)");
    }

    protected class ViewHolder {
        private CheckBox text;

        public ViewHolder(View view) {
            text = (CheckBox) view.findViewById(R.id.text);
        }
    }
}
