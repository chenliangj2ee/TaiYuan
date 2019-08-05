package com.glhd.tb.app.base.bean;

import java.io.Serializable;

/**
 * Created by chenliangj2ee on 2018/5/3.
 */

public class BeanUser implements Serializable {


    /**
     * accountId : 8728862
     * type : 0
     * name : 张三
     * phone : 13090988766
     * company : 西安XXX有限公司
     * remarks :
     */

    private String accountId = "";
    private String account = "";
    private String type = "";
    private String name = "";
    private String phone = "";
    private String loginPhone = "";
    private String company = "";
    private String remarks = "";
    private boolean isLogin;
    private String curType;
    private int inspNum = 0;
    private int repairNum = 0;

    public String getCurType() {
        return curType;
    }

    public int getInspNum() {
        return inspNum;
    }

    public void setInspNum(int inspNum) {
        this.inspNum = inspNum;
    }

    public int getRepairNum() {
        return repairNum;
    }

    public void setRepairNum(int repairNum) {
        this.repairNum = repairNum;
    }

    public void setCurType(String curType) {
        this.curType = curType;
    }

    public String getLoginPhone() {
        return loginPhone;
    }

    public void setLoginPhone(String loginPhone) {
        this.loginPhone = loginPhone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
