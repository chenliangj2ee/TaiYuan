package com.glhd.tb.app.base.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**合同
 * Created by chenliangj2ee on 2018/5/4.
 */

public class BeanContract implements Serializable{


    /**
     * id : 8728862
     * coding : 20180809870092
     * contractTitle : 西安广告灯箱传媒广告
     * contractDate : 2010-02-02
     * company: : 北京bac有限公司
     * totalAmount : 100万
     * payment : 20万
     * paymentPercent : 20%
     * startDate : 2010-02-02
     * endDate : 2010-02-02
     */

    private String id="";
    private String coding="";
    private String contractTitle="";
    private String contractDate="";
    private String company=""; // FIXME check this code
    private String totalAmount="";
    private String payment="";
    private String paymentPercent="";
    private String startDate="";
    private String endDate="";

    private ArrayList<BeanPaymentInfo> paymentInfos=new ArrayList<>();
    private ArrayList<BeanAdvert> ads=new ArrayList<>();

    public ArrayList<BeanPaymentInfo> getPaymentInfos() {
        return paymentInfos;
    }

    public void setPaymentInfos(ArrayList<BeanPaymentInfo> paymentInfos) {
        this.paymentInfos = paymentInfos;
    }

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

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

    public String getContractTitle() {
        return contractTitle;
    }

    public void setContractTitle(String contractTitle) {
        this.contractTitle = contractTitle;
    }

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPaymentPercent() {
        return paymentPercent;
    }

    public void setPaymentPercent(String paymentPercent) {
        this.paymentPercent = paymentPercent;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
