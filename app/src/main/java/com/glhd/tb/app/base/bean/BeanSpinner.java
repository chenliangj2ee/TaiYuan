package com.glhd.tb.app.base.bean;

import java.io.Serializable;

/**
 * Created by chenliangj2ee on 2018/5/3.
 */

public class BeanSpinner implements Serializable {

    private String id="";
    private String title="";
    private boolean checked=false;
    public BeanSpinner( ) {
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public BeanSpinner(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
