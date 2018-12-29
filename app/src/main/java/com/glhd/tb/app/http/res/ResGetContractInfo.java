package com.glhd.tb.app.http.res;

import com.glhd.tb.app.base.BaseRes;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.base.bean.BeanContract;
import com.glhd.tb.app.base.bean.BeanPaymentInfo;

import java.util.ArrayList;

/**
 * Created by chenliangj2ee on 2018/5/3.
 */

public class ResGetContractInfo extends BaseRes {


    private BeanContract data = new BeanContract();

    public BeanContract getData() {
        return data;
    }

    public void setData(BeanContract data) {
        this.data = data;
    }
}
