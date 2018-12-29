package com.glhd.tb.app.act.inspection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.ItemSpinnerGridviewAdapter;
import com.glhd.tb.app.base.BaseActivity;
import com.glhd.tb.app.base.bean.BeanSpinner;

import java.util.ArrayList;

public class InspFilterSpinnerActivity extends BaseActivity {

    protected TextView title;
    private GridView gridview;
    private ArrayList<BeanSpinner> objects = new ArrayList<BeanSpinner>();
    private ItemSpinnerGridviewAdapter adapter;
    private boolean more = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_insp_filter_spinner);
        objects = (ArrayList<BeanSpinner>) getIntent().getSerializableExtra("data");
        more = getIntent().getBooleanExtra("more", false);
        initView();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title);
        gridview = (GridView) findViewById(R.id.gridview);

        adapter = new ItemSpinnerGridviewAdapter(this, objects);
        adapter.setMore(more);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (more == false) {
                    Intent intent = new Intent();
                    intent.putExtra("selectPosition", i);
                    setResult(0, intent);
                    finish();
                } else {
                    objects.get(i).setChecked(!objects.get(i).isChecked());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void submitAction(View view) {
        ArrayList<Integer> positions = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i).isChecked()) {
                positions.add(i);
            }
        }
        if (positions.size() > 0) {
            Intent intent = new Intent();
            intent.putExtra("selectPositions", positions);
            setResult(0, intent);
        }
        finish();
    }
}
