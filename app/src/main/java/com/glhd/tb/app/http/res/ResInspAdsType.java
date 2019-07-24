package com.glhd.tb.app.http.res;

import com.glhd.tb.app.base.bean.BeanSpinner;

import java.util.ArrayList;
import java.util.List;

public class ResInspAdsType {


    private int code;
    private String message;
    private ArrayList<BeanSpinner> data=new ArrayList<>();

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

    public ArrayList<BeanSpinner> getData() {
        return data;
    }

    public void setData(ArrayList<BeanSpinner> data) {
        this.data = data;
    }
}
