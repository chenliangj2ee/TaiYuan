package com.glhd.tb.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.act.admin.AdminContractInfoActivity;
import com.glhd.tb.app.act.customer.ContractInfoActivity;
import com.glhd.tb.app.base.bean.BeanContract;

import java.util.ArrayList;
import java.util.List;

public class ItemCusContractListAdapter extends BaseAdapter {

    private List<BeanContract> beans = new ArrayList<BeanContract>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemCusContractListAdapter(Context context, ArrayList<BeanContract> beans) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.beans=beans;
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public BeanContract getItem(int position) {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_he_tong_xin_xi, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((BeanContract)getItem(position), (ViewHolder) convertView.getTag(),convertView);
        return convertView;
    }

    private void initializeViews(final BeanContract b, ViewHolder holder, View view) {
        holder.id.setText(b.getCoding());
        holder.name.setText(b.getContractTitle());
        holder.contractDate.setText(b.getContractDate());
        holder.totalPrice.setText(b.getTotalAmount());
        holder.payPrice.setText(b.getPayment());
        holder.payBaiFenBi.setText("("+b.getPaymentPercent()+")");
        holder.company.setText(b.getCompany());
        holder.startEndDate.setText(b.getStartDate()+"~"+b.getEndDate());
        holder.payType.setText("实际付款");

        if("~".equals(holder.startEndDate.getText().toString())){
            holder.startEndDate.setText("");
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,ContractInfoActivity.class);
                intent.putExtra("data",b);
                context.startActivity(intent);
            }
        });


    }

    static

    class ViewHolder {
        protected TextView name;
        protected TextView id;
        protected TextView contractDate;
        protected TextView totalPrice;
        protected TextView payPrice;
        protected TextView payBaiFenBi;
        protected TextView company;
        protected TextView startEndDate;
        protected LinearLayout container;
        protected TextView payType;
        ViewHolder(View rootView) {
            initView(rootView);
        }

        private void initView(View rootView) {
            name = (TextView) rootView.findViewById(R.id.name);
            id = (TextView) rootView.findViewById(R.id.id);
            contractDate = (TextView) rootView.findViewById(R.id.contract_date);
            totalPrice = (TextView) rootView.findViewById(R.id.total_price);
            payPrice = (TextView) rootView.findViewById(R.id.pay_price);
            payBaiFenBi = (TextView) rootView.findViewById(R.id.pay_bai_fen_bi);
            company = (TextView) rootView.findViewById(R.id.company);
            startEndDate = (TextView) rootView.findViewById(R.id.start_end_date);
            container = (LinearLayout) rootView.findViewById(R.id.container);
            payType = (TextView) rootView.findViewById(R.id.pay_type);
        }
    }
}
