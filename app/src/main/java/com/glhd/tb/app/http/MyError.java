package com.glhd.tb.app.http;

/**
 * Created by chenliangj2ee on 2018/4/10.
 */

public class MyError<T> {
    private T bean;
    private int requestCode;
    private Exception e;

    public MyError(int requestCode, T bean, Exception e) {
        this.requestCode = requestCode;
        this.bean = bean;
        this.e = e;
    }

    public T getBean() {
        return bean;
    }

    public void setBean(T bean) {
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
