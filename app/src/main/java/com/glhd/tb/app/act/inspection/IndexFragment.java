package com.glhd.tb.app.act.inspection;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ast365.library.adapter.AstFlowLayoutAdapter;
import com.ast365.library.view.AstFlowChildView;
import com.ast365.library.view.AstFlowLayout;
import com.ast365.library.view.AstFlowViewGroup;
import com.baidu.location.Address;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.glhd.tb.app.API;
import com.glhd.tb.app.R;
import com.glhd.tb.app.act.InspMyActivity;
import com.glhd.tb.app.adapter.ItemInspIndexHistoryAdapter;
import com.glhd.tb.app.base.BaseRes;
import com.glhd.tb.app.base.MyBaseFragment;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.event.EventCancelMoreSelect;
import com.glhd.tb.app.event.EventInspFilter;
import com.glhd.tb.app.event.EventInspSubmitMore;
import com.glhd.tb.app.event.EventRefreshInspHistoryLayout;
import com.glhd.tb.app.event.EventRefreshInspList;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.res.ResGetInspBaseData;
import com.glhd.tb.app.http.res.ResGetTrajectory;
import com.glhd.tb.app.http.res.ResInspAdsType;
import com.glhd.tb.app.http.res.ResSearchOne;
import com.glhd.tb.app.utils.MyLocation;
import com.glhd.tb.app.utils.MyLog;
import com.glhd.tb.app.utils.MySp;
import com.glhd.tb.app.utils.MyToast;
import com.glhd.tb.app.utils.MyUtils;
import com.google.gson.Gson;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/*
巡检 列表页
 */
public class IndexFragment extends MyBaseFragment implements View.OnClickListener {

    private AstFlowLayout history;
    protected ImageView scan;
    protected TextView title;
    protected TextView my;
    protected ImageView filterImage;
    protected TextView noInsp;
    protected TextView yesInsp;
    protected FrameLayout frame;
    protected GridView gridview;
    protected TextView piLiangXunJian;
    protected TextView xunJianBaoXiu;
    protected ImageView noTypeFilter;
    protected ImageView yesTypeFilter;
    private ProgressDialog dialog;
    private InspTaskListFragment noInspF, yesInspF;
    private boolean isAllSelect = false;
    public int width, height;
    private ItemInspIndexHistoryAdapter adapter;
    private ArrayList<ResGetTrajectory.DataBean> historys = new ArrayList<ResGetTrajectory.DataBean>();
    private int tabIndex = 0;
    private ScrollView scrollView;

    @Override
    public int initViewId() {
        return R.layout.fragment_insp_index;
    }

