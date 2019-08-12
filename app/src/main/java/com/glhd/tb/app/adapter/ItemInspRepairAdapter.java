package com.glhd.tb.app.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.EditText;

import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.base.bean.BeanSpinner;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.GetInspRange;
import com.glhd.tb.app.utils.MyLog;
import com.google.gson.Gson;

public class ItemInspRepairAdapter extends BaseAdapter {

    private List<BeanSpinner> objects = new ArrayList<BeanSpinner>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemInspRepairAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public List<BeanSpinner> getObjects() {
        return objects;
    }

    public void setObjects(List<BeanSpinner> objects) {
        this.objects = objects;
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
            convertView = layoutInflater.inflate(R.layout.item_insp_repair, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((BeanSpinner) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(final BeanSpinner object, final ViewHolder holder) {
        holder.type.setText(object.getTitle());
        holder.num.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        holder.repairType.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        GetInspFault(object.getId(), holder, false);

        holder.repairType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (map.get(object.getId()) != null) {
                    faultAction(map.get(object.getId()), holder);
                } else {
                    GetInspFault(object.getId(), holder, true);
                }


            }
        });

    }

    protected class ViewHolder {
        private TextView type;
        private EditText num;
        private TextView repairType;

        public ViewHolder(View view) {
            type = (TextView) view.findViewById(R.id.type);
            num = (EditText) view.findViewById(R.id.num);
            repairType = (TextView) view.findViewById(R.id.repair_type);
        }
    }

    HashMap<String, ArrayList<GetInspRange.DataBean>> map = new HashMap<>();

    private void GetInspFault(final String mediatypeId, final ViewHolder holder, final boolean show) {
        API.GetInspFault(mediatypeId, new MyHttp.ResultCallback<GetInspRange>() {
            @Override
            public void onSuccess(GetInspRange res) {
                if (res.getCode() == 0) {
                    map.put(mediatypeId, res.getData());
                    if (show) faultAction(map.get(mediatypeId), holder);
                }
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    HashMap<Object, boolean[]> boomap = new HashMap<>();
    HashMap<Object,AlertDialog.Builder> builders=new HashMap<>();

    /**
     * 选择故障类型
     *
     * @param v
     */

    boolean[] itemsBoo;
    public void faultAction(final ArrayList<GetInspRange.DataBean> result, final ViewHolder holder) {
        final String items[] = new String[result.size()];

         itemsBoo = boomap.get(holder.type.getText().toString());
        if (itemsBoo == null) {
            itemsBoo = new boolean[result.size()];

            boomap.put(holder.type.getText().toString(),itemsBoo);
        }else{

        }

        for (int i = 0; i < result.size(); i++) {
            items[i] = result.get(i).getTitle();
        }

        AlertDialog.Builder builder=builders.get(holder.type.getText().toString());

        if(builder==null){
            MyLog.i("wok","builder==null");
            builder = new AlertDialog.Builder(context, 0);
            builder.create();
            builder.setCancelable(false);
            builders.put(holder.type.getText().toString(),builder);
            builder.setIcon(R.mipmap.ic_launcher);

            builder.setMultiChoiceItems(items, itemsBoo, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                    itemsBoo[i] = b;
                    boomap.put(holder.type.getText().toString(),itemsBoo);
                    String faultId = null;
                    String title = null;
                    for (int in = 0; in < itemsBoo.length; in++) {
                        if (itemsBoo[in]) {
                            if (faultId == null) {
                                faultId = result.get(in).getId();
                                title = result.get(in).getTitle();
                            } else {
                                faultId = faultId + "," + result.get(in).getId();
                                title = title + "," + result.get(in).getTitle();
                            }


                        }
                    }

                    if (title != null) {
                        holder.repairType.setText(title);
                        holder.repairType.setTag(faultId);//把选择的故障类型保存在tag
                    } else {
                        holder.repairType.setText("请选择故障类型");
                        holder.repairType.setTag("");//把选择的故障类型保存在tag
                    }

                }
            });
        }



        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });



        builder.show();
    }

}
