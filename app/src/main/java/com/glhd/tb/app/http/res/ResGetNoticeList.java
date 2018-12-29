package com.glhd.tb.app.http.res;

import com.glhd.tb.app.base.BaseRes;
import com.glhd.tb.app.base.bean.BeanNotice;

import java.util.ArrayList;

/**
 * Created by chenliangj2ee on 2018/5/3.
 */

public class ResGetNoticeList extends BaseRes {


    private ArrayList<BeanNotice> data = new ArrayList<>();

    public ArrayList<BeanNotice> getData() {
        return data;
    }

    public void setData(ArrayList<BeanNotice> data) {
        this.data = data;
    }
}
