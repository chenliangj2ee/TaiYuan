package com.glhd.tb.app.http.res;

import java.util.ArrayList;

public class ResGetRepair {


    /**
     * code : 1
     * message :
     * data : {"repairPersonnel":[{"id":"1","name":"张三（业务部）"},{"id":"2","name":"李四（业务部）"}],"viewStaff":[{"id":"1","name":"XX局","subData":[{"id":"1","name":"XX部门","subData":[{"id":"1","name":"张三"},{"id":"2","name":"李四"}]},{"id":"2","name":"XX部门","subData":[{"id":"1","name":"张三"},{"id":"2","name":"李四"}]}]},{"id":"2","name":"XX局","subData":[{"id":"1","name":"XX部门","subData":[{"id":"1","name":"张三"},{"id":"2","name":"李四"}]},{"id":"2","name":"XX部门","subData":[{"id":"1","name":"张三"},{"id":"2","name":"李四"}]}]}]}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private ArrayList<RepairPersonnelBean> repairPersonnel;
        private ArrayList<ViewStaffBean> viewStaff;

        public ArrayList<RepairPersonnelBean> getRepairPersonnel() {
            return repairPersonnel;
        }

        public void setRepairPersonnel(ArrayList<RepairPersonnelBean> repairPersonnel) {
            this.repairPersonnel = repairPersonnel;
        }

        public ArrayList<ViewStaffBean> getViewStaff() {
            return viewStaff;
        }

        public void setViewStaff(ArrayList<ViewStaffBean> viewStaff) {
            this.viewStaff = viewStaff;
        }

        public static class RepairPersonnelBean {


            public ArrayList<RepairPersonnelBean> users=new ArrayList<>();

            /**
             * id : 1
             * name : 张三（业务部）
             */

            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class ViewStaffBean {
            /**
             * id : 1
             * name : XX局
             * subData : [{"id":"1","name":"XX部门","subData":[{"id":"1","name":"张三"},{"id":"2","name":"李四"}]},{"id":"2","name":"XX部门","subData":[{"id":"1","name":"张三"},{"id":"2","name":"李四"}]}]
             */

            private String id;
            private String name;
            private boolean isCheck;
            private ArrayList<ViewStaffBean> subData;

            public String getId() {
                return id;
            }

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public ArrayList<ViewStaffBean> getSubData() {
                return subData;
            }

            public void setSubData(ArrayList<ViewStaffBean> subData) {
                this.subData = subData;
            }


        }
    }
}
