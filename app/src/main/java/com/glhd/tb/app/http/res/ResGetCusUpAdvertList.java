package com.glhd.tb.app.http.res;

import com.glhd.tb.app.base.BaseRes;
import com.glhd.tb.app.base.bean.BeanAdvert;

import java.util.ArrayList;

/**
 * Created by chenliangj2ee on 2018/5/3.
 */

public class ResGetCusUpAdvertList extends BaseRes {

    private ArrayList<BeanAdvert> data;

    public ArrayList<BeanAdvert> getData() {
        return data;
    }

    public void setData(ArrayList<BeanAdvert> data) {
        this.data = data;
    }
}
