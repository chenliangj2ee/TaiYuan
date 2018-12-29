package com.glhd.tb.app.http;

/**
 * Created by chenliangj2ee on 2018/4/10.
 */

public class MyResponse  {
    private Object bean;
    private int requestCode;
    private Exception e;

    public MyResponse(int requestCode, Object bean, Exception e) {
        this.requestCode = requestCode;
        this.bean = bean;
        this.e = e;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public Exception getE() {
        return e;
    }

    public void setE(Exception e) {
        this.e = e;
    }
}
