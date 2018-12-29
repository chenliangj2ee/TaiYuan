package com.glhd.tb.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.http.res.ResFuKuanList;

import java.util.ArrayList;

public class ItemAdminFukuanTixingAdapter extends BaseAdapter {

    private ArrayList<ResFuKuanList.DataBean> objects = new ArrayList<>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemAdminFukuanTixingAdapter(Context context, ArrayList<ResFuKuanList.DataBean> objects) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public ResFuKuanList.DataBean getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_admin_fukuan_tixing, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((ResFuKuanList.DataBean) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(final ResFuKuanList.DataBean b, final ViewHolder h) {
        h.checkbox.setChecked(b.isCheck());
        h.idView.setText(b.getCoding());
        h.companyView.setText(b.getBdName());
        h.contractView.setText(b.getPlanName());
        h.dateView.setText(b.getPlanIncomedate());
        h.dayView.setText("预期天数"+b.getDifferDay());
        h.price1View.setText("应收金额"+b.getPlanIncomemoney()+"("+b.getPlanIncomePercent()+"%)");
        h.price2View.setText("累计收款"+b.getIncomemoney()+"("+b.getIncomePercent()+"%)");
        h.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.setCheck(h.checkbox.isChecked());
            }
        });
    }

    protected class ViewHolder {
        private CheckBox checkbox;
        private TextView idView;
        private TextView companyView;
        private TextView contractView;
        private TextView price1View;
        private TextView dateView;
        private TextView dayView;
        private TextView price2View;

        public ViewHolder(View view) {
            checkbox = (CheckBox) view.findViewById(R.id.checkbox);
            idView = (TextView) view.findViewById(R.id.id_view);
            companyView = (TextView) view.findViewById(R.id.company_view);
            contractView = (TextView) view.findViewById(R.id.contract_view);
            price1View = (TextView) view.findViewById(R.id.price1_view);
            dateView = (TextView) view.findViewById(R.id.date_view);
            dayView = (TextView) view.findViewById(R.id.day_view);
            price2View = (TextView) view.findViewById(R.id.price2_view);
        }
    }
}
