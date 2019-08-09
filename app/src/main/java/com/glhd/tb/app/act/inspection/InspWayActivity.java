package com.glhd.tb.app.act.inspection;

import android.os.Bundle;
import android.app.Activity;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.SearchView;
import android.widget.CheckBox;
import android.view.View;
import android.widget.ListView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.InspWayListAdapter;
import com.glhd.tb.app.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class InspWayActivity extends BaseActivity {

    private TextView title;
    private EditText searchContent;
    private TextView searchNum;
    private ListView searchList;
    private InspWayListAdapter inspWayListAdapter;
    private List<Object> carlist = new ArrayList<Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insp_way);
        initview();

    }

    private void initview() {
        title = (TextView) findViewById(R.id.title);
        searchContent = (EditText) findViewById(R.id.search_content);
        searchNum = (TextView) findViewById(R.id.search_num);
        searchList = (ListView) findViewById(R.id.search_list);
        inspWayListAdapter = new InspWayListAdapter(this,carlist);
        searchList.setAdapter(inspWayListAdapter);
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(InspWayDetailsActivity.class);
            }
        });
    }


}
