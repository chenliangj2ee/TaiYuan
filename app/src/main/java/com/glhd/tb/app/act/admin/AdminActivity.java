package com.glhd.tb.app.act.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.czp.library.ArcProgress;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.act.InspMyActivity;
import com.glhd.tb.app.act.inspection.InspInfoActivity;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.bean.BeanAdminIndexData;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetAdminIndexData;
import com.glhd.tb.app.http.res.ResSearchOne;
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.ArrayList;

public class AdminActivity extends BaseActivity {


    protected TextView shoukuanNum1;
    protected TextView shoukuanNum2;
    protected TextView shoukuanNum3;
    private SwipeRefreshLayout refresh;
    private TextView conNum1;
    private TextView conNum2;
    private TextView conNum3;
    private TextView conNum4;
    private ArcProgress progress01;
    private TextView progressTextView01;
    private PieChart pieChart01;

    private TextView advertNum1;
    private TextView advertNum2;
    private TextView advertNum3;
    private TextView advertNum4;
    private ArcProgress progress02;
    private TextView progressTextView02;
    private PieChart pieChart02;


    private TextView inspNum1;
    private TextView inspNum2;
    private TextView inspNum3;
    private TextView inspNum4;
    private ArcProgress progress03;
    private TextView progressTextView03;
    private PieChart pieChart03;

