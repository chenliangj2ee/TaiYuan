package com.glhd.tb.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.utils.MyImage;

public class ItemInspSubmitMoreGridviewAdapter extends BaseAdapter {

    private ArrayList<String> objects = new ArrayList<String>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemInspSubmitMoreGridviewAdapter(Context context, ArrayList<String> objects) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public String getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_insp_submit_more_gridview, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((String) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(final String object, ViewHolder holder) {
        MyImage.loadFile(context, object, holder.icon);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                objects.remove(object);
                notifyDataSetChanged();
            }
        });
    }

    protected class ViewHolder {
        private ImageView icon;
        private ImageView delete;

        public ViewHolder(View view) {
            icon = (ImageView) view.findViewById(R.id.icon);
            delete = (ImageView) view.findViewById(R.id.delete);
        }
    }
}