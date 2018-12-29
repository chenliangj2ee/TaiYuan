package com.glhd.tb.app.base.bean;

import java.io.Serializable;

public class BeanAdminInsp implements Serializable{


    /**
     * advertTotalNum : 1000个
     * todayNotInspNum : 100
     * todayInspNum : 100
     * advertFaultNum : 10
     * inspUserNum : 3
     */

    private String advertTotalNum;
    private String todayNotInspNum;
    private String todayInspNum;
    private String advertFaultNum;
    private String inspUserNum;

    public String getAdvertTotalNum() {
        return advertTotalNum;
    }

    public void setAdvertTotalNum(String advertTotalNum) {
        this.advertTotalNum = advertTotalNum;
    }

    public String getTodayNotInspNum() {
        return todayNotInspNum;
    }

    public void setTodayNotInspNum(String todayNotInspNum) {
        this.todayNotInspNum = todayNotInspNum;
    }

    public String getTodayInspNum() {
        return todayInspNum;
    }

    public void setTodayInspNum(String todayInspNum) {
        this.todayInspNum = todayInspNum;
    }

    public String getAdvertFaultNum() {
        return advertFaultNum;
    }

    public void setAdvertFaultNum(String advertFaultNum) {
        this.advertFaultNum = advertFaultNum;
    }

    public String getInspUserNum() {
        return inspUserNum;
    }

    public void setInspUserNum(String inspUserNum) {
        this.inspUserNum = inspUserNum;
    }
}