    private TextView upDvertNum1;
    private TextView upDvertNum2;
    private TextView upDvertNum3;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        initView();
        http();
        upgrade();
    }


    public void initView() {
        refresh = findViewById(R.id.refresh);
        conNum1 = (TextView) findViewById(R.id.con_num_1);
        conNum2 = (TextView) findViewById(R.id.con_num_2);
        conNum3 = (TextView) findViewById(R.id.con_num_3);
        conNum4 = (TextView) findViewById(R.id.con_num_4);
        progress01 = (ArcProgress) findViewById(R.id.progress01);
        progressTextView01 = (TextView) findViewById(R.id.progressTextView01);
        pieChart01 = (PieChart) findViewById(R.id.piechart_01);
        advertNum1 = (TextView) findViewById(R.id.advert_num_1);
        advertNum2 = (TextView) findViewById(R.id.advert_num_2);
        advertNum3 = (TextView) findViewById(R.id.advert_num_3);
        advertNum4 = (TextView) findViewById(R.id.advert_num_4);
        progress02 = (ArcProgress) findViewById(R.id.progress02);
        progressTextView02 = (TextView) findViewById(R.id.progressTextView02);
        upDvertNum1 = (TextView) findViewById(R.id.up_dvert_num_1);
        upDvertNum2 = (TextView) findViewById(R.id.up_dvert_num_2);
        upDvertNum3 = (TextView) findViewById(R.id.up_dvert_num_3);
        inspNum1 = (TextView) findViewById(R.id.insp_num_1);
        inspNum2 = (TextView) findViewById(R.id.insp_num_2);
        inspNum3 = (TextView) findViewById(R.id.insp_num_3);
        inspNum4 = (TextView) findViewById(R.id.insp_num_4);
        progress03 = (ArcProgress) findViewById(R.id.progress03);
        progressTextView03 = (TextView) findViewById(R.id.progressTextView03);
        pieChart02 = (PieChart) findViewById(R.id.piechart_02);
        pieChart03 = (PieChart) findViewById(R.id.piechart_03);
        shoukuanNum1 = (TextView) findViewById(R.id.shoukuan_num1);
        shoukuanNum2 = (TextView) findViewById(R.id.shoukuan_num2);
        shoukuanNum3 = (TextView) findViewById(R.id.shoukuan_num3);



        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                http();
            }
        });

        chat(pieChart01, "0%", 0);
        chat(pieChart02, "0%", 1);
        chat(pieChart03, "0%", 2);

    }

    private void chat(PieChart chat, String value, int index) {
        chat.clear();
        Log.i("chatLog", value);
        log(value);
        value = value.replace("%", "").replace(" ", "");
        int value1 = Integer.parseInt(value);
        int value2 = 100 - value1;

        chat.setUsePercentValues(true);
        chat.setDrawCenterText(false);
        chat.setDrawHoleEnabled(false);
        //模拟数据
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        Log.i("chatLog", "value1:" + value1 + "   value2:" + value2);

        if (value1 == 100) {//value2=0
            entries.add(new PieEntry(value1, value1 + ""));
            entries.add(new PieEntry(value2, ""));
        } else if (value1 == 0) {//value2=0
            entries.add(new PieEntry(value1, ""));
            entries.add(new PieEntry(value2, ""));
        } else {
            entries.add(new PieEntry(value1, value1 + ""));
            entries.add(new PieEntry(value2, value2 + ""));
        }


        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(1f);
        PieData data = new PieData(dataSet);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        if (index == 0) {
            colors.add(Color.parseColor("#ff0000"));
        }
        if (index == 1) {
            colors.add(Color.parseColor("#1a8df7"));
        }
        if (index == 2) {
            colors.add(Color.parseColor("#659775"));
        }
        colors.add(Color.parseColor("#cccccc"));


        dataSet.setColors(colors);
        data.setValueTextSize(0);
        chat.setUsePercentValues(false);
        chat.setDescription("");
        chat.setData(data);
        chat.setRotationEnabled(false);
        chat.setCenterTextSize(0);
        chat.setEntryLabelTextSize(8);
        chat.setTransparentCircleColor(Color.WHITE);
        Legend mLegend = chat.getLegend();  //设置比例图
        mLegend.setEnabled(false);
    }


    private void initViewData(BeanAdminIndexData b) {
        conNum1.setText(b.getContractTotalAmount());
        conNum2.setText(b.getContractNum());
        conNum3.setText(b.getUnreceivableAmount());
        conNum4.setText(b.getReceivableAmount());
        progress01.setMax(100);
        progress01.setProgress(Integer.parseInt(b.getReceivableRate().replace("%", "")));
        progressTextView01.setText(b.getReceivableRate());
        chat(pieChart01, b.getReceivableRate(), 0);


        advertNum1.setText(b.getStationAdvertNum());
        advertNum2.setText(b.getAdvertTotalNum());
        advertNum3.setText(b.getTrainAdvertNum());
        advertNum4.setText(b.getAdvertContractNum());
        progress02.setMax(100);
        progress02.setProgress(Integer.parseInt(b.getAdvertVacantRate().replace("%", "")));
        progressTextView02.setText(b.getAdvertVacantRate());
        chat(pieChart02, b.getAdvertVacantRate(), 1);

        upDvertNum1.setText(b.getTodayUpAdvertNum());
        upDvertNum2.setText(b.getWeekUpAdvertNum());
        upDvertNum3.setText(b.getMonthUpAdvertNum());


        inspNum1.setText(b.getTodayInspNum());
        inspNum2.setText(b.getTodayNotInspNum());
        inspNum3.setText(b.getAdvertFaultNum());
        inspNum4.setText(b.getInspUserNum());
        progress03.setMax(100);
        progress03.setProgress(Integer.parseInt(b.getInspFaultRate().replace("%", "")));
        progressTextView03.setText(b.getInspFaultRate());
        chat(pieChart03, b.getInspFaultRate(), 2);

        shoukuanNum1.setText(b.getTotalNum());
        shoukuanNum2.setText(b.getThirtyNum());
        shoukuanNum3.setText(b.getSixtyNum());
    }


    public void contractAction(View v) {
        startActivity(AdminContractListActivity.class);

    }

    public void advertAction(View v) {
        startActivity(AdminAdvertsListActivity.class);
    }

    public void upAdvertAction(View v) {
        startActivity(AdminUpAdsListActivity.class);
    }

    public void inspAction(View v) {
        startActivity(AdminInspInfoActivity.class);
    }

    public void shouKuanAction(View v){
            startActivity(AdminFuKuanListActivity.class);
    }

    public void scanView(View view) {
        ZXingLibrary.initDisplayOpinion(this);
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, 0);
    }


    private void http() {
        refresh.setRefreshing(true);
        API.getAdminIndexData(MySp.getUser(this).getAccountId(), new MyHttp.ResultCallback<ResGetAdminIndexData>() {
            @Override
            public void onSuccess(ResGetAdminIndexData res) {
                refresh.setRefreshing(false);
                if (res.getCode() == 0) {
                    initViewData(res.getData());
                } else {
                    MyToast.showMessage(AdminActivity.this, res.getMessage());
                }
            }

            @Override
            public void onError(String message) {
                refresh.setRefreshing(false);
                MyToast.showMessage(AdminActivity.this, "网络异常，请稍后再试");
            }
        });


    }

    public void myAction(View view) {
        startActivity(InspMyActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    searchOne(result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(this, "解析失败", Toast.LENGTH_LONG).show();
                }
            }
        }


    }

    private void searchOne(String coding) {
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在查找...");
        dialog.show();
        API.getAdminSearchOne(MySp.getUser(this).getAccountId(), coding, new MyHttp.ResultCallback<ResSearchOne>() {
            @Override
            public void onSuccess(ResSearchOne res) {
                dialog.dismiss();
                if (res.getCode() == 0 && res.getData() != null && res.getData().size() > 0) {
                    //跳转到详情页
                    Intent intent = new Intent(AdminActivity.this, InspInfoActivity.class);
                    intent.putExtra("bean", res.getData().get(0));
                    startActivity(intent);

                } else {
                    MyToast.showMessage(AdminActivity.this, res.getMessage());
                }
            }

            @Override
            public void onError(String message) {
                dialog.dismiss();
                MyToast.showMessage(AdminActivity.this, "查找失败，请稍后再试");


            }
        });
    }

}
