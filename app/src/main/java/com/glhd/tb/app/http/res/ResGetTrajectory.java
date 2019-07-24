package com.glhd.tb.app.http.res;

import java.util.ArrayList;
import java.util.List;

public class ResGetTrajectory {

    /**
     * data : [{"name":"","stationId":"","florid":"","locationId":"","regionId":"","marshallingId":"","trainType":""}]
     * code : 0
     * message : 获取成功
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
         * name :
         * stationId :
         * florid :
         * locationId :
         * regionId :
         * marshallingId :
         * trainType :
         */

        private String name="";
        private String stationId="";
        private String floorId="";
        private String locationId="";
        private String regionId="";
        private String marshallingId="";
        private String trainType="";

        private String stationName="";
        private String floridName="";
        private String locationName="";
        private String regionName="";
        private String marshallingName="";
        private String trainTypeName="";
        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getFloridName() {
            return floridName;
        }

        public void setFloridName(String floridName) {
            this.floridName = floridName;
        }

        public String getLocationName() {
            return locationName;
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        public String getRegionName() {
            return regionName;
        }

        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }

        public String getMarshallingName() {
            return marshallingName;
        }

        public void setMarshallingName(String marshallingName) {
            this.marshallingName = marshallingName;
        }

        public String getTrainTypeName() {
            return trainTypeName;
        }

        public void setTrainTypeName(String trainTypeName) {
            this.trainTypeName = trainTypeName;
        }



        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStationId() {
            return stationId;
        }

        public void setStationId(String stationId) {
            this.stationId = stationId;
        }

        public String getFloorId() {
            return floorId;
        }

        public void setFloorId(String floorId) {
            this.floorId = floorId;
        }

        public String getLocationId() {
            return locationId;
        }

        public void setLocationId(String locationId) {
            this.locationId = locationId;
        }

        public String getRegionId() {
            return regionId;
        }

        public void setRegionId(String regionId) {
            this.regionId = regionId;
        }

        public String getMarshallingId() {
            return marshallingId;
        }

        public void setMarshallingId(String marshallingId) {
            this.marshallingId = marshallingId;
        }

        public String getTrainType() {
            return trainType;
        }

        public void setTrainType(String trainType) {
            this.trainType = trainType;
        }
    }
}
