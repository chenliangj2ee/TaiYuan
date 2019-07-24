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
import com.glhd.tb.app.http.res.ResGetTrajectory;

public class ItemInspIndexHistoryAdapter extends BaseAdapter {

    private List<ResGetTrajectory.DataBean> objects = new ArrayList<ResGetTrajectory.DataBean>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemInspIndexHistoryAdapter(Context context, ArrayList<ResGetTrajectory.DataBean> historys) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = historys;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public ResGetTrajectory.DataBean getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_insp_index_history, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((ResGetTrajectory.DataBean) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(ResGetTrajectory.DataBean o, ViewHolder holder) {
        holder.title.setText(o.getName());
    }

    protected class ViewHolder {
        private TextView title;

        public ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.title);
        }
    }
}
