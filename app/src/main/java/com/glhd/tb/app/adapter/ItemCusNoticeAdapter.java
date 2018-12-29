package com.glhd.tb.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.act.customer.NoticeInfoActivity;
import com.glhd.tb.app.base.bean.BeanNotice;
import com.glhd.tb.app.utils.MyImage;
import com.glhd.tb.app.utils.MyToast;

public class ItemCusNoticeAdapter extends BaseAdapter {

    private ArrayList<BeanNotice> objects = new ArrayList<BeanNotice>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemCusNoticeAdapter(Context context, ArrayList<BeanNotice> objects) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public BeanNotice getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_cus_notice, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((BeanNotice) getItem(position), (ViewHolder) convertView.getTag(), convertView);
        return convertView;
    }

    private void initializeViews(final BeanNotice object, ViewHolder holder, View view) {
        holder.title.setText(object.getTitle());
        holder.date.setText(object.getStartDate() + "至" + object.getEndDate());
        MyImage.load(context, object.getImage(), holder.image);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, NoticeInfoActivity.class);
//                intent.putExtra("data", object);
//                context.startActivity(intent);
                try{
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri apk_url = Uri.parse(object.getContent());
                intent.setData(apk_url);
                context.startActivity(intent);//打开浏览器
                }catch (Exception e){
                    MyToast.showMessage(context,"路径有误...");
                }
            }
        });
    }

    protected class ViewHolder {
        private TextView title;
        private TextView date;
        private ImageView image;

        public ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
            image = (ImageView) view.findViewById(R.id.image);
        }
    }
}
