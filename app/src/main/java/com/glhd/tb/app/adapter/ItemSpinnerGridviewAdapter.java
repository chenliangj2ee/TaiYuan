package com.glhd.tb.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.base.bean.BeanSpinner;


public class ItemSpinnerGridviewAdapter extends BaseAdapter {

    private ArrayList<BeanSpinner> objects = new ArrayList<BeanSpinner>();

    private Context context;
    private LayoutInflater layoutInflater;
    private boolean more = false;
    public ItemSpinnerGridviewAdapter(Context context, ArrayList<BeanSpinner> objects) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = objects;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public BeanSpinner getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_spinner_gridview, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((BeanSpinner) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(BeanSpinner object, ViewHolder holder) {
        holder.name.setText(object.getTitle());
        holder.name.setEnabled(!object.isChecked());
    }

    protected class ViewHolder {
        private TextView name;

        public ViewHolder(View view) {
            name = view.findViewById(R.id.name);
        }
    }
}
