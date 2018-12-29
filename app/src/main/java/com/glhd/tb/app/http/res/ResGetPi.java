package com.glhd.tb.app.http.res;

public class ResGetPi {


    /**
     * result : success
     * response : {"service_ip":"","service_port":""}
     */

    private String result = "";
    private ResponseBean response = new ResponseBean();

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
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
