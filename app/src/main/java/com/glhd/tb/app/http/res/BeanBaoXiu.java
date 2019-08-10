package com.glhd.tb.app.http.res;

public class BeanBaoXiu {
        /**
         * presentationDate : 2019-08-10 11:35:23
         * repairState : 0未处理 1处理
         * responsible : 维修负责人
         * totalNumber : 1
         * mediatype : LED0个/灯箱1个/吊旗0个/展板10个
         * station : 西安北站
         * trajectory : F1/候车室
         * repairDate : 2019-08-09 17:30:10
         */
    /**
     * ""station"":""所属车站/车底号"",
     *         ""totalNumber"":""故障总数"",
     *         ""presentationDate"":""报修时间"",
     *         ""trajectory"":""车站轨迹/车辆段"",
     *         ""mediatype"":""故障媒体类型"",
     *         ""responsibleUser"":""维修负责人"",
     *         ""repairState"":""维修状态"",
     *         ""repairDate"":""维修时间""
     */

    private String presentationDate;
        private String repairState;
        private String responsible;
        private int totalNumber;
        private String mediatype;
        private String station;
        private String trajectory;
        private String repairDate;

        public String getPresentationDate() {
            return presentationDate;
        }

        public void setPresentationDate(String presentationDate) {
            this.presentationDate = presentationDate;
        }

        public String getRepairState() {
            return repairState;
        }

        public void setRepairState(String repairState) {
            this.repairState = repairState;
        }

        public String getResponsible() {
            return responsible;
        }

        public void setResponsible(String responsible) {
            this.responsible = responsible;
        }

        public int getTotalNumber() {
            return totalNumber;
        }

        public void setTotalNumber(int totalNumber) {
            this.totalNumber = totalNumber;
        }

        public String getMediatype() {
            return mediatype;
        }

        public void setMediatype(String mediatype) {
            this.mediatype = mediatype;
        }

        public String getStation() {
            return station;
        }

        public void setStation(String station) {
            this.station = station;
        }

        public String getTrajectory() {
            return trajectory;
        }

        public void setTrajectory(String trajectory) {
            this.trajectory = trajectory;
        }

        public String getRepairDate() {
            return repairDate;
        }

        public void setRepairDate(String repairDate) {
            this.repairDate = repairDate;
        }
}
