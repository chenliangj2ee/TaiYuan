package com.glhd.tb.app.base.bean;

import java.io.Serializable;


/**
 * Created by chenliangj2ee on 2018/5/3.
 */

public class BeanRepair implements Serializable {

    /**
     * coding : T.AST.DXG.00034
     * id : 10e5117f27064d97acbc4f312f713771
     * image : http://192.168.6.132:8080///advertmanageapp//upload//media_material//
     * inspName : 张三
     * location : 地下通道
     * locationdescribe : 鞍山站旅客地下通道LED灯箱
     * mediaImage : http://192.168.6.132:8080///advertmanageapp//upload//media_material//
     * properystation : 鞍山站
     * remark : 123
     * repairId : 3
     * taskImage : http://192.168.6.132:8080///advertmanageapp//upload//inspection//e1c3400a0c154381a52421bc3281a169.jpg,http://192.168.6.132:8080///advertmanageapp//upload//inspection//54ddd5ee8e33470c938d92b94a2ae773.jpg
     * whSize : 1.20*3.60*1.00
     */

    private String coding;
    private String id;
    private String image;
    private String inspName;
    private String location;
    private String locationdescribe;
    private String mediaImage;
    private String properystation;
    private String remark;
    private String repairId;
    private String taskImage;
    private String whSize;
    private String repairImage;

    public String getRepairImage() {
        return repairImage;
    }

    public void setRepairImage(String repairImage) {
        this.repairImage = repairImage;
    }

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInspName() {
        return inspName;
    }

    public void setInspName(String inspName) {
        this.inspName = inspName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationdescribe() {
        return locationdescribe;
    }

    public void setLocationdescribe(String locationdescribe) {
        this.locationdescribe = locationdescribe;
    }

    public String getMediaImage() {
        return mediaImage;
    }

    public void setMediaImage(String mediaImage) {
        this.mediaImage = mediaImage;
    }

    public String getProperystation() {
        return properystation;
    }

    public void setProperystation(String properystation) {
        this.properystation = properystation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    public String getTaskImage() {
        return taskImage;
    }

    public void setTaskImage(String taskImage) {
        this.taskImage = taskImage;
    }

    public String getWhSize() {
        return whSize;
    }

    public void setWhSize(String whSize) {
        this.whSize = whSize;
    }
}
