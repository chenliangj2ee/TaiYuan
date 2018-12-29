package com.glhd.tb.app.http.res;

import com.glhd.tb.app.base.BaseRes;
import com.glhd.tb.app.base.bean.BeanBaseData;
import com.glhd.tb.app.base.bean.BeanContract;

import java.util.ArrayList;

/**
 * Created by chenliangj2ee on 2018/5/3.
 */

public class ResGetInspBaseData extends BaseRes {


    private BeanBaseData data = new BeanBaseData();

    public BeanBaseData getData() {
        return data;
    }

    public void setData(BeanBaseData data) {
        this.data = data;
    }
}
