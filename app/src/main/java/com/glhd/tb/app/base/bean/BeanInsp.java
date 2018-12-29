package com.glhd.tb.app.base.bean;

import java.io.Serializable;

/**
 * Created by chenliangj2ee on 2018/5/3.
 */

public class BeanInsp implements Serializable {


    /**
     * inspStatus : 正常
     * inspImageUrl : http://www.test.jpg
     * inspDate : 2018-01-12
     * inspRemarks : 巡检结果一切正常
     */
    private String inspStatus="";
    private String inspStatusTitl="";
    private String inspImageUrl="";
    private String inspDate="";
    private String inspRemarks="无";
    private String inspUser="";

    public String getInspUser() {
        return inspUser;
    }

    public void setInspUser(String inspUser) {
        this.inspUser = inspUser;
    }

    public String getInspStatus() {
        return inspStatus;
    }

    public void setInspStatus(String inspStatus) {
        this.inspStatus = inspStatus;
    }

    public String getInspStatusTitl() {
        return inspStatusTitl;
    }

    public void setInspStatusTitl(String inspStatusTitl) {
        this.inspStatusTitl = inspStatusTitl;
    }

    public String getInspImageUrl() {
        return inspImageUrl;
    }

    public void setInspImageUrl(String inspImageUrl) {
        this.inspImageUrl = inspImageUrl;
    }

    public String getInspDate() {
        return inspDate;
    }

    public void setInspDate(String inspDate) {
        this.inspDate = inspDate;
    }

    public String getInspRemarks() {
        return inspRemarks;
    }

    public void setInspRemarks(String inspRemarks) {
        this.inspRemarks = inspRemarks;
    }
}
