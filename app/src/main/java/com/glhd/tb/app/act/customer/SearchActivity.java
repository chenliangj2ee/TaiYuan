package com.glhd.tb.app.act.customer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.EditText;

import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.MySpinner;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.bean.BeanSpinner;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetCusSearchBaseData;
import com.glhd.tb.app.utils.MySp;

import java.util.ArrayList;

public class SearchActivity extends BaseActivity {


    protected AppCompatSpinner stations;
    protected AppCompatSpinner types;
    protected AppCompatSpinner locations;
    protected AppCompatSpinner directions;
    protected EditText coding;
    protected EditText name;
    private String stationId = "";
    private String typeId = "";
    private String locationId = "";
    private String directionId = "";

    private ArrayList<BeanSpinner> stationsData = new ArrayList<>();
    private ArrayList<BeanSpinner> typesData = new ArrayList<>();
    private ArrayList<BeanSpinner> locationsData = new ArrayList<>();
    private ArrayList<BeanSpinner> directionsData = new ArrayList<>();
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_search);
        initView();

    }

    private void initView() {
        stations = (AppCompatSpinner) findViewById(R.id.stations);
        types = (AppCompatSpinner) findViewById(R.id.types);
        locations = (AppCompatSpinner) findViewById(R.id.locations);
        directions = (AppCompatSpinner) findViewById(R.id.directions);

        getBaseData();

    }

    private void getBaseData() {
        progress=new ProgressDialog(this);
        progress.setMessage("正在加载数据...");
        progress.show();
        API.getCusSearchBaseData(MySp.getUser(this).getAccountId(), new MyHttp.ResultCallback<ResGetCusSearchBaseData>() {
            @Override
            public void onSuccess(ResGetCusSearchBaseData res) {

                if (res.getCode() == 0) {
                    stationsData.add(new BeanSpinner("","所有"));
                    stationsData.addAll(res.getData().getStations());

                    typesData.add(new BeanSpinner("","所有"));
                    typesData.addAll(res.getData().getTypes());

                    locationsData.add(new BeanSpinner("","所有"));
                    locationsData.addAll(res.getData().getLocations());

                    directionsData.add(new BeanSpinner("","所有"));
                    directionsData.addAll(res.getData().getDirections());

                    stations.setAdapter(new MySpinner(SearchActivity.this, stationsData));
                    types.setAdapter(new MySpinner(SearchActivity.this, typesData));
                    locations.setAdapter(new MySpinner(SearchActivity.this, locationsData));
                    directions.setAdapter(new MySpinner(SearchActivity.this, directionsData));
                }
                progress.dismiss();
            }

            @Override
            public void onError(String message) {
                progress.dismiss();
            }
        });
    }

    public void submitAction(View view) {
        try {

            if (stations.getSelectedItemPosition() < stationsData.size())
                stationId = stationsData.get(stations.getSelectedItemPosition()).getId();
            if (types.getSelectedItemPosition() < typesData.size())
                typeId = typesData.get(types.getSelectedItemPosition()).getId();
            if (locations.getSelectedItemPosition() < locationsData.size())
                locationId = locationsData.get(locations.getSelectedItemPosition()).getId();
            if (directions.getSelectedItemPosition() < directionsData.size())
                directionId = directionsData.get(directions.getSelectedItemPosition()).getId();

            Intent intent = new Intent(this, CusAdsListActivity.class);
            intent.putExtra("stationId", stationId);
            intent.putExtra("typeId", typeId);
            intent.putExtra("locationId", locationId);
            intent.putExtra("directionId", directionId);

            startActivity(intent);
        } catch (Exception e) {

        }

    }
}
