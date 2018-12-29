package com.glhd.tb.app.http.res;

import com.glhd.tb.app.base.BaseRes;
import com.glhd.tb.app.base.bean.BeanAdminIndexData;

public class ResGetAdminIndexData extends BaseRes{
    private BeanAdminIndexData data;

    public BeanAdminIndexData getData() {
        return data;
    }

    public void setData(BeanAdminIndexData data) {
        this.data = data;
    }
}
