package com.glhd.tb.app.http.res;

public class ResGetPi {


    /**
     * result : success
     * data : {"service_ip":"","service_port":""}
     */

    private int code;
    private String message = "";
    private ResponseBean data = new ResponseBean();

    public ResponseBean getData() {
        return data;
    }

    public void setData(ResponseBean data) {
        this.data = data;
    }

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






    public static class ResponseBean {
        /**
         * service_ip :
         * service_port :
         */

        private String service_ip = "";
        private String service_port = "";
        private String errorCode = "";
        private String errorText = "";
        private String service_projectname = "";

        public String getService_projectname() {
            return service_projectname;
        }

        public void setService_projectname(String service_projectname) {
            this.service_projectname = service_projectname;
        }

        public String getService_ip() {
            return service_ip;
        }

        public void setService_ip(String service_ip) {
            this.service_ip = service_ip;
        }

        public String getService_port() {
            return service_port;
        }

        public void setService_port(String service_port) {
            this.service_port = service_port;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorText() {
            return errorText;
        }

        public void setErrorText(String errorText) {
            this.errorText = errorText;
        }
    }
}
