package com.glhd.tb.app.http.res;

import com.glhd.tb.app.base.BaseRes;

public class ResUpgrade extends BaseRes {

    private String upgrade = "";
    private String url = "";

    public String getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(String upgrade) {
        this.upgrade = upgrade;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
