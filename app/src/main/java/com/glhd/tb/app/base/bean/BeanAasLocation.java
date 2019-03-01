package com.glhd.tb.app.base.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class BeanAasLocation implements Serializable {

    private String id = "";
    private String id2 = "";
    private String title = "";
    private ArrayList<BeanAasLocation> position = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<BeanAasLocation> getPosition() {
        return position;
    }

    public void setPosition(ArrayList<BeanAasLocation> position) {
        this.position = position;
    }
}
