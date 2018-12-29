package com.glhd.tb.app.base.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Created by chenliangj2ee on 2018/5/3.
 */

public class BeanAdvert implements Serializable {

    /**
     * id : 8728862
     * coding : BJNA-JANJA-KAL-KALL-LA005
     * location : 北京西站-宝鸡南站-东侧墙面
     * typeTitle : 灯箱
     * inspCycle : 每周一次
     * nextTimeLatestDate : 2018-03-15
     * status : 0
     * currentAdvertTitle : 华为P20手机广告
     * currentAdvertStartDate : 2018-03-15
     * currentAdvertEndDate : 2018-05-15
     * whSize : 200cm*300cm
     * number : 8台
     */

    private String id = "";
    private String coding = "";
    private String location = "";
    private String typeTitle = "";
    private String inspCycle = "";
    private String nextTimeLatestDate = "";
    private String status = "";
    private String currentAdvertTitle = "";
    private String currentAdvertStartDate = "";
    private String currentAdvertEndDate = "";
    private String whSize = "";
    private String number = "";
    private String image = "";
    private String dwImage = "";
    private String mediaImage = "";
    private String contractStartDate = "";
    private String contractEndDate = "";
    private String locationdescribe = "";
    private String properystation = "";
    private BeanInsp insp;

    public String getProperystation() {
        return properystation;
    }

    public void setProperystation(String properystation) {
        this.properystation = properystation;
    }

    public String getLocationdescribe() {
        return locationdescribe;
    }

    public void setLocationdescribe(String locationdescribe) {
        this.locationdescribe = locationdescribe;
    }

    private boolean isChecked = false;

    public String getDwImage() {
        return dwImage;
    }

    public void setDwImage(String dwImage) {
        this.dwImage = dwImage;
    }

    public String getMediaImage() {
        return mediaImage;
    }

    public void setMediaImage(String mediaImage) {
        this.mediaImage = mediaImage;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(String contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public String getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(String contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    private BeanUpAdvert upAds;

    public BeanUpAdvert getUpAds() {
        return upAds;
    }

    public void setUpAds(BeanUpAdvert upAds) {
        this.upAds = upAds;
    }

    public BeanInsp getInsp() {
        return insp;
    }

    public void setInsp(BeanInsp insp) {
        this.insp = insp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTypeTitle() {
        return typeTitle;
    }

    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }

    public String getInspCycle() {
        return inspCycle;
    }

    public void setInspCycle(String inspCycle) {
        this.inspCycle = inspCycle;
    }

    public String getNextTimeLatestDate() {
        return nextTimeLatestDate;
    }

    public void setNextTimeLatestDate(String nextTimeLatestDate) {
        this.nextTimeLatestDate = nextTimeLatestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentAdvertTitle() {
        return currentAdvertTitle;
    }

    public void setCurrentAdvertTitle(String currentAdvertTitle) {
        this.currentAdvertTitle = currentAdvertTitle;
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

    public String getWhSize() {
        return whSize;
    }

    public void setWhSize(String whSize) {
        this.whSize = whSize;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
