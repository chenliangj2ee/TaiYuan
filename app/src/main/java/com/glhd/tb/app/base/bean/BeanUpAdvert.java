package com.glhd.tb.app.base.bean;

import java.io.Serializable;


/**
 * Created by chenliangj2ee on 2018/5/3.
 */

public class BeanUpAdvert implements Serializable {


    /**
     * upAdvertDate : 2018-08-09
     * upAdvertTypeTitle : 手机类
     * currentAdvertTitle : 华为p20手机广告
     * currentAdvertImage : http://www.baidu.com/index/demo.jpg
     * currentAdvertStartDate : 2018/03/15
     * currentAdvertEndDate : 2018/05/15
     */

    private String upAdvertDate = "";
    private String upAdvertTypeTitle = "";
    private String currentAdvertTitle = "";
    private String currentAdvertImage = "";
    private String currentAdvertStartDate = "";
    private String currentAdvertEndDate = "";

    public String getUpAdvertDate() {
        return upAdvertDate;
    }

    public void setUpAdvertDate(String upAdvertDate) {
        this.upAdvertDate = upAdvertDate;
    }

    public String getUpAdvertTypeTitle() {
        return upAdvertTypeTitle;
    }

    public void setUpAdvertTypeTitle(String upAdvertTypeTitle) {
        this.upAdvertTypeTitle = upAdvertTypeTitle;
    }

    public String getCurrentAdvertTitle() {
        return currentAdvertTitle;
    }

    public void setCurrentAdvertTitle(String currentAdvertTitle) {
        this.currentAdvertTitle = currentAdvertTitle;
    }

    public String getCurrentAdvertImage() {
        return currentAdvertImage;
    }

    public void setCurrentAdvertImage(String currentAdvertImage) {
        this.currentAdvertImage = currentAdvertImage;
    }

    public String getCurrentAdvertStartDate() {
        return currentAdvertStartDate;
    }

    public void setCurrentAdvertStartDate(String currentAdvertStartDate) {
        this.currentAdvertStartDate = currentAdvertStartDate;
    }

    public String getCurrentAdvertEndDate() {
        return currentAdvertEndDate;
    }

    public void setCurrentAdvertEndDate(String currentAdvertEndDate) {
        this.currentAdvertEndDate = currentAdvertEndDate;
    }
}
