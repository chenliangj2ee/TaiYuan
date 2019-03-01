package com.glhd.tb.app.http.res;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 上刊施工》未完成/已完成
 */
public class ResAdsConstruction {


    /**
     * code : 0
     * message : 上传成功
     * data : [{"id":"2c32263d43874d559e4e10370a7fee18","contractStartDate":"2018-12-10","contractEndDate":"2018-12-31","contractTtile":"山西锦华天成汽车销售有限责任公司","properystation":"和什托洛盖站","mediatype":"LED","number":"12","contractCompany":"oppo","firstclassification":"快消","secondclassification":"家庭日用品","intervalday":"50","resourceid":"147","mediaid":"R.WMR.DXG.00026","remark":"XXXXXXXXXXX","construcType":"0","publType":"上刊","image":"http://www.test.jpg","truction":{"publStatus":"0","publImageUrl":"http://www.test.jpg","publDate":"2017-01-13","publRemarks":"XXXXXX"}}]
     */

    private int code;
    private String message = "";
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

    public static class DataBean implements Serializable {
        /**
         * id : 2c32263d43874d559e4e10370a7fee18
         * contractStartDate : 2018-12-10
         * contractEndDate : 2018-12-31
         * contractTtile : 山西锦华天成汽车销售有限责任公司
         * properystation : 和什托洛盖站
         * mediatype : LED
         * number : 12
         * contractCompany : oppo
         * firstclassification : 快消
         * secondclassification : 家庭日用品
         * intervalday : 50
         * resourceid : 147
         * mediaid : R.WMR.DXG.00026
         * remark : XXXXXXXXXXX
         * construcType : 0
         * publType : 上刊
         * image : http://www.test.jpg
         * truction : {"publStatus":"0","publImageUrl":"http://www.test.jpg","publDate":"2017-01-13","publRemarks":"XXXXXX"}
         */

        private String id;
        private String contractStartDate = "";
        private String contractEndDate = "";
        private String contractTtile = "";
        private String properystation = "";
        private String mediatype = "";
        private String number = "";
        private String contractCompany = "";
        private String firstclassification = "";
        private String secondclassification = "";
        private String intervalday = "";
        private String resourceid = "";
        private String mediaid = "";
        private String remark = "";
        private String construcType = "";
        private String publType = "";
        private String material="";
        private String image = "";
        private TructionBean truction = new TructionBean();

        public String getMaterial() {
            return material;
        }

        public void setMaterial(String material) {
            this.material = material;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getContractTtile() {
            return contractTtile;
        }

        public void setContractTtile(String contractTtile) {
            this.contractTtile = contractTtile;
        }

        public String getProperystation() {
            return properystation;
        }

        public void setProperystation(String properystation) {
            this.properystation = properystation;
        }

        public String getMediatype() {
            return mediatype;
        }

        public void setMediatype(String mediatype) {
            this.mediatype = mediatype;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getContractCompany() {
            return contractCompany;
        }

        public void setContractCompany(String contractCompany) {
            this.contractCompany = contractCompany;
        }

        public String getFirstclassification() {
            return firstclassification;
        }

        public void setFirstclassification(String firstclassification) {
            this.firstclassification = firstclassification;
        }

        public String getSecondclassification() {
            return secondclassification;
        }

        public void setSecondclassification(String secondclassification) {
            this.secondclassification = secondclassification;
        }

        public String getIntervalday() {
            return intervalday;
        }

        public void setIntervalday(String intervalday) {
            this.intervalday = intervalday;
        }

        public String getResourceid() {
            return resourceid;
        }

        public void setResourceid(String resourceid) {
            this.resourceid = resourceid;
        }

        public String getMediaid() {
            return mediaid;
        }

        public void setMediaid(String mediaid) {
            this.mediaid = mediaid;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getConstrucType() {
            return construcType;
        }

        public void setConstrucType(String construcType) {
            this.construcType = construcType;
        }

        public String getPublType() {
            return publType;
        }

        public void setPublType(String publType) {
            this.publType = publType;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public TructionBean getTruction() {
            return truction;
        }

        public void setTruction(TructionBean truction) {
            this.truction = truction;
        }

        public static class TructionBean  implements Serializable  {
            /**
             * publStatus : 0
             * publImageUrl : http://www.test.jpg
             * publDate : 2017-01-13
             * publRemarks : XXXXXX
             */

            private String publStatus = "";
            private String publImageUrl = "";
            private String publDate = "";
            private String publRemarks = "";

            public String getPublStatus() {
                return publStatus;
            }

            public void setPublStatus(String publStatus) {
                this.publStatus = publStatus;
            }

            public String getPublImageUrl() {
                return publImageUrl;
            }

            public void setPublImageUrl(String publImageUrl) {
                this.publImageUrl = publImageUrl;
            }

            public String getPublDate() {
                return publDate;
            }

            public void setPublDate(String publDate) {
                this.publDate = publDate;
            }

            public String getPublRemarks() {
                return publRemarks;
            }

            public void setPublRemarks(String publRemarks) {
                this.publRemarks = publRemarks;
            }
        }
    }
}