    @Override
    public void initView() {
        width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        height = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        scan = (ImageView) rootView.findViewById(R.id.scan);
        scan.setOnClickListener(IndexFragment.this);
        title = (TextView) rootView.findViewById(R.id.title);
        my = (TextView) rootView.findViewById(R.id.my);
        my.setOnClickListener(IndexFragment.this);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        filterImage = (ImageView) rootView.findViewById(R.id.filterImage);
        filterImage.setOnClickListener(IndexFragment.this);
        noInsp = (TextView) rootView.findViewById(R.id.no_insp);
        noInsp.setOnClickListener(IndexFragment.this);
        yesInsp = (TextView) rootView.findViewById(R.id.yes_insp);
        yesInsp.setOnClickListener(IndexFragment.this);
        frame = (FrameLayout) rootView.findViewById(R.id.insp_index_frame);
        gridview = (GridView) rootView.findViewById(R.id.gridview);
        piLiangXunJian = (TextView) rootView.findViewById(R.id.pi_liang_xun_jian);
        piLiangXunJian.setOnClickListener(IndexFragment.this);
        xunJianBaoXiu = (TextView) rootView.findViewById(R.id.xun_jian_bao_xiu);
        xunJianBaoXiu.setOnClickListener(IndexFragment.this);
        noTypeFilter = (ImageView) rootView.findViewById(R.id.no_type_filter);
        noTypeFilter.setOnClickListener(IndexFragment.this);
        yesTypeFilter = (ImageView) rootView.findViewById(R.id.yes_type_filter);
        yesTypeFilter.setOnClickListener(IndexFragment.this);
        history = (AstFlowLayout) findViewById(R.id.history);
        noInspF = new InspTaskListFragment("0");
        yesInspF = new InspTaskListFragment("1");
        replace(R.id.insp_index_frame, yesInspF);
        replace(R.id.insp_index_frame, noInspF);
        dialog = new ProgressDialog(getContext());

        gridview.setAdapter(adapter = new ItemInspIndexHistoryAdapter(getActivity(), historys));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            }
        });

        getBaseData();

        getAdsType();

        startLocation();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (InspRangeActivity.isSelect == false) {
            filterImage.performClick();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.scan) {
            ZXingLibrary.initDisplayOpinion(getContext());
            Intent intent = new Intent(getContext(), CaptureActivity.class);
            startActivityForResult(intent, 0);
        } else if (view.getId() == R.id.my) {
            startActivity(InspMyActivity.class);
        } else if (view.getId() == R.id.filterImage) {
            Intent intent = new Intent(getContext(), InspRangeActivity.class);
            intent.putExtra("taskState", taskState);
            startActivity(intent);

        } else if (view.getId() == R.id.no_insp) {
            tabIndex = 0;
            taskState = "0";
            noInsp.setTextColor(getResources().getColor(R.color.titleColor));
            yesInsp.setTextColor(Color.BLACK);
            replace(R.id.insp_index_frame, noInspF);
            getAdsType();
        } else if (view.getId() == R.id.yes_insp) {
            tabIndex = 1;
            taskState = "1";
            yesInsp.setTextColor(getResources().getColor(R.color.titleColor));
            noInsp.setTextColor(Color.BLACK);
            replace(R.id.insp_index_frame, yesInspF);
            getAdsType();
        } else if (view.getId() == R.id.pi_liang_xun_jian) {
            ArrayList<BeanAdvert> objs = noInspF.getDatas();


            if (objs.size() == 0) {
                MyToast.showMessage(getContext(), "请选择项目后提交！");
                return;
            }
            String add = stationName + floorName + locationName + regionName + trainTypeName + marshallingName;

            Intent intent = new Intent(getActivity(), InspSubmitMoreActivity.class);
            intent.putExtra("address", add);
            intent.putExtra("beans", objs);
            startActivity(intent);
        } else if (view.getId() == R.id.xun_jian_bao_xiu) {
            ArrayList<BeanAdvert> objs = noInspF.getDatas();

            if (objs.size() == 0) {
                MyToast.showMessage(getContext(), "请选择项目后提交！");
                return;
            }
            String add = stationName + floorName + locationName + regionName + trainTypeName + marshallingName;

            if ("".equals(add.trim())) {
                MyToast.showMessage(getContext(), "请选择巡检范围！");
                return;
            }

            Intent intent = new Intent(getActivity(), InspRepairActivity.class);
            intent.putExtra("beans", objs);
            intent.putExtra("address", add);
            intent.putExtra("BeanSpinners", inspAdsType.getData());
            intent.putExtra("name", historyName);
            intent.putExtra("locationName", title.getText().toString());
            intent.putExtra("BeanSpinners", inspAdsType.getData());
            startActivity(intent);

        } else if (view.getId() == R.id.no_type_filter) {
            if (tabIndex == 1) {
                noInsp.performClick();
                return;
            }
            dialogChoice(0);
        } else if (view.getId() == R.id.yes_type_filter) {
            if (tabIndex == 0) {
                yesInsp.performClick();
                return;
            }
            dialogChoice(1);
        }
    }

    /**
     * 启动定位
     */
    private void startLocation() {
        final MyLocation location = new MyLocation(getContext());
        location.start(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {

                MyLocation.latitude = bdLocation.getLatitude() + "";
                MyLocation.longitude = bdLocation.getLongitude() + "";
                MyLocation.bdLocation =bdLocation;
                Address address = bdLocation.getAddress();
                MyLog.i("address",new Gson().toJson(bdLocation));
                if (null == bdLocation.getFloor()) {
                    title.setText(address.city + address.district + address.street + address.streetNumber);

                } else {
                    title.setText(address.street + address.streetNumber + bdLocation.getFloor());
                }

                location.stop();

            }


        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //二维码扫描
        if (requestCode == 0) {
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


    }

    ResGetInspBaseData inspBaseData = new ResGetInspBaseData();
    private String stationId = "";
    private String locationId = "";
    private String carnoId = "";
    private String marshallingId = "";
    private String marshallingName = "";
    private String stationTitle = "";
    private String floorId = "";
    private String regionId = "";
    private String mediatypeId = "";
    private String trainType = "";
    private String trainTypeName = "";
    private String inspState = "";

    public String stationName = "";
    public String floorName = "";
    public String locationName = "";
    public String regionName = "";

    private void getBaseData() {
        if (MySp.getUser(getContext()) == null)
            return;
        API.getInspBaseData(MySp.getUser(getContext()).getAccountId(),
                inspState,
                stationId,
                locationId,
                floorId,
                regionId,
                mediatypeId,
                marshallingId,
                trainType,
                new MyHttp.ResultCallback<ResGetInspBaseData>() {
                    @Override
                    public void onSuccess(ResGetInspBaseData res) {
                        if (res.getCode() == 0) {
                            inspBaseData = res;
                            noInsp.setText("待巡检(" + res.getData().getTodayInspNum() + "/" + res.getData().getTodayInspNum() + ")");
                            yesInsp.setText("已巡检(" + res.getData().getAlreadyInspNum() + ")");
                        }
                    }

                    @Override
                    public void onError(String message) {
                    }
                });
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
                        Intent intent = new Intent(getActivity(), InspSubmitActivity.class);
                        intent.putExtra("bean", res.getData().get(0));
                        getActivity().startActivity(intent);

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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateCheckListForBatch(EventInspSubmitMore event) {
        getBaseData();
        getAdsType();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventCancelMoreSelect(EventCancelMoreSelect event) {
    }

    private String taskState = "0";
    private ResInspAdsType inspAdsType = new ResInspAdsType();

    private void getAdsType() {
        if (MySp.getUser(getContext()) == null)
            return;


        API.GetInspAdsType(
                MySp.getUser(getContext()).getAccountId(),
                taskState,
                stationId,
                locationId,
                floorId,
                regionId,
                marshallingId,
                trainType,
                new MyHttp.ResultCallback<ResInspAdsType>() {
                    @Override
                    public void onSuccess(ResInspAdsType res) {
                        if (res.getCode() == 0) {
                            inspAdsType = res;
                        }
                    }

                    @Override
                    public void onError(String message) {
                    }
                });
    }


    int noTypeSelectPosi, yesTypeSelectPosi;
    boolean itemsBoo[];
    boolean itemsBoo2[];

    /**
     * 单选
     */
    private void dialogChoice(int type) {


        if (inspAdsType.getData().size() == 0) {
            MyToast.showMessage(this.getContext(), "无类型");
            return;
        }


        if (type == 0) {

            final String items[] = new String[inspAdsType.getData().size()];
            if (itemsBoo == null)
                itemsBoo = new boolean[inspAdsType.getData().size()];

            for (int i = 0; i < inspAdsType.getData().size(); i++) {
                items[i] = inspAdsType.getData().get(i).getTitle();
            }

            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), 0);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMultiChoiceItems(items, itemsBoo, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                    itemsBoo[i] = b;
                    String types = null;
                    for (int in = 0; in < itemsBoo.length; in++) {
                        if (itemsBoo[in]) {
                            if (types == null) {
                                types = inspAdsType.getData().get(in).getId();
                            } else {
                                types = types + "," + inspAdsType.getData().get(in).getId();
                            }


                        }
                    }
                    mediatypeId = types;
                }
            });

            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    noInspF.refresh(mediatypeId);
                }
            });


            builder.create().show();

        } else {

            final String items[] = new String[inspAdsType.getData().size()];
            if (itemsBoo2 == null)
                itemsBoo2 = new boolean[inspAdsType.getData().size()];

            for (int i = 0; i < inspAdsType.getData().size(); i++) {
                items[i] = inspAdsType.getData().get(i).getTitle();
            }

            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), 0);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMultiChoiceItems(items, itemsBoo2, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                    itemsBoo2[i] = b;
                    String types = null;
                    for (int in = 0; in < itemsBoo2.length; in++) {
                        if (itemsBoo2[in]) {
                            if (types == null) {
                                types = inspAdsType.getData().get(in).getId();
                            } else {
                                types = types + "," + inspAdsType.getData().get(in).getId();
                            }


                        }
                    }
                    mediatypeId = types;
                }
            });

            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    yesInspF.refresh(mediatypeId);
                }
            });


            builder.create().show();

        }

    }


    private String historyName;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventInspFilter(EventInspFilter event) {

        if (event.isHistory)
            return;
        stationId = event.getStationId();
        locationId = event.getLocationId();
        floorId = event.getFloorId();
        regionId = event.getRegionId();
        marshallingId = event.getTrainNo();
        trainType = event.getTrain();

        stationName = event.stationName;
        floorName = event.floorName;
        locationName = event.locationName;
        regionName = event.regionName;
        marshallingName = event.trainNoName;
        trainTypeName = event.trainName;
        getBaseData();
        getAdsType();


        if (event.saveHistory == false)
            return;

        String name = null;
        if (!"".equals(stationName)) {
            name = stationName;
        }
        if (!"".equals(floorName)) {
            name = name + "/" + floorName;
        }
        if (!"".equals(locationName)) {
            name = name + "/" + locationName;
        }
        if (!"".equals(regionName)) {
            name = name + "/" + regionName;
        }
//        if (!"".equals(trainTypeName)) {
//            name = trainTypeName;
//        }
        if (!"".equals(marshallingName)) {
            name = marshallingName;
        }
        historyName=name;
        if (!"".equals(stationName) && "".equals(floorName)) {
            getetTrajectoryList();
            return;
        } else {
            addTrajectory(name);
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventRefreshInspList(EventRefreshInspList event) {
        getBaseData();
        getAdsType();
    }

    public void addTrajectory(String name) {

        if (name == null || "".equals(name))
            return;

        API.addTrajectory(MySp.getUser(getActivity()).getAccountId(),
                name,
                stationId,
                floorId,
                locationId,
                regionId,
                marshallingId,
                trainType,
                new MyHttp.ResultCallback<BaseRes>() {
                    @Override
                    public void onSuccess(BaseRes res) {
                        getetTrajectoryList();
                    }

                    @Override
                    public void onError(String message) {

                    }
                }
        );
    }

    public void getetTrajectoryList() {
        API.getTrajectoryList(MySp.getUser(getActivity()).getAccountId(),
                stationId,
                new MyHttp.ResultCallback<ResGetTrajectory>() {
                    @Override
                    public void onSuccess(ResGetTrajectory res) {
                        if (res.getCode() == 0) {
                            historys.clear();
                            historys.addAll(res.getData());
                            adapter.notifyDataSetChanged();
                            initHistory(historys);
                        }


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                int h = MyUtils.dipTopx(getContext(), 180);
                                if (scrollView.getHeight() > h) {
                                    LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) scrollView.getLayoutParams();
                                    p.height = h;
                                    scrollView.setLayoutParams(p);
                                }
                            }
                        }, 100);

                    }

                    @Override
                    public void onError(String message) {

                    }
                }
        );
    }


    private void initHistory(final ArrayList<ResGetTrajectory.DataBean> historys) {

        ArrayList<String> keys = new ArrayList<>();

        for (int i = 0; i < historys.size(); i++) {
            keys.add(historys.get(i).getName());
        }

//        for(int i=0;i<historys.size();i++){
//            keys.add(historys.get(i).getName());
//        }
//        for(int i=0;i<historys.size();i++){
//            keys.add(historys.get(i).getName());
//        }

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


                for (int i = 0; i < parent.getChildCount(); i++) {
                    parent.getChildAt(i).setEnabled(true);
                    ((TextView) ((AstFlowChildView) parent.getChildAt(i)).getTagView()).setTextColor(Color.parseColor("#333333"));
                }
                parent.postInvalidate();

                view.setEnabled(false);


                parent.getChildAt(position).setEnabled(false);
                ((TextView) ((AstFlowChildView) parent.getChildAt(position)).getTagView()).setTextColor(Color.parseColor("#ffffff"));


                stationId = historys.get(position).getStationId();
                locationId = historys.get(position).getLocationId();
                floorId = historys.get(position).getFloorId();
                regionId = historys.get(position).getRegionId();
                marshallingId = historys.get(position).getMarshallingId();
                trainType = historys.get(position).getTrainType();

                EventInspFilter event = new EventInspFilter();
                event.stationId = stationId;
                event.floorId = floorId;
                event.locationId = locationId;
                event.regionId = regionId;
                event.train = trainType;
                event.trainNo = marshallingId;

                event.stationName = historys.get(position).getStationName();
                event.floorName = historys.get(position).getFloridName();
                event.locationName = historys.get(position).getLocationName();
                event.regionName = historys.get(position).getRegionName();
                event.trainNo = historys.get(position).getTrainTypeName();
                event.trainNoName = historys.get(position).getMarshallingName();
                event.saveHistory = false;

                EventBus.getDefault().post(event);

                return false;
            }
        });
    }

    boolean show = true;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventRefreshInspHistoryLayout(EventRefreshInspHistoryLayout event) {
        int h = MyUtils.dipTopx(getContext(), 180);
        if (h > history.getHeight())
            h = history.getHeight();

        if (event.show && show == false) {
            show = true;
            MyLog.i("chenliangtag", "show.....");
            ObjectAnimator a = ObjectAnimator.ofInt(this, "scrollH", 0, h);
            a.setDuration(500);

            a.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) scrollView.getLayoutParams();
                    p.height = scrollH;
                    scrollView.setLayoutParams(p);
                }
            });
            a.start();
        } else if (event.show == false && show == true) {
            show = false;

            MyLog.i("chenliangtag", "hide.....");
            ObjectAnimator a = ObjectAnimator.ofInt(this, "scrollH", scrollView.getHeight(), 0);
            a.setDuration(500);

            a.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) scrollView.getLayoutParams();
                    p.height = scrollH;
                    scrollView.setLayoutParams(p);
                }
            });
            a.start();
        }


    }

    int scrollH;

    public int getScrollH() {
        return scrollH;
    }

    public void setScrollH(int scrollH) {
        this.scrollH = scrollH;
    }


}