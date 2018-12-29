package com.glhd.tb.app.http.res;

import com.glhd.tb.app.base.BaseRes;
import com.glhd.tb.app.base.bean.BeanInspSearchBaseData;

/**
 * Created by chenliangj2ee on 2018/5/3.
 */

public class ResGetCusSearchBaseData extends BaseRes {

    private BeanInspSearchBaseData data;

    public BeanInspSearchBaseData getData() {
        return data;
    }

    public void setData(BeanInspSearchBaseData data) {
        this.data = data;
    }
}
