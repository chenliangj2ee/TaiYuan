package com.glhd.tb.app.http.res;

import java.util.ArrayList;
import java.util.List;

public class GetInspRange {


    /**
     * code : 0
     * data : [{"id":"","title":"全部"},{"id":"15194","title":"武汉站"}]
     * message : 获取成功
     */

    private int code;
    private String message;
    private ArrayList<DataBean> data=new ArrayList<>();

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

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id :
         * title : 全部
         */

        private String id;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
