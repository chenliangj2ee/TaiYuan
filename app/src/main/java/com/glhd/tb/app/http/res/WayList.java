package com.glhd.tb.app.http.res;

import java.io.Serializable;
import java.util.List;

public class WayList implements Serializable {

    /**
     * code : 0
     * data : [{"duration":"5小时42分","endStation":"北京西","endTime":"18:26:00","startStation":"西安北","startTime":"12:44:00","trainNoList":[{"date":"12:44:00~18:26:00","dateTimeFlag":"G25","distance":"西安北~北京西","trainNo":"G658"}],"trainsetName":"CRH380AL-2589(0G658,G658,0G25,G25)"}]
     */

    private int code;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * duration : 5小时42分
         * endStation : 北京西
         * endTime : 18:26:00
         * startStation : 西安北
         * startTime : 12:44:00
         * trainNoList : [{"date":"12:44:00~18:26:00","dateTimeFlag":"G25","distance":"西安北~北京西","trainNo":"G658"}]
         * trainsetName : CRH380AL-2589(0G658,G658,0G25,G25)
         */

        private String duration;
        private String endStation;
        private String endTime;
        private String startStation;
        private String startTime;
        private String trainsetName;
        private List<TrainNoListBean> trainNoList;

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getEndStation() {
            return endStation;
        }

        public void setEndStation(String endStation) {
            this.endStation = endStation;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getStartStation() {
            return startStation;
        }

        public void setStartStation(String startStation) {
            this.startStation = startStation;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getTrainsetName() {
            return trainsetName;
        }

        public void setTrainsetName(String trainsetName) {
            this.trainsetName = trainsetName;
        }

        public List<TrainNoListBean> getTrainNoList() {
            return trainNoList;
        }

        public void setTrainNoList(List<TrainNoListBean> trainNoList) {
            this.trainNoList = trainNoList;
        }

        public static class TrainNoListBean implements Serializable {
            /**
             * date : 12:44:00~18:26:00
             * dateTimeFlag : G25
             * distance : 西安北~北京西
             * trainNo : G658
             */

            private String date;
            private String dateTimeFlag;
            private String distance;
            private String trainNo;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDateTimeFlag() {
                return dateTimeFlag;
            }

            public void setDateTimeFlag(String dateTimeFlag) {
                this.dateTimeFlag = dateTimeFlag;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getTrainNo() {
                return trainNo;
            }

            public void setTrainNo(String trainNo) {
                this.trainNo = trainNo;
            }
        }
    }
}
