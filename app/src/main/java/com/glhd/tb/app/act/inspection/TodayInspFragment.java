package com.glhd.tb.app.act.inspection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ast365.library.listview.AstListView;
import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.adapter.ItemInspIndexAdapter;
import com.glhd.tb.app.base.MyBaseFragment;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.event.EventInspSubmitMore;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetInspBaseData;
import com.glhd.tb.app.http.res.ResGetInspList;
import com.glhd.tb.app.http.res.ResSearchOne;
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class TodayInspFragment extends MyBaseFragment implements View.OnClickListener, AstListView.RefreshListener {

    protected AstListView listview;
    protected ImageView scan;
    private TextView filter, batch_btn;//batch_btn批量选择
    protected EditText search;
    protected RadioButton noInsp;
    protected RadioButton yesInsp;
    private TextView stationName;
    private View header;
    private View checkLin;//控制底部按钮显示与隐藏
    private Button searchButton, allCheckBtn, confirmBtn;
    private ItemInspIndexAdapter adapter;
    private ArrayList<BeanAdvert> beans = new ArrayList<>();
    private ArrayList<BeanAdvert> beansNo = new ArrayList<>();
    private ArrayList<BeanAdvert> beansYes = new ArrayList<>();
    private int pageNumNo = 0;
    private int pageNumYes = 0;
    private String pageSize = "100";
    private ProgressDialog dialog;
    private String stationId;
    private String locationId;
    private String carnoId;
    private String marshallingId;
    private String stationTitle;

    private boolean isBatch = false;//是否是批量处理


    @Override
    public int initViewId() {
        return R.layout.activity_insp_index_old;
    }

    @Override
    public void initView() {
        stationId = MySp.getString(getContext(), "stationId");
        locationId = MySp.getString(getContext(), "locationId");
        carnoId = MySp.getString(getContext(), "carnoId");
        marshallingId = MySp.getString(getContext(), "marshallingId");
        stationTitle = MySp.getString(getContext(), "stationName");

        checkLin = findViewById(R.id.checkLin);

        listview = (AstListView) findViewById(R.id.listview);
        header = View.inflate(getContext(), R.layout.header_insp_index, null);
        stationName = header.findViewById(R.id.station_name);
        stationName.setText(stationTitle);
        searchButton = header.findViewById(R.id.search_button);
        scan = (ImageView) header.findViewById(R.id.scan);
        scan.setOnClickListener(TodayInspFragment.this);
        filter = header.findViewById(R.id.filter);
        filter.setOnClickListener(this);
        batch_btn = header.findViewById(R.id.batch_btn);
        batch_btn.setOnClickListener(this);
        confirmBtn = (Button) findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(this);
        allCheckBtn = (Button) findViewById(R.id.allCheckBtn);
        allCheckBtn.setOnClickListener(this);
        search = (EditText) header.findViewById(R.id.my_search);
        noInsp = (RadioButton) header.findViewById(R.id.no_insp);
        noInsp.setOnClickListener(TodayInspFragment.this);
        yesInsp = (RadioButton) header.findViewById(R.id.yes_insp);
        yesInsp.setOnClickListener(TodayInspFragment.this);
        noInsp.setChecked(true);

        dialog = new ProgressDialog(getContext());

        listview.addHeaderView(header);
        adapter = new ItemInspIndexAdapter(getContext(), beans);
        listview.setAdapter(adapter);

        listview.setOnRefreshListener(this);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchOne(search.getText().toString(), false);
            }
        });

        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    onRefresh();
