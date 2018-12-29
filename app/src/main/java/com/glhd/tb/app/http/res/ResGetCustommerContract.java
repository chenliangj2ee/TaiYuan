package com.glhd.tb.app.http.res;

import com.glhd.tb.app.base.BaseRes;
import com.glhd.tb.app.base.bean.BeanContract;
import com.glhd.tb.app.base.bean.BeanUser;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by chenliangj2ee on 2018/5/3.
 */

public class ResGetCustommerContract extends BaseRes {
    public ResGetCustommerContract(){


    }

    private ArrayList<BeanContract> data = new ArrayList<>();

    public ArrayList<BeanContract> getDatas() {
        return data;
    }

    public void setDatas(ArrayList<BeanContract> data) {
        this.data = data;
    }
}
