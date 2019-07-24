package com.glhd.tb.app.base.bean;

import java.io.Serializable;

/**
 * Created by chenliangj2ee on 2018/5/3.
 */

public class BeanBaseData implements Serializable {


    /**
     * todayInspNum : 12
     * totalInspNum : 23
     * alreadyInspNum : 12
     */

    private String todayInspNum = "0";
    private String totalInspNum = "0";
    private String alreadyInspNum = "0";

    public String getTodayInspNum() {
        return todayInspNum;
    }

    public void setTodayInspNum(String todayInspNum) {
        this.todayInspNum = todayInspNum;
    }

    public String getTotalInspNum() {
        return totalInspNum;
    }

    public void setTotalInspNum(String totalInspNum) {
        this.totalInspNum = totalInspNum;
    }

    public String getAlreadyInspNum() {
        return alreadyInspNum;
    }

    public void setAlreadyInspNum(String alreadyInspNum) {
        this.alreadyInspNum = alreadyInspNum;
    }
}
