package com.glhd.tb.app.base;

import com.glhd.tb.app.http.MyHttpManager;

import java.io.Serializable;

/**
 * Created by chenliangj2ee on 2018/5/3.
 */

public class BaseRes implements Serializable {
    public boolean dataDebug = MyHttpManager.debug;
    private int code = -1;//200成功，其他失败
    private String message = "";

    private String availableNum = "0";
    private String notUpAdsNum = "0";
    private String vacantPercent = "0";
    private int inspNum = 0;
    private int repairNum = 0;



    public int getInspNum() {
        return inspNum;
    }

    public void setInspNum(int inspNum) {
        this.inspNum = inspNum;
    }

    public int getRepairNum() {
        return repairNum;
    }

    public void setRepairNum(int repairNum) {
        this.repairNum = repairNum;
    }

    public int getCode() {
        if (dataDebug) {
            return 0;
        }
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAvailableNum() {
        return availableNum;
    }

    public void setAvailableNum(String availableNum) {
        this.availableNum = availableNum;
    }

    public String getNotUpAdsNum() {
        return notUpAdsNum;
    }

    public void setNotUpAdsNum(String notUpAdsNum) {
        this.notUpAdsNum = notUpAdsNum;
    }

    public String getVacantPercent() {
        return vacantPercent;
    }

    public void setVacantPercent(String vacantPercent) {
        this.vacantPercent = vacantPercent;
    }
}
