package com.glhd.tb.app.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.act.inspection.RepairUserActivity;
import com.glhd.tb.app.http.res.ResGetRepair;
import com.glhd.tb.app.utils.MyToast;

import java.util.ArrayList;

import rx.internal.util.LinkedArrayList;

/**
 * 正方形ImageView
 */
public class TreeViewLayout extends LinearLayout {


    public static ArrayList<ResGetRepair.DataBean.ViewStaffBean> cache;
    ArrayList<String> ids=new ArrayList<>();
    public TreeViewLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setData(ArrayList<ResGetRepair.DataBean.ViewStaffBean> datas){

        if(TreeViewLayout.cache!=null){
            for(int i=0;i<TreeViewLayout.cache.size();i++){
                ids.add(TreeViewLayout.cache.get(i).getId());
            }
        }
        init(0,datas,this);

    }

    private void init(int leave,ArrayList<ResGetRepair.DataBean.ViewStaffBean> datas,LinearLayout parentView){
        for(int i=0;i<datas.size();i++){
            addSub(leave,datas.get(i),parentView);
        }
    }


    private void addSub(int leave, final ResGetRepair.DataBean.ViewStaffBean data, LinearLayout parentView){
        View currentView= View.inflate(getContext(),R.layout.layout_tree,null);
        TextView textView=currentView.findViewById(R.id.textview);
        final CheckBox checkBox=currentView.findViewById(R.id.checkbox);
        if (checkBox.isChecked()){
            checkBox.setChecked(true);
        }
//        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b){
//                    checkBox.setChecked(b);
//                }
//                if(listener!=null){
//                    data.setCheck(checkBox.isChecked());
//                    listener.click(data);
//                }
//            }
//        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CheckBox checkBox1= (CheckBox) view;
//                if (checkBox1.isChecked()){
//                    checkBox.setChecked(true);
//                }
                    if(listener!=null){
                        data.setCheck(checkBox.isChecked());
                        listener.click(data);
                    }
            }
        });
        LinearLayout sub=currentView.findViewById(R.id.sub);
        textView.setText(data.getName());
        checkBox.setText(data.getName());


        for(int index=0;index<ids.size();index++){
            if(ids.get(index)!=null&&ids.get(index).equals(data.getId())){
                data.setCheck(true);
                checkBox.setChecked(true);
                if(listener!=null){
                    data.setCheck(checkBox.isChecked());
                    listener.click(data);
                }
            }
        }



        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        params.leftMargin=leave*50;
        leave++;
        parentView.addView(currentView,params);
        if(data.getSubData()!=null&&data.getSubData().size()>0){
            checkBox.setVisibility(View.GONE);
            init(leave,data.getSubData(),sub);
        }else{
            textView.setVisibility(View.GONE);
        }

    }
    OnClickListener listener;
    public void setOnClick(OnClickListener listener){
        this.listener=listener;
    }
    public interface  OnClickListener{
        public void click(ResGetRepair.DataBean.ViewStaffBean data);
    }

}
