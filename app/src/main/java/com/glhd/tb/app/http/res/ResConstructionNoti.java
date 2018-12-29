package com.glhd.tb.app.http.res;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResConstructionNoti {


    /**
     * code : 0
     * message : 上传成功
     * data : [{"id":"2c32263d43874d559e4e10370a7fee18","contractStartDate":"2018-12-10","contractEndDate":"2018-12-31","contractTtile":"山西锦华天成汽车销售有限责任公司","properystation":"和什托洛盖站","specification":"29.00*0.45*1.00","mediatype":"LED","contractCode":"RHT20180063","contractName":"合同名称","number":"12","contractCompany":"oppo","firstclassification":"快消","secondclassification":"家庭日用品","intervalday":"50","resourceid":"147","remark":"XXXXXXXXXXX","mediaid":"R.WMR.DXG.00026","construcType":"0","publType":"上刊","image":"http://www.test.jpg","ispubl":"0","truction":{"name":"张三","time":"2018-01-01","publStatus":"0","publImageUrl":"http://www.test.jpg","publDate":"2017-01-13","publRemarks":"XXXXXX"}}]
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

    public static class DataBean implements Serializable {
        /**
         * id : 2c32263d43874d559e4e10370a7fee18
         * contractStartDate : 2018-12-10
         * contractEndDate : 2018-12-31
         * contractTtile : 山西锦华天成汽车销售有限责任公司
         * properystation : 和什托洛盖站
         * specification : 29.00*0.45*1.00
         * mediatype : LED
         * contractCode : RHT20180063
         * contractName : 合同名称
         * number : 12
         * contractCompany : oppo
         * firstclassification : 快消
         * secondclassification : 家庭日用品
         * intervalday : 50
         * resourceid : 147
         * remark : XXXXXXXXXXX
         * mediaid : R.WMR.DXG.00026
         * construcType : 0
         * publType : 上刊
         * image : http://www.test.jpg
         * ispubl : 0
         * truction : {"name":"张三","time":"2018-01-01","publStatus":"0","publImageUrl":"http://www.test.jpg","publDate":"2017-01-13","publRemarks":"XXXXXX"}
         */

        private String id;
        private String contractStartDate;
        private String contractEndDate;
        private String contractTtile;
        private String properystation;
        private String specification;
        private String mediatype;
        private String contractCode;
        private String contractName;
        private String number;
        private String contractCompany;
        private String firstclassification;
        private String secondclassification;
        private String intervalday;
        private String resourceid;
        private String remark;
        private String mediaid;
        private String construcType;
        private String publType;
        private String image;
        private String ispubl;
        private TructionBean truction=new TructionBean();

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

        public String getSpecification() {
            return specification;
        }

        public void setSpecification(String specification) {
            this.specification = specification;
        }

        public String getMediatype() {
            return mediatype;
        }

        public void setMediatype(String mediatype) {
            this.mediatype = mediatype;
        }

        public String getContractCode() {
            return contractCode;
        }

        public void setContractCode(String contractCode) {
            this.contractCode = contractCode;
        }

        public String getContractName() {
            return contractName;
        }

        public void setContractName(String contractName) {
            this.contractName = contractName;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getMediaid() {
            return mediaid;
        }

        public void setMediaid(String mediaid) {
            this.mediaid = mediaid;
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

        public String getIspubl() {
            return ispubl;
        }

        public void setIspubl(String ispubl) {
            this.ispubl = ispubl;
        }

        public TructionBean getTruction() {
            return truction;
        }

        public void setTruction(TructionBean truction) {
            this.truction = truction;
        }

        public static class TructionBean implements Serializable {
            /**
             * name : 张三
             * time : 2018-01-01
             * publStatus : 0
             * publImageUrl : http://www.test.jpg
             * publDate : 2017-01-13
             * publRemarks : XXXXXX
             */

            private String name;
            private String time;
            private String publStatus="";
            private String publImageUrl;
            private String publDate;
            private String publRemarks;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

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
