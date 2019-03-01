package com.glhd.tb.app.act.inspection;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ast365.library.adapter.AstFlowLayoutAdapter;
import com.ast365.library.view.AstFlowLayout;
import com.ast365.library.view.AstFlowViewGroup;
import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.SearchHistoryAdapter;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.bean.BeanSpinner;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetInspSearchBaseData;
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 作废，不在使用
 */
public class InspFilterActivity extends BaseActivity {


    protected LinearLayout dateLayout;
    protected TextView checi;
    protected TextView chedihao;
    private TextView station;
    private TextView type;
    private TextView location;
    private TextView startDate;
    private TextView endDate;
    private AstFlowLayout history;
    private boolean nodate = false;


    private SearchHistoryAdapter hisrotyAdapter;
    private String startDateValue;
    private String endDateValue;

    private BeanSpinner selectStation;
    private BeanSpinner selectType;
    private BeanSpinner selectLocation;

    private BeanSpinner selectCarno;
    private String selectCarnos = null;
    private ArrayList<Integer> positions;
    private BeanSpinner selectMarshalling;

    private ArrayList<BeanSpinner> stations = new ArrayList<>();
    private ArrayList<BeanSpinner> types = new ArrayList<>();
    private ArrayList<BeanSpinner> locations = new ArrayList<>();
    private ArrayList<BeanSpinner> carno = new ArrayList<>();
    private ArrayList<BeanSpinner> marshalling = new ArrayList<>();
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_insp_filter);
        nodate = getIntent().getBooleanExtra("nodate", false);
        initView();
        getBaseData();
    }


    /**
     * 初始化搜索历史layout
     */
    private void initSearchHistoryLayout() {

        ArrayList<String> keys = new ArrayList<>();
        ArrayList<ArrayList<BeanSpinner>> historys = MySp.getSearchHistory(this);
        if (historys == null || historys.isEmpty())
            return;
        for (int i = 0; i < MySp.getSearchHistory(this).size(); i++) {
            ArrayList<BeanSpinner> spinners = MySp.getSearchHistory(this).get(i);
            String key = null;
            for (int j = 0; j < spinners.size(); j++) {
                if (spinners.get(j) != null) {
                    if (key == null) {
                        key = spinners.get(j).getTitle();
                    } else {
                        key = key + "/" + spinners.get(j).getTitle();
                    }

                }
            }
            if (key != null && !"".equals(key.trim())) {
                keys.add(key);
            }
        }

        history.setAdapter(new AstFlowLayoutAdapter<String>(keys) {

            @Override
            public View getView(AstFlowViewGroup parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.item_search_hot_keyword,
                        history, false);
                tv.setText(s);
                return tv;
            }
        });

        history.setOnTagClickListener(new AstFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, AstFlowViewGroup parent) {
                ArrayList<BeanSpinner> selcetSpinner = MySp.getSearchHistory(InspFilterActivity.this).get(position);
                selectStation = selcetSpinner.get(0);
                selectType = selcetSpinner.get(1);
                selectLocation = selcetSpinner.get(2);

                if (nodate) {
                    Intent intent = new Intent(InspFilterActivity.this, InspHistoryActivity.class);
                    intent.putExtra("selectStation", selectStation);
                    intent.putExtra("selectType", selectType);
                    intent.putExtra("selectLocation", selectLocation);
                    setResult(0, intent);
                    finish();
                } else {
                    Intent intent = new Intent(InspFilterActivity.this, InspHistoryActivity.class);
                    intent.putExtra("selectStation", selectStation);
                    intent.putExtra("selectType", selectType);
                    intent.putExtra("selectLocation", selectLocation);
                    startActivity(intent);
                }

                return false;
            }
        });

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

    private void initView() {
        station = (TextView) findViewById(R.id.station);
        type = (TextView) findViewById(R.id.type);
        location = (TextView) findViewById(R.id.location);
        startDate = (TextView) findViewById(R.id.start_date);
        endDate = (TextView) findViewById(R.id.end_date);
        dateLayout = (LinearLayout) findViewById(R.id.date_layout);
        history = (AstFlowLayout) findViewById(R.id.history);
        checi = (TextView) findViewById(R.id.checi);
        chedihao = (TextView) findViewById(R.id.chedihao);
        if (nodate)
            dateLayout.setVisibility(View.GONE);

        initSearchHistoryLayout();

    }

    public void stationAction(View view) {
        if (stations.isEmpty()) {
            MyToast.showMessage(this, "所属车站为空");
            return;
        }


        Intent intent = new Intent(this, InspFilterSpinnerActivity.class);
        intent.putExtra("data", stations);
        intent.putExtra("title", "所属车站");
        startActivityForResult(intent, 0);

    }

    public void typeAction(View view) {
        if (types.isEmpty()) {
            MyToast.showMessage(this, "媒体类型为空");
            return;
        }
        Intent intent = new Intent(this, InspFilterSpinnerActivity.class);
        intent.putExtra("data", types);
        intent.putExtra("title", "媒体类型");
        startActivityForResult(intent, 1);
    }

    public void locationAction(View view) {
        if (locations.isEmpty()) {
            MyToast.showMessage(this, "媒体位置为空");
            return;
        }
        Intent intent = new Intent(this, InspFilterSpinnerActivity.class);
        intent.putExtra("data", locations);
        intent.putExtra("title", "媒体位置");
        startActivityForResult(intent, 2);
    }

    public void checiAction(View view) {
        if (carno.isEmpty()) {
            MyToast.showMessage(this, "车次为空");
            return;
        }
        Intent intent = new Intent(this, InspFilterSpinnerActivity.class);
        intent.putExtra("data", carno);
        intent.putExtra("title", "媒体位置");
        intent.putExtra("more", true);
        startActivityForResult(intent, 3);
    }

    public void chediAction(View view) {
        if (marshalling.isEmpty()) {
            MyToast.showMessage(this, "车底为空");
            return;
        }
        Intent intent = new Intent(this, InspFilterSpinnerActivity.class);
        intent.putExtra("data", marshalling);
        intent.putExtra("title", "媒体位置");
        startActivityForResult(intent, 4);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        if (requestCode == 0 && resultCode == 0) {
            int index = data.getIntExtra("selectPosition", -1);
            selectStation = stations.get(index);
            station.setText(selectStation.getTitle());
            station.setTextColor(Color.parseColor("#1a8df7"));
        }

        if (requestCode == 1 && resultCode == 0) {
            int index = data.getIntExtra("selectPosition", -1);
            selectType = types.get(index);
            type.setText(selectType.getTitle());
            type.setTextColor(Color.parseColor("#1a8df7"));
        }
        if (requestCode == 2 && resultCode == 0) {
            int index = data.getIntExtra("selectPosition", -1);
            selectLocation = locations.get(index);
            location.setText(selectLocation.getTitle());
            location.setTextColor(Color.parseColor("#1a8df7"));
        }
        if (requestCode == 3 && resultCode == 0) {
            int index = data.getIntExtra("selectPosition", -1);
            if (index != -1) {
                selectCarno = carno.get(index);
                checi.setText(selectCarno.getTitle());
                checi.setTextColor(Color.parseColor("#1a8df7"));
            } else {
                positions = (ArrayList<Integer>) data.getSerializableExtra("selectPositions");

                String text = null;
                selectCarnos = null;
                for (int i = 0; i < positions.size(); i++) {
                    if (text == null) {
                        selectCarnos = "" + carno.get(positions.get(i)).getId();
                        text = "" + carno.get(positions.get(i)).getTitle();
                    } else {
                        selectCarnos = selectCarnos + "," + carno.get(positions.get(i)).getId();
                        text = text + "," + carno.get(positions.get(i)).getTitle();
                    }
                }

                checi.setText(text);
                checi.setTextColor(Color.parseColor("#1a8df7"));
            }

        }
        if (requestCode == 4 && resultCode == 0) {
            int index = data.getIntExtra("selectPosition", -1);
            selectMarshalling = marshalling.get(index);
            chedihao.setText(selectMarshalling.getTitle());
            chedihao.setTextColor(Color.parseColor("#1a8df7"));
        }
    }


    /**
     * 西安站/灯箱/大厅出口处
     *
     * @param view
     */
    public void submitAction(View view) {

        ArrayList<BeanSpinner> spinners = new ArrayList<>();
        spinners.add(selectStation);
        spinners.add(selectType);
        spinners.add(selectLocation);


        ArrayList<ArrayList<BeanSpinner>> historys = MySp.getSearchHistory(this);
        if (historys == null)
            historys = new ArrayList<>();

        Gson gson = new Gson();
        for (int i = 0; i < historys.size(); i++) {
            if (gson.toJson(historys.get(i)).equals(gson.toJson(spinners))) {
                historys.remove(i);
                break;
            }
        }

        historys.add(0, spinners);

        MySp.setSearchHistory(this, historys);

        if (nodate == true) {//无时间选择
            Intent intent = new Intent(this, InspHistoryActivity.class);
            if (selectStation != null) {
                intent.putExtra("selectStation", selectStation.getId());
                intent.putExtra("selectStationName", selectStation.getTitle());
            }
            if (selectType != null)
                intent.putExtra("selectType", selectType.getId());
            if (selectLocation != null)
                intent.putExtra("selectLocation", selectLocation.getId());
            if (selectCarno != null)
                intent.putExtra("selectCarno", selectCarno.getId());
            else {
                intent.putExtra("selectCarno", selectCarnos);
            }
            if (selectMarshalling != null)
                intent.putExtra("selectMarshalling", selectMarshalling);
            setResult(0, intent);
            finish();
        } else {//有时间选择，跳转到历史纪录

            Intent intent = new Intent(this, InspHistoryActivity.class);
            if (selectStation != null)
                intent.putExtra("selectStation", selectStation.getId());
            if (selectType != null)
                intent.putExtra("selectType", selectType.getId());
            if (selectLocation != null)
                intent.putExtra("selectLocation", selectLocation.getId());
            if (selectCarno != null)
                intent.putExtra("selectCarno", selectCarno.getId());
            else {
                intent.putExtra("selectCarno", selectCarnos);
            }
            if (selectMarshalling != null)
                intent.putExtra("selectMarshalling", selectMarshalling.getId());


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
                        if (res.getCode() == 0 && res.getData() != null) {
//                            if (res.getData().getLocations() != null)
//                                locations.addAll(res.getData().getLocations());
//                            if (res.getData().getStations() != null)
//                                stations.addAll(res.getData().getStations());
//                            if (res.getData().getTypes() != null)
//                                types.addAll(res.getData().getTypes());
//                            if (res.getData().getCarno() != null)
//                                carno.addAll(res.getData().getCarno());
//                            if (res.getData().getMarshalling() != null)
//                                marshalling.addAll(res.getData().getMarshalling());
                        }
                    }

                    @Override
                    public void onError(String message) {
                        dialog.dismiss();
                    }
                });
    }
}
