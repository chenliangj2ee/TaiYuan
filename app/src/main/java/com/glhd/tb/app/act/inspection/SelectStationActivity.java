package com.glhd.tb.app.act.inspection;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.act.LoginActivity;
import com.glhd.tb.app.adapter.ItemSelectStationAdapter;
import com.glhd.tb.app.adapter.ItemSelectStationRightAdapter;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.bean.BeanAasLocation;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetInspSearchBaseData;
import com.glhd.tb.app.utils.MySp;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 巡检反馈
 */
public class SelectStationActivity extends BaseActivity {


    protected ListView listviewLeft;
    protected GridView listviewRight;
    protected TextView startDate;
    protected TextView endDate;
    protected LinearLayout dateLayout;
    protected LinearLayout container;
    private Button cancel;
    private Button ok;
    private Button skipButton;

    private boolean nodate;
    private String fromActivity;
    private String stationId, locationId, carnoId, marshallingId, stationName;
    private String startDateValue;
    private String endDateValue;
    private ItemSelectStationAdapter adapterLeft;
    private ItemSelectStationRightAdapter adapterRight;
    private ArrayList<BeanAasLocation> datasLeft = new ArrayList<>();
    private ArrayList<BeanAasLocation> datasRight = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_select_station);
        nodate = getIntent().getBooleanExtra("nodate", false);
        fromActivity = getIntent().getStringExtra("fromActivity");

        initView();
        getBaseData();

    }


    private void initView() {
        listviewLeft = (ListView) findViewById(R.id.listview_left);
        listviewRight = (GridView) findViewById(R.id.listview_right);
        startDate = (TextView) findViewById(R.id.start_date);
        endDate = (TextView) findViewById(R.id.end_date);
        dateLayout = (LinearLayout) findViewById(R.id.date_layout);
        container = (LinearLayout) findViewById(R.id.container);


        cancel = findViewById(R.id.cancel);
        skipButton = findViewById(R.id.skip);
        ok = findViewById(R.id.ok);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stationId = "";
                locationId = "";
                carnoId = "";
                marshallingId = "";
                stationName = "";
                result(false);
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result(true);
            }
        });

        skipButton.setVisibility(LoginActivity.class.getName().equals(fromActivity) ? View.VISIBLE : View.GONE);
        cancel.setVisibility(LoginActivity.class.getName().equals(fromActivity) ? View.GONE : View.VISIBLE);

        adapterLeft = new ItemSelectStationAdapter(this, datasLeft);
        listviewLeft.setAdapter(adapterLeft);

        listviewRight = (GridView) findViewById(R.id.listview_right);
        adapterRight = new ItemSelectStationRightAdapter(this, datasRight);
        listviewRight.setAdapter(adapterRight);

        listviewLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                stationId = datasLeft.get(i).getId();
                stationName = datasLeft.get(i).getTitle();
                adapterLeft.setSelectIndex(i);
                datasRight.clear();
                datasRight.addAll(datasLeft.get(i).getPosition());
                adapterRight.notifyDataSetChanged();

                int s = MySp.getInt(getApplicationContext(), "rightSelectIndex");
                if (s >= 0) {
                    adapterRight.setSelectIndex(s);
                }

                MySp.putInt(getApplicationContext(), "leftSelectIndex", i);

            }
        });
        listviewRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                adapterRight.setSelectIndex(i);
                if (datasRight.get(i).getId2() == null || "".equals(datasRight.get(i).getId2())) {
                    locationId = datasRight.get(i).getId();
                } else {
                    marshallingId = datasRight.get(i).getId();
                    carnoId = datasRight.get(i).getId2();
                }
                result(true);
                MySp.putInt(getApplicationContext(), "rightSelectIndex", i);

            }
        });

        if (InspIndexActivity.class.getSimpleName().equals(fromActivity)) {
            dateLayout.setVisibility(View.VISIBLE);
        }
    }

    private void result(boolean boo) {
        Intent intent = new Intent();
        intent.putExtra("stationId", stationId);
        intent.putExtra("locationId", locationId);
        intent.putExtra("carnoId", carnoId);
        intent.putExtra("marshallingId", marshallingId);
        intent.putExtra("stationName", stationName);


        MySp.putString(this, "stationId", stationId);
        MySp.putString(this, "locationId", locationId);
        MySp.putString(this, "carnoId", carnoId);
        MySp.putString(this, "marshallingId", marshallingId);
        MySp.putString(this, "stationName", stationName);

        if (LoginActivity.class.getSimpleName().equals(fromActivity)) {
            startActivity(InspIndexActivity.class);
        } else if (InspIndexActivity.class.getSimpleName().equals(fromActivity)) {
            if (boo != false)
                toInspHistoryActivity(null);
        } else {
            setResult(Activity.RESULT_OK, intent);
        }

        finish();
    }

    private ProgressDialog dialog;

    private void getBaseData() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("初始化数据...");
        dialog.show();
        String type = nodate ? "0" : "1";
        API.getInspSearchBaseData(MySp.getUser(this).getAccountId(), type,
                new MyHttp.ResultCallback<ResGetInspSearchBaseData>() {
                    @Override
                    public void onSuccess(ResGetInspSearchBaseData res) {
                        dialog.dismiss();
                        if (res.getCode() == 0) {
                            datasLeft.clear();
                            datasLeft.addAll(res.getData());
                            adapterLeft.notifyDataSetChanged();
                            listviewLeft.performItemClick(listviewLeft, 0, 0);
                        }

                    }

                    @Override
                    public void onError(String message) {
                        dialog.dismiss();
                    }
                });
    }


    public void back(View view) {
        if (LoginActivity.class.getName().equals(fromActivity)) {
            startActivity(InspIndexActivity.class);
        }
        finish();
    }

    public void tiaoguoAction(View view) {
        startActivity(InspIndexActivity.class);
        finish();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && LoginActivity.class.getName().equals(fromActivity)) {
            startActivity(InspIndexActivity.class);
            finish();
            return true;
        }

        return super.dispatchKeyEvent(event);
    }

    public void startDateAction(View view) {
        final TextView date = (TextView) view;
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("选择时间");

        final DatePicker datePicker = new DatePicker(this);
        datePicker.setCalendarViewShown(false);
        dialog.setView(datePicker);


        if (date.getText().toString().contains("-")) {
            String[] dateStrs = date.getText().toString().split("-");
            datePicker.init(Integer.parseInt(dateStrs[0]), Integer.parseInt(dateStrs[1]) - 1, Integer.parseInt(dateStrs[2]), null);
        }

        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (date.getTag().toString().equals("0")) {
                    startDateValue = "" + datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth();
                    date.setText(startDateValue);
                } else {
                    endDateValue = "" + datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth();
                    date.setText(endDateValue);
                }


            }
        });
        dialog.setNegativeButton("取消", null);
        dialog.show();
    }


    /**
     * 跳转到历史巡检
     *
     * @param view
     */
    public void toInspHistoryActivity(View view) {


        Intent intent = new Intent(this, InspHistoryActivity.class);
        intent.putExtra("selectStation", stationId);
        intent.putExtra("selectLocation", locationId);
        intent.putExtra("selectCarno", carnoId);
        intent.putExtra("selectMarshalling", marshallingId);


        if (startDateValue != null) {
            String[] startDates = startDateValue.split("-");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(startDates[0]), Integer.parseInt(startDates[1]) - 1, Integer.parseInt(startDates[2]));
            intent.putExtra("startDate", calendar.getTimeInMillis() + "");
        }
        if (endDateValue != null) {
            String[] endDates = endDateValue.split("-");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(endDates[0]), Integer.parseInt(endDates[1]) - 1, Integer.parseInt(endDates[2]));
            intent.putExtra("endDate", calendar.getTimeInMillis() + "");
        }
        startActivity(intent);


    }
}