package com.glhd.tb.app.http.res;

import com.glhd.tb.app.base.BaseRes;
import com.glhd.tb.app.base.bean.BeanAdminInsp;

public class ResGetAdminInspInfo extends BaseRes {

    private BeanAdminInsp data;

    public BeanAdminInsp getData() {
        return data;
    }

    public void setData(BeanAdminInsp data) {
        this.data = data;
    }
}
