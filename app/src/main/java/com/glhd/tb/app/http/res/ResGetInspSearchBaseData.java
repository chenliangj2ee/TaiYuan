package com.glhd.tb.app.http.res;

import com.glhd.tb.app.base.BaseRes;
import com.glhd.tb.app.base.bean.BeanAasLocation;
import com.glhd.tb.app.base.bean.BeanBaseData;
import com.glhd.tb.app.base.bean.BeanInspSearchBaseData;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by chenliangj2ee on 2018/5/3.
 */

public class ResGetInspSearchBaseData extends BaseRes {

    private ArrayList<BeanAasLocation> data = new ArrayList<>();


    public ArrayList<BeanAasLocation> getData() {
        return data;
    }

    public void setData(ArrayList<BeanAasLocation> data) {
        this.data = data;
    }
}

