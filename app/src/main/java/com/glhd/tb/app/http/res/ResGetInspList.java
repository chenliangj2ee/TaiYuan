package com.glhd.tb.app.http.res;

import com.glhd.tb.app.base.BaseRes;
import com.glhd.tb.app.base.bean.BeanAdvert;
import com.glhd.tb.app.base.bean.BeanContract;
import com.glhd.tb.app.base.bean.BeanPaymentInfo;

import java.util.ArrayList;

/**
 * Created by chenliangj2ee on 2018/5/3.
 */

public class ResGetInspList extends BaseRes {
    public ResGetInspList(){

        if(dataDebug){

            for(int i=0;i<4;i++){
                BeanAdvert ad=new BeanAdvert();
                ad.setLocation("西安北站-宝鸡南站-东侧");
                ad.setCoding("XXX-XXXX-XXXX-XX");
                ad.setTypeTitle("灯箱");
                ad.setNumber("12个");
                ad.setCurrentAdvertEndDate("2018-2-2");
                ad.setCurrentAdvertStartDate("2018-3-2");
                ad.setCurrentAdvertTitle("华为P9手机");
                ad.setInspCycle("每天巡检");
                ad.setNextTimeLatestDate("2018-02-01");
                ad.setImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=351522555,3345611713&fm=27&gp=0.jpg");
                data.add(ad);

            }

        }

    }
    private ArrayList<BeanAdvert> data=new ArrayList<>();

    public ArrayList<BeanAdvert> getData() {
        return data;
    }

    public void setData(ArrayList<BeanAdvert> data) {
        this.data = data;
    }
}
