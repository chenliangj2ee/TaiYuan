package com.glhd.tb.app.base.bean;

import java.io.Serializable;

public class BeanAdapterType implements Serializable {

    private String type = "";
    private int num = 1;



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
