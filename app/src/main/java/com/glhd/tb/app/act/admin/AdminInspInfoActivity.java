package com.glhd.tb.app.act.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.MySpinner;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.bean.BeanAdminInsp;
import com.glhd.tb.app.base.bean.BeanSpinner;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetAdminInspInfo;
import com.glhd.tb.app.http.res.ResGetCusSearchBaseData;
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;

import java.util.ArrayList;
import java.util.Calendar;

public class AdminInspInfoActivity extends BaseActivity {

    private LinearLayout container;
    private TextView title;
    private TextView zuotianText;
    private TextView jintianText;
    private TextView mingtianText;
    private TextView preDate;
    private TextView todayDate;
    private TextView nextDate;
    private AppCompatSpinner stationAppCompatSpinner;
    private TextView num1;
    private TextView num2;
    private TextView num3;
    private TextView num4;
    private TextView num5;
    private TextView stationName;

    private String date;
    private String stationId;
    private ArrayList<BeanSpinner> stationsData = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_insp_info);

        zuotianText = (TextView) findViewById(R.id.zuotian_text);
        jintianText = (TextView) findViewById(R.id.jintian_text);
        mingtianText = (TextView) findViewById(R.id.mingtian_text);
        preDate = (TextView) findViewById(R.id.pre_date);
        todayDate = (TextView) findViewById(R.id.today_date);
        nextDate = (TextView) findViewById(R.id.next_date);
        stationAppCompatSpinner = (AppCompatSpinner) findViewById(R.id.station_Spinner);
        num1 = (TextView) findViewById(R.id.num1);
        num2 = (TextView) findViewById(R.id.num2);
        num3 = (TextView) findViewById(R.id.num3);
        num4 = (TextView) findViewById(R.id.num4);
        num5 = (TextView) findViewById(R.id.num5);
        stationName= (TextView) findViewById(R.id.station_name);


        Calendar calendar = Calendar.getInstance();
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        if (Calendar.MONDAY == weekDay) {
            zuotianText.setText("昨天  周日");
            mingtianText.setText("昨天  周二");
        } else if (Calendar.TUESDAY == weekDay) {
            zuotianText.setText("昨天  周一");
            mingtianText.setText("昨天  周三");
        } else if (Calendar.WEDNESDAY == weekDay) {
            zuotianText.setText("昨天  周二");
            mingtianText.setText("昨天  周四");
        } else if (Calendar.THURSDAY == weekDay) {
            zuotianText.setText("昨天  周三");
            mingtianText.setText("昨天  周五");
        } else if (Calendar.FRIDAY == weekDay) {
            zuotianText.setText("昨天  周四");
            mingtianText.setText("昨天  周六");
        } else if (Calendar.SATURDAY == weekDay) {
            zuotianText.setText("昨天  周五");
            mingtianText.setText("昨天  周日");
        } else if (Calendar.SUNDAY == weekDay) {
            zuotianText.setText("昨天  周六");
            mingtianText.setText("昨天  周一");
        }

        initDateView(calendar);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)-1);
        date = calendar.getTimeInMillis()+ "";
        getStations();
//        http();
    }

    private void getStations() {
        API.getAdminAllStations(MySp.getUser(this).getAccountId(), new MyHttp.ResultCallback<ResGetCusSearchBaseData>() {
            @Override
            public void onSuccess(ResGetCusSearchBaseData res) {
                if (res.getCode() == 0) {
                        stationsData.add(new BeanSpinner("","所有车站"));
                    stationsData.addAll(res.getData().getStations());
                    stationAppCompatSpinner.setAdapter(new MySpinner(AdminInspInfoActivity.this, stationsData));
                    stationAppCompatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            stationName.setText(stationsData.get(i).getTitle());
                            stationId=stationsData.get(i).getId();
                            http();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onError(String message) {
            }
        });
    }
    private void initViewData(BeanAdminInsp b) {
        num1.setText(b.getAdvertTotalNum());
        num2.setText(b.getTodayNotInspNum());
        num3.setText(b.getTodayInspNum());
        num4.setText(b.getAdvertFaultNum());
        num5.setText(b.getInspUserNum());

    }


    private void http() {
        API.getAdminInspInfo(MySp.getUser(this).getAccountId(), date, stationId, new MyHttp.ResultCallback<ResGetAdminInspInfo>() {
            @Override
            public void onSuccess(ResGetAdminInspInfo res) {

                if (res.getCode() == 0) {
                    initViewData(res.getData());
                } else {
                    MyToast.showMessage(AdminInspInfoActivity.this, res.getMessage());
                }

            }

            @Override
            public void onError(String message) {
                MyToast.showMessage(AdminInspInfoActivity.this, "网络异常，请稍后再试");
            }
        });
    }


    public void startDateAction(View view) {
        final TextView dateTextView = (TextView) view;
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("选择时间");

        final DatePicker datePicker = new DatePicker(this);
        datePicker.setCalendarViewShown(false);
        dialog.setView(datePicker);


        if (dateTextView.getTag() != null && dateTextView.getTag().toString().contains("-")) {
            String[] dateStrs = dateTextView.getTag().toString().split("-");
            datePicker.init(Integer.parseInt(dateStrs[0]), Integer.parseInt(dateStrs[1]), Integer.parseInt(dateStrs[2]), null);
        }

        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Calendar cal = Calendar.getInstance();
                cal.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth()-1);
                initDateView(cal);
                dateTextView.setText((1 + datePicker.getMonth()) + "月" + datePicker.getDayOfMonth() + "日");
                dateTextView.setTag("" + datePicker.getYear() + "-" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth());
                date = cal.getTimeInMillis() + "";
                http();
            }
        });
        dialog.setNegativeButton("取消", null);
        dialog.show();
    }


    private void initDateView(Calendar date){
        todayDate.setText((1 + date.get(Calendar.MONTH)) + "月" + date.get(Calendar.DAY_OF_MONTH) + "日");
        date.add(Calendar.DAY_OF_MONTH, -1);
        preDate.setText((1 + date.get(Calendar.MONTH)) + "月" + date.get(Calendar.DAY_OF_MONTH) + "日");
        date.add(Calendar.DAY_OF_MONTH, 2);
        nextDate.setText((1 + date.get(Calendar.MONTH)) + "月" + date.get(Calendar.DAY_OF_MONTH) + "日");
    }


    public void stationAction(View view){
        stationAppCompatSpinner.performClick();
    }
}
