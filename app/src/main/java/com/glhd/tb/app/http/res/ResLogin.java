package com.glhd.tb.app.http.res;

import com.glhd.tb.app.base.BaseRes;
import com.glhd.tb.app.base.bean.BeanUser;

/**
 * Created by chenliangj2ee on 2018/5/3.
 */

public class ResLogin extends BaseRes {

    private BeanUser data;

    public BeanUser getData() {
        return data;
    }

    public void setData(BeanUser data) {
        this.data = data;
    }
}
