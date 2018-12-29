package com.glhd.tb.app.base.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by chenliangj2ee on 2018/5/3.
 */

public class BeanPaymentInfo implements Serializable {


    /**
     * id : 8728862
     * paymentTitle : 首付款
     * paymentPercent : 20%
     * amount : 20万
     * statusTitle: : 未付
     * paymentDate : 2018-02-12
     */

    private String id = "";
    private String paymentTitle = "";
    private String paymentPercent = "";
    private String amount = "";
    private String statusTitle = ""; // FIXME check this code
    private String paymentDate = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaymentTitle() {
        return paymentTitle;
    }

    public void setPaymentTitle(String paymentTitle) {
        this.paymentTitle = paymentTitle;
    }

    public String getPaymentPercent() {
        return paymentPercent;
    }

    public void setPaymentPercent(String paymentPercent) {
        this.paymentPercent = paymentPercent;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatusTitle() {
        return statusTitle;
    }

    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
}
