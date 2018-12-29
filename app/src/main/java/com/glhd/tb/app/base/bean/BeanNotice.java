package com.glhd.tb.app.base.bean;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class BeanNotice implements Serializable{


    /**
     * id : 8728862
     * title : 20180809870092
     * image : 西安广告灯箱传媒广告
     * startDate : 2010-02-02
     * content : 公告内容
     * endDate : 2010-02-02
     */

    private String id;
    private String title;
    private String image;
    private String startDate;
    private String content;
    private String endDate;
    private String releaseDate;

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    private ArrayList<BeanAdvert> ads=new ArrayList<>();

    public ArrayList<BeanAdvert> getAds() {
        return ads;
    }

    public void setAds(ArrayList<BeanAdvert> ads) {
        this.ads = ads;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
