package com.glhd.tb.app.http.res;

import java.util.ArrayList;
import java.util.List;

public class ResFuKuanList {


    /**
     * code : 0
     * message : 上传成功
     * data : [{"id":"1841","planId":"adf29af48f73411a8a5fc8d9c1b77a93","bdId":"1","coding":"RHT20180035","bdName":"山西锦华天成汽车销售有限责任公司","contractmoney":"100045.00","planName":"RHT20180035_01","planIncomemoney":"20000.0000","planIncomePercent":"19.9","planIncomedate":"2018-12-05","differDay":"7","incomemoney":"10000.0000","incomePercent":"50.0","ispayoff":"1"}]
     */

    private int code;
    private String message;
    private List<DataBean> data=new ArrayList<>();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1841
         * planId : adf29af48f73411a8a5fc8d9c1b77a93
         * bdId : 1
         * coding : RHT20180035
         * bdName : 山西锦华天成汽车销售有限责任公司
         * contractmoney : 100045.00
         * planName : RHT20180035_01
         * planIncomemoney : 20000.0000
         * planIncomePercent : 19.9
         * planIncomedate : 2018-12-05
         * differDay : 7
         * incomemoney : 10000.0000
         * incomePercent : 50.0
         * ispayoff : 1
         */

        private String id;
        private String planId;
        private String bdId;
        private String coding;
        private String bdName;
        private String contractmoney;
        private String planName;
        private String planIncomemoney;
        private String planIncomePercent;
        private String planIncomedate;
        private String differDay;
        private String incomemoney;
        private String incomePercent;
        private String ispayoff;
        private boolean check;

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPlanId() {
            return planId;
        }

        public void setPlanId(String planId) {
            this.planId = planId;
        }

        public String getBdId() {
            return bdId;
        }

        public void setBdId(String bdId) {
            this.bdId = bdId;
        }

        public String getCoding() {
            return coding;
        }

        public void setCoding(String coding) {
            this.coding = coding;
        }

        public String getBdName() {
            return bdName;
        }

        public void setBdName(String bdName) {
            this.bdName = bdName;
        }

        public String getContractmoney() {
            return contractmoney;
        }

        public void setContractmoney(String contractmoney) {
            this.contractmoney = contractmoney;
        }

        public String getPlanName() {
            return planName;
        }

        public void setPlanName(String planName) {
            this.planName = planName;
        }

        public String getPlanIncomemoney() {
            return planIncomemoney;
        }

        public void setPlanIncomemoney(String planIncomemoney) {
            this.planIncomemoney = planIncomemoney;
        }

        public String getPlanIncomePercent() {
            return planIncomePercent;
        }

        public void setPlanIncomePercent(String planIncomePercent) {
            this.planIncomePercent = planIncomePercent;
        }

        public String getPlanIncomedate() {
            return planIncomedate;
        }

        public void setPlanIncomedate(String planIncomedate) {
            this.planIncomedate = planIncomedate;
        }

        public String getDifferDay() {
            return differDay;
        }

        public void setDifferDay(String differDay) {
            this.differDay = differDay;
        }

        public String getIncomemoney() {
            return incomemoney;
        }

        public void setIncomemoney(String incomemoney) {
            this.incomemoney = incomemoney;
        }

        public String getIncomePercent() {
            return incomePercent;
        }

        public void setIncomePercent(String incomePercent) {
            this.incomePercent = incomePercent;
        }

        public String getIspayoff() {
            return ispayoff;
        }

        public void setIspayoff(String ispayoff) {
            this.ispayoff = ispayoff;
        }
    }
}
