package com.glhd.tb.app.http.res;


import com.glhd.tb.app.base.bean.BeanRepair;

import java.util.List;

/**
 * 上刊施工》未完成/已完成
 */
public class ResGetRepairList {


    private int code;
    private String message;
    private List<BeanRepair> data;

    public int getCode() {
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

    public List<BeanRepair> getData() {
        return data;
    }

    public void setData(List<BeanRepair> data) {
        this.data = data;
    }
}