//                    searchOne(search.getText().toString(), false);
                }
                return false;

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.scan) {
            ZXingLibrary.initDisplayOpinion(getContext());
            Intent intent = new Intent(getContext(), CaptureActivity.class);
            startActivityForResult(intent, 0);
        } else if (view.getId() == R.id.no_insp) {
            batch_btn.setVisibility(View.VISIBLE);//显示批量处理按钮
            if (beansNo.size() == 0) {
                taskListHttpNo(pageNumNo, noInsp.isChecked() ? "0" : "1");
            } else {
                beans.clear();
                beans.addAll(beansNo);
            }
            adapter.notifyDataSetChanged();

        } else if (view.getId() == R.id.yes_insp) {
            batch_btn.setVisibility(View.GONE);//隐藏批量处理按钮
            if (adapter.isCheckBoxIsShow()) {
                batch_btn.setText("批量处理");
                checkLin.setVisibility(View.GONE);
                isBatch = false;
                adapter.setCheckBoxIsShow(false);
            }


            pageNumYes = 0;
            taskListHttpYes(pageNumYes, true);
        } else if (view.getId() == R.id.filter) {


            Intent intent = new Intent(getContext(), SelectStationActivity.class);
            intent.putExtra("fromActivity", getClass().getSimpleName());
            intent.putExtra("nodate", true);
            startActivityForResult(intent, 1);

        } else if (view.getId() == R.id.batch_btn) {
            if (!isBatch) {
                //显示底部处理按钮
                checkLin.setVisibility(View.VISIBLE);
                batch_btn.setText("取消操作");
                adapter.setCheckBoxIsShow(true);
                isBatch = true;
            } else {
                //隐藏底部处理按钮
                hideSetBatch();
            }
            adapter.notifyDataSetChanged();
        } else if (view.getId() == R.id.allCheckBtn) {
            if (allCheckBtn.getText().equals("全选")) {
                int num=setCheckBox(true);
                allCheckBtn.setText("全部取消"+"("+num+")");
            } else {
                setCheckBox(false);
                allCheckBtn.setText("全选");
            }
            adapter.notifyDataSetChanged();
        } else if (view.getId() == R.id.confirmBtn) {
            ArrayList<BeanAdvert> objs = adapter.getObjects();
            ArrayList<BeanAdvert> selectAds = new ArrayList<>();
            for (int i = 0; i < objs.size(); i++) {
                if (objs.get(i).isChecked()) {
                    selectAds.add(objs.get(i));
                }
            }

            if (selectAds.size() == 0) {
                MyToast.showMessage(getContext(), "请选择项目后提交！");
                return;
            }

            Intent intent = new Intent(getActivity(), InspSubmitMoreActivity.class);
            intent.putExtra("beans", selectAds);
            startActivity(intent);

//            submitData(ids);
        }
    }

    /*
     *
     * 批量设置列表复选框选择状态
     * */
    private int setCheckBox(boolean checked) {
        ArrayList<BeanAdvert> objs = adapter.getObjects();
        for (int i = 0; i < objs.size(); i++) {
            objs.get(i).setChecked(checked);
        }
        return objs.size();
    }


    /*
     *
     * 隐藏与重设批量处理按钮等
     * */
    private void hideSetBatch() {
        batch_btn.setText("批量处理");
        adapter.setCheckBoxIsShow(false);
        setCheckBox(false);//取消所有选中的项
        checkLin.setVisibility(View.GONE);
        isBatch = false;
    }

    /*
     *
     * 批量处理后更新巡检列表
     * */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateCheckListForBatch(EventInspSubmitMore event) {
        if (isBatch) {
            batch_btn.performClick();
        }

        ArrayList<BeanAdvert> objs = adapter.getObjects();
        for (int i = 0; i < event.ids.size(); i++) {
            for (int j = 0; j < objs.size(); j++) {
                if (event.ids.get(i).equals(objs.get(j).getId())) {
                    objs.remove(j);
                    beansYes.add(objs.get(j));
                }
            }

        }
        adapter.notifyDataSetChanged();

    }


    @Override
    public void onResume() {
        super.onResume();
        getBaseData();

        //巡检反馈后，从新刷新列表
        if (InspSubmitActivity.success) {

            for (int i = 0; i < beans.size(); i++) {
                if (beans.get(i).getId().equals(InspSubmitActivity.id)) {
                    beans.remove(i);
                    adapter.notifyDataSetChanged();
                    break;
                }
            }

            for (int i = 0; i < beansNo.size(); i++) {
                if (beansNo.get(i).getId().equals(InspSubmitActivity.id)) {
                    beansNo.remove(i);
                    break;
                }
            }

            pageNumYes = 0;
            //刷新已巡检列表
            taskListHttpYes(pageNumYes, false);
            InspSubmitActivity.success = false;

        }
    }

    private void taskListHttpNo(int num, final String type) {
//        API.getMyInspList(MySp.getUser(getContext()).getAccountId(), type, search.getText().toString(),num + "",
//                pageSize, stationId, locationId, carnoId, marshallingId, new MyHttp.ResultCallback<ResGetInspList>() {
//                    @Override
//                    public void onSuccess(ResGetInspList res) {
//                        listview.stop();
//                        if (res.getCode() == 0 || res.getCode() == 1) {
//
//                            if (res.getCode() == 1) {
//                                MyToast.showMessage(getContext(), res.getMessage());
//                            }
//                            beans.clear();
//                            if (noInsp.isChecked()) {
//                                if (pageNumNo == 0)
//                                    beansNo.clear();
//                                beansNo.addAll(res.getData());
//
//                                beans.addAll(beansNo);
//                                if (res.getData().size() > 0)
//                                    pageNumNo++;
//                                for (int i = 0; i < beans.size(); i++) {
//                                    beans.get(i).setInsp(null);
//
//                                }
//                            }
//
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onError(String message) {
//                        listview.stop();
//                        beans.clear();
//                        if (noInsp.isChecked()) {
//                            beans.addAll(beansNo);
//                            for (int i = 0; i < beans.size(); i++) {
//                                beans.get(i).setInsp(null);
//
//                            }
//                        } else {
//                            beans.addAll(beansYes);
//
//                        }
//
//
//                        adapter.notifyDataSetChanged();
//                        MyToast.showMessage(getContext(), "系统异常");
//                    }
//                });

    }

    private void taskListHttpYes(int num, final boolean refreshList) {
//        API.getMyInspList(MySp.getUser(getContext()).getAccountId(), "1", search.getText().toString(),num + "",
//                pageSize, stationId, locationId, carnoId, marshallingId, new MyHttp.ResultCallback<ResGetInspList>() {
//                    @Override
//                    public void onSuccess(ResGetInspList res) {
//                        listview.stop();
//
//                        if (res.getCode() == 0 || res.getCode() == 1) {
//
//                            if (res.getCode() == 1) {
//                                MyToast.showMessage(getContext(), res.getMessage());
//                            }
//
//
//                            if (!noInsp.isChecked()) {
//                                if (pageNumYes == 0)
//                                    beansYes.clear();
//                                beansYes.addAll(res.getData());
//                                if (res.getData().size() > 0)
//                                    pageNumYes++;
//                                if (refreshList) {
//                                    beans.clear();
//                                    beans.addAll(beansYes);
//                                }
//                            }
//
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onError(String message) {
//                        listview.stop();
//                        beans.clear();
//                        if (noInsp.isChecked()) {
//                            beans.addAll(beansNo);
//                            for (int i = 0; i < beans.size(); i++) {
//                                beans.get(i).setInsp(null);
//
//                            }
//                        } else {
//                            beans.addAll(beansYes);
//
//                        }
//
//
//                        adapter.notifyDataSetChanged();
//                        MyToast.showMessage(getContext(), "系统异常");
//                    }
//                });

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    searchOne(result, true);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getContext(), "解析失败", Toast.LENGTH_LONG).show();
                }
            }
        }

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Log.i("chenliang", "onActivityResult。。。。。。RESULT_OK");
            stationId = "";
            locationId = "";
            carnoId = "";
            marshallingId = "";
            //条件过滤
            try {
                if (data.getStringExtra("stationId") != null) {
                    stationId = data.getStringExtra("stationId");
                }

                if (data.getStringExtra("locationId") != null) {
                    locationId = data.getStringExtra("locationId");
                }

                if (data.getStringExtra("carnoId") != null) {
                    carnoId = data.getStringExtra("carnoId");
                }
                if (data.getStringExtra("marshallingId") != null) {
                    marshallingId = data.getStringExtra("marshallingId");
                }
                if (data.getStringExtra("stationName") != null) {
                    stationName.setText(data.getStringExtra("stationName"));
                } else {
                    stationName.setText("");
                }


                onRefresh();


            } catch (Exception e) {
                onRefresh();
            }


        }

    }

    private void searchOne(String coding, boolean isCoding) {
        if (coding == null || "".equals(coding.trim())) {
            MyToast.showMessage(getContext(), "不能为空");
            return;
        }

        String code = "", text = "";
        if (isCoding)
            code = coding;
        else
            text = coding;

        dialog.setMessage("正在查找...");
        dialog.show();
        API.getSearchOne(MySp.getUser(getContext()).getAccountId(), code, text, new MyHttp.ResultCallback<ResSearchOne>() {
            @Override
            public void onSuccess(ResSearchOne res) {
                dialog.dismiss();
                if (res.getCode() == 0 && res.getData() != null && res.getData().size() > 0) {
                    //跳转到详情页
                    if (res.getData().get(0).getInsp() != null) {
                        Intent intent = new Intent(getContext(), InspInfoActivity.class);
                        intent.putExtra("bean", res.getData().get(0));
                        startActivity(intent);
                    } else {
//                        Intent intent = new Intent(getContext(), InspSubmitActivity.class);
//                        intent.putExtra("bean", res.getData().get(0));
//                        startActivity(intent);

                        ArrayList<BeanAdvert> selectAds = new ArrayList<>();
                        selectAds.add(res.getData().get(0));
                        Intent intent = new Intent(getActivity(), InspSubmitMoreActivity.class);
                        intent.putExtra("beans", selectAds);
                        startActivity(intent);


                    }

                } else {
                    MyToast.showMessage(getContext(), res.getMessage());
                }
            }

            @Override
            public void onError(String message) {
                dialog.dismiss();
                MyToast.showMessage(getContext(), "查找失败，请稍后再试");
            }
        });
    }

    private void getBaseData() {
//        API.getInspBaseData(MySp.getUser(getContext()).getAccountId(), new MyHttp.ResultCallback<ResGetInspBaseData>() {
//            @Override
//            public void onSuccess(ResGetInspBaseData res) {
//                if (res.getCode() == 0) {
//                    noInsp.setText("待巡(" + res.getData().getTodayInspNum() + "/" + res.getData().getTotalInspNum() + ")");
//                    yesInsp.setText("已巡(" + res.getData().getAlreadyInspNum() + ")");
//                } else {
//
//
//                }
//            }
//
//            @Override
//            public void onError(String message) {
//            }
//        });
    }

    @Override
    public void onRefresh() {
        listview.reset();
        if (noInsp.isChecked()) {
            pageNumNo = 0;
            taskListHttpNo(pageNumNo, noInsp.isChecked() ? "0" : "1");
        } else {
            pageNumYes = 0;
            taskListHttpYes(pageNumYes, true);
        }

        getBaseData();
    }

    @Override
    public void onLoadMore() {
        if (noInsp.isChecked()) {
            taskListHttpNo(pageNumNo, noInsp.isChecked() ? "0" : "1");
        } else {
            taskListHttpYes(pageNumYes, true);
        }
    }
}