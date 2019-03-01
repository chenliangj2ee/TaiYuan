package com.glhd.tb.app;

import android.util.Log;

import com.glhd.tb.app.base.BaseRes;
import com.glhd.tb.app.base.bean.BeanUser;
import com.glhd.tb.app.http.MyHttp;
import com.glhd.tb.app.http.MyHttpManager;
import com.glhd.tb.app.http.res.ResAdsConstruction;
import com.glhd.tb.app.http.res.ResConstructionNoti;
import com.glhd.tb.app.http.res.ResFuKuanList;
import com.glhd.tb.app.http.res.ResGetAdminIndexData;
import com.glhd.tb.app.http.res.ResGetAdminInspInfo;
import com.glhd.tb.app.http.res.ResGetContractInfo;
import com.glhd.tb.app.http.res.ResGetCusInspHistory;
import com.glhd.tb.app.http.res.ResGetCusSearchBaseData;
import com.glhd.tb.app.http.res.ResGetCusUpAdvertList;
import com.glhd.tb.app.http.res.ResGetCustommerContract;
import com.glhd.tb.app.http.res.ResGetInspBaseData;
import com.glhd.tb.app.http.res.ResGetInspHistory;
import com.glhd.tb.app.http.res.ResGetInspList;
import com.glhd.tb.app.http.res.ResGetInspSearchBaseData;
import com.glhd.tb.app.http.res.ResGetMyAdverts;
import com.glhd.tb.app.http.res.ResGetNoticeList;
import com.glhd.tb.app.http.res.ResGetPi;
import com.glhd.tb.app.http.res.ResGetRepair;
import com.glhd.tb.app.http.res.ResGetRepairList;
import com.glhd.tb.app.http.res.ResLogin;
import com.glhd.tb.app.http.res.ResSearchOne;
import com.glhd.tb.app.http.res.ResUpgrade;
import com.glhd.tb.app.http.res.ResUpload;
import com.glhd.tb.app.utils.MyLocation;

import java.io.File;


/**
 * Created by chenliangj2ee on 2018/4/10.
 */

public class API {

    //47.104.81.230:8901外网
    // 内网

    public static String MAIN_IP = "192.168.1.113:8080";

    public static String IP = "192.168.6.129:8080";
    public static String HOST_IMAGE = "http://" + IP + "/advertmanageapp";
    public static String HOST = "http://" + IP + "/advertmanageapp/admin/app";


    /**
     * 巡检接口
     */
    private static String URL_LOGIN = HOST + "/inspection/login";//登录
    private static String URL_RESET_PASSWORD = HOST + "/inspection/modifypwd";                      //修改密码
    private static String URL_GET_MY_INSP_LIST = HOST + "/inspection/list";                         //获取我的巡检
    private static String URL_INSP_FEEDBACK = HOST + "/inspection/feedback";                        //巡检反馈
    private static String URL_INSP_FEEDBACKBATCH = HOST + "/inspection/batch";                       //批量巡检
    private static String URL_INSP_UPLOAD = HOST + "/inspection/file";                              //上传图片
    private static String URL_SEARCH_ONE = HOST + "/inspection/lookup";                             //精准搜索
    private static String URL_GET_INSP_BASE_DATA_ = HOST + "/inspection/task";                      //获取基础数据
    private static String URL_GET_INSP_HISTORY = HOST + "/inspection/date";                         //获取巡检历史
    private static String URL_GET_SEARCH_BASE_DATA = HOST + "/inspection/info";                     //获取搜索基础数据
    private static String URL_UPDATE_USER_INFO = HOST + "/inspection/modifyuser";                   //获取搜索基础数据
    private static String URL_GET_CONSTRUCTION = HOST + "/inspection/publish";                      //获取施工/未完成，已完成
    private static String URL_CONSTRUCTION_SUBMIT = HOST + "/inspection/register";                   //施工登记
    private static String URL_GET_REPAIR = HOST + "/inspection/maintenance";                   //获取维修人员
    private static String URL_GET_REPAIR_LIST = HOST + "/administrators/press";                  //获取已维修，未维修
    private static String URL_INSP_REPAIRBACKBATCH = HOST + "/inspection/repairFeedback";                       //维修反馈
    /**
     * 客户端接口
     */
    private static String URL_GET_CONTRACT_LIST = HOST + "/client/InvestmentList";                  //客户合同列表
    private static String URL_GET_MY_ADVERTS = HOST + "/client/medialist";                          //我的媒体
    private static String URL_GET_CUS_SEARCH_DATA = HOST + "/client/group";                         //获取搜索基础信息
    private static String URL_GET_CUS_INSP_HISTORY = HOST + "/client/insplist";                     //获取巡检记录
    private static String URL_GET_CUS_UP_ADVERT = HOST + "/client/getPrePublicList";                //上刊列表
    private static String URL_GET_CUS_UP_ADVERTS_TONGJI = HOST + "/client/statistics";              //获取上刊统计信息
    private static String URL_GET_CONTRACT_INFO = HOST + "/client/InvestmentForm";                  //合同详情
    private static String URL_GET_NOTICE_LIST = HOST + "/client/getBulletinList";                   //公告列表
    private static String URL_FEEDBACK = HOST + "/client/addCSFeedback";                             //客户反馈
    private static String URL_FUKUAN_LIST = HOST + "/client/presslist";                             //催款信息查看


    /**
     * 管理端接口
     */
    private static String URL_GET_ADMIN_CONTRACT_LIST = HOST + "/administrators/InvestmentList";    //公司合同列表
    private static String URL_GET_ADMIN_INSP_HISTORY = HOST + "/administrators/getPrePublicList";   //上刊列表
    private static String URL_GET_ADMIN_ADVERTS = HOST + "/administrators/medialist";               //媒体资源列表
    private static String URL_GET_ADMIN_INDEX_DATA = HOST + "/administrators/home";                 //管理端首页数据
    private static String URL_GET_ADMIN_INSP_INFO = HOST + "/administrators/InspecStatis";          //巡检统计查询
    private static String URL_GET_ADMIN_CONTRACT_INFO = HOST + "/administrators/InvestmentForm";    //合同详情
    private static String URL_GET_ADMIN_ALL_STATIONS = HOST + "/administrators/stationAll";         //获取所有车站
    private static String URL_GET_UPGRADE = HOST + "/administrators/versionup";                     //升级检查
    private static String URL_ADMIN_SEARCH_ONE = HOST + "/administrators/lookup";                   //媒体资源精确查找
    private static String URL_ADMIN_FUKUAN_LIST = HOST + "/administrators/presslist";               //付款提醒列表
    private static String URL_ADMIN_CUI_KUAN = HOST + "/administrators/press";                  //催款

    public static void init(String ip, String port, String projectname) {
        API.IP = ip;
        if ("".equals(port) == false) {
            API.IP = API.IP + ":" + port;
        }
        API.IP = API.IP + "/" + projectname;

        API.HOST_IMAGE = "http://" + API.IP + "/app";
        API.HOST = "http://" + API.IP + "/app";
        /**
         * 巡检接口
         */
        API.URL_LOGIN = HOST + "/inspection/login";//登录
        API.URL_RESET_PASSWORD = HOST + "/inspection/modifypwd";                      //修改密码
        API.URL_GET_MY_INSP_LIST = HOST + "/inspection/list";                         //获取我的巡检
        API.URL_INSP_FEEDBACK = HOST + "/inspection/feedback";                        //巡检反馈
        API.URL_INSP_FEEDBACKBATCH = HOST + "/inspection/batch";                       //批量巡检
        API.URL_INSP_UPLOAD = HOST + "/inspection/file";                              //上传图片
        API.URL_SEARCH_ONE = HOST + "/inspection/lookup";                             //精准搜索
        API.URL_GET_INSP_BASE_DATA_ = HOST + "/inspection/task";                      //获取基础数据
        API.URL_GET_INSP_HISTORY = HOST + "/inspection/date";                         //获取巡检历史
        API.URL_GET_SEARCH_BASE_DATA = HOST + "/inspection/info";                     //获取搜索基础数据
        API.URL_UPDATE_USER_INFO = HOST + "/inspection/modifyuser";                   //获取搜索基础数据
        API.URL_GET_CONSTRUCTION = HOST + "/inspection/publish";                   //获取施工/未完成，已完成
        API.URL_CONSTRUCTION_SUBMIT = HOST + "/inspection/register";                   //施工登记
        API.URL_GET_REPAIR = HOST + "/inspection/maintenance";                   //获取维修人员
        API.URL_GET_REPAIR_LIST = HOST + "/inspection/repairlist";                   //获取已维修、未维修
        API.URL_INSP_REPAIRBACKBATCH=HOST+"/inspection/repairFeedback";                //维修反馈

        /**
         * 客户端接口;
         */
        API.URL_GET_CONTRACT_LIST = HOST + "/client/InvestmentList";                  //客户合同列表
        API.URL_GET_MY_ADVERTS = HOST + "/client/medialist";                          //我的媒体
        API.URL_GET_CUS_SEARCH_DATA = HOST + "/client/group";                         //获取搜索基础信息
        API.URL_GET_CUS_INSP_HISTORY = HOST + "/client/insplist";                     //获取巡检记录
        API.URL_GET_CUS_UP_ADVERT = HOST + "/client/getPrePublicList";                //上刊列表
        API.URL_GET_CUS_UP_ADVERTS_TONGJI = HOST + "/client/statistics";              //获取上刊统计信息
        API.URL_GET_CONTRACT_INFO = HOST + "/client/InvestmentForm";                  //合同详情
        API.URL_GET_NOTICE_LIST = HOST + "/client/getBulletinList";                   //公告列表
        API.URL_FEEDBACK = HOST + "/client/addCSFeedback";                             //客户反馈
        API.URL_FUKUAN_LIST = HOST + "/client/presslist";                             //催款信息查看

        /**
         * 管理端接口
         */
        API.URL_GET_ADMIN_CONTRACT_LIST = HOST + "/administrators/InvestmentList";    //公司合同列表
        API.URL_GET_ADMIN_INSP_HISTORY = HOST + "/administrators/getPrePublicList";   //上刊列表
        API.URL_GET_ADMIN_ADVERTS = HOST + "/administrators/medialist";               //媒体资源列表
        API.URL_GET_ADMIN_INDEX_DATA = HOST + "/administrators/home";                 //管理端首页数据
        API.URL_GET_ADMIN_INSP_INFO = HOST + "/administrators/InspecStatis";          //巡检统计查询
        API.URL_GET_ADMIN_CONTRACT_INFO = HOST + "/administrators/InvestmentForm";    //合同详情
        API.URL_GET_ADMIN_ALL_STATIONS = HOST + "/administrators/stationAll";         //获取所有车站
        API.URL_GET_UPGRADE = HOST + "/administrators/versionup";                     //升级检查
        API.URL_ADMIN_SEARCH_ONE = HOST + "/administrators/lookup";                   //媒体资源精确查找
        API.URL_ADMIN_FUKUAN_LIST = HOST + "/administrators/presslist";               //付款提醒列表
        API.URL_ADMIN_CUI_KUAN = HOST + "/administrators/press";                  //催款
    }

    /**
     * 登录
     *
     * @param account账号
     * @param password
     */
    public static void getIp(String loginname, String password, String phone, MyHttp.ResultCallback<ResGetPi>... callback) {
        MyHttp<ResGetPi> http = new MyHttp<>(ResGetPi.class);
        http.put("loginname", loginname);
        http.put("password", password);
        http.put("phone", phone);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post("http://" + MAIN_IP + "/advertservice/admin/datasync/getServiceInfo");
    }

    /**
     * 登录
     *
     * @param account账号
     * @param password
     */
    public static void login(String account, String password,
                             MyHttp.ResultCallback<ResLogin>... callback) {
        MyHttp<ResLogin> http = new MyHttp<>(ResLogin.class);
        http.put("account", account);
        http.put("password", password);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_LOGIN);
        Log.i("API", API.URL_LOGIN);
    }

    /**
     * 修改密码
     *
     * @param accountId：账号id
     * @param oldPassword：原始密码
     * @param newPassword：新密码
     * @param callback
     */
    public static void resetPassword(String accountId, String oldPassword, String newPassword,
                                     MyHttp.ResultCallback<BaseRes>... callback) {
        MyHttp<BaseRes> http = new MyHttp<>(BaseRes.class);
        http.put("accountId", accountId);
        http.put("oldPassword", oldPassword);
        http.put("newPassword", newPassword);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_RESET_PASSWORD);
    }

    /**
     * 登录
     *
     * @param accountId 账号
     * @param password
     */
    public static void getContractList(String accountId, String pageNum, String pageSize,
                                       MyHttp.ResultCallback<ResGetCustommerContract>... callback) {
        MyHttp<ResGetCustommerContract> http = new MyHttp<>(ResGetCustommerContract.class);
        http.put("accountId", accountId);
        http.put("pageNo", pageNum);
        http.put("pageSize", pageSize);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_GET_CONTRACT_LIST);


    }

    /**
     * 登录
     *
     * @param account账号
     * @param password
     */
    public static void getAdminContractList(String accountId, String pageNum, String pageSize,
                                            MyHttp.ResultCallback<ResGetCustommerContract>... callback) {
        MyHttp<ResGetCustommerContract> http = new MyHttp<>(ResGetCustommerContract.class);
        http.put("accountId", accountId);
        http.put("pageNo", pageNum);
        http.put("pageSize", pageSize);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_GET_ADMIN_CONTRACT_LIST);


    }

    /**
     * @param accountId:账号
     * @param inspStatus：0：未巡检，1巡检
     * @param pageNum：页数
     * @param pageSize：每页数
     * @param callback
     */
    public static void getMyInspList(String accountId, String inspStatus, String pageNum, String pageSize,
                                     String stationId,  String locationId, String carnoId, String marshallingId,
                                     MyHttp.ResultCallback<ResGetInspList>... callback) {

        MyHttp<ResGetInspList> http = new MyHttp<>(ResGetInspList.class);
        http.put("accountId", accountId);
        http.put("inspStatus", inspStatus);
        http.put("pageNo", pageNum);
        http.put("pageSize", pageSize);

        http.put("stationId", stationId);
        http.put("locationId", locationId);
        http.put("carnoId", carnoId);
        http.put("marshallingId", marshallingId);

        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_GET_MY_INSP_LIST);
    }


    /**
     * 巡检反馈
     *
     * @param id
     * @param remarks
     * @param status
     * @param callback
     */
    public static void inspFeedback(String accountId, String id, String fileName, String remarks, String status,String repairPersonnel,String viewStaff,
                                    MyHttp.ResultCallback<BaseRes>... callback) {
        MyHttp<BaseRes> http = new MyHttp<>(BaseRes.class);
        http.put("id", id);
        http.put("accountId", accountId);
        http.put("remarks", remarks);
        http.put("status", status);
        http.put("fileName", fileName);


        http.put("repairPersonnel", repairPersonnel);
        http.put("viewStaff", viewStaff);
        http.put("location", MyLocation.latitude+","+MyLocation.longitude);

        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_INSP_FEEDBACK);
    }

    /*
     *
     * 批量巡检
     * */
    public static void inspFeedbackBatch(String accountId, String ids, String fileName, String remarks, String status,MyHttp.ResultCallback<BaseRes>... callback) {
        MyHttp<BaseRes> http = new MyHttp<>(BaseRes.class);
        http.put("accountId", accountId);
        http.put("ids", ids);
        http.put("remarks", remarks);
        http.put("status", status);
        http.put("fileName", fileName);
        http.put("location", MyLocation.latitude+","+MyLocation.longitude);

        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_INSP_FEEDBACKBATCH);
    }

    /*
     *
     * 获取维修人员，通知人员
     * */
    public static void getRepair(MyHttp.ResultCallback<ResGetRepair>... callback) {
        MyHttp<ResGetRepair> http = new MyHttp<>(ResGetRepair.class);
        http.put("fileName", "ddd");
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_GET_REPAIR);
    }


    /**
     * 巡检反馈
     *
     * @param id
     * @param remarks
     * @param status
     * @param callback
     */
    public static void upload(File file,
                              MyHttp.ResultCallback<ResUpload> callback, MyHttpManager.Param... params) {
        MyHttp<ResUpload> http = new MyHttp<>(ResUpload.class);
        if (callback != null)
            http.ResultCallback(callback);
        http.upload(API.URL_INSP_UPLOAD, ResUpload.class, file, "fileKey", params);

    }


    /**
     * 精准查找(巡检端)
     *
     * @param accountId
     * @param coding
     * @param callback
     */
    public static void getSearchOne(String accountId, String coding,
                                    MyHttp.ResultCallback<ResSearchOne>... callback) {

        MyHttp<ResSearchOne> http = new MyHttp<>(ResSearchOne.class);
        http.put("accountId", accountId);
        http.put("coding", coding);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_SEARCH_ONE);
    }

    /**
     * 精准查找(管理端)
     *
     * @param accountId
     * @param coding
     * @param callback
     */
    public static void getAdminSearchOne(String accountId, String coding,
                                         MyHttp.ResultCallback<ResSearchOne>... callback) {

        MyHttp<ResSearchOne> http = new MyHttp<>(ResSearchOne.class);
        http.put("accountId", accountId);
        http.put("coding", coding);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_ADMIN_SEARCH_ONE);
    }

    /**
     * 6.	获取账户基础数据
     *
     * @param accountId
     * @param callback
     */
    public static void getInspBaseData(String accountId, MyHttp.ResultCallback<ResGetInspBaseData>... callback) {

        MyHttp<ResGetInspBaseData> http = new MyHttp<>(ResGetInspBaseData.class);
        http.put("accountId", accountId);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_GET_INSP_BASE_DATA_);
    }

    /**
     * @param accountId
     * @param pageNum
     * @param pageSize
     * @param stationId
     * @param typeId
     * @param locationId
     * @param startDate
     * @param endDate
     * @param callback
     */
    public static void getMyInspHistory(String accountId,
                                        String pageNum,
                                        String pageSize,
                                        String stationId,
                                        String typeId,
                                        String locationId,
                                        String startDate,
                                        String endDate,
                                        String carnoId,
                                        String marshallingId,
                                        MyHttp.ResultCallback<ResGetInspHistory>... callback) {

        MyHttp<ResGetInspHistory> http = new MyHttp<>(ResGetInspHistory.class);
        http.put("accountId", accountId);
        http.put("pageNo", pageNum);
        http.put("pageSize", pageSize);
        http.put("typeId", typeId);
        http.put("stationId", stationId);
        http.put("locationId", locationId);
        http.put("carnoId", carnoId);
        http.put("marshallingId", marshallingId);

        http.put("startDate", startDate);
        http.put("endDate", endDate);

        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_GET_INSP_HISTORY);
    }


    public static void getInspSearchBaseData(String accountId, String dataType,
                                             MyHttp.ResultCallback<ResGetInspSearchBaseData>... callback) {

        MyHttp<ResGetInspSearchBaseData> http = new MyHttp<>(ResGetInspSearchBaseData.class);
        http.put("accountId", accountId);
        http.put("dataType", dataType);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_GET_SEARCH_BASE_DATA);
    }

    public static void updateUserInfo(BeanUser user, MyHttp.ResultCallback<BaseRes>... callback) {

        MyHttp<BaseRes> http = new MyHttp<>(BaseRes.class);
        http.put("accountId", user.getAccountId());
        http.put("phone", user.getPhone());
        http.put("remarks", user.getRemarks());
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_UPDATE_USER_INFO);
    }


    /**
     * 意见反馈
     *
     * @param accountId
     * @param title
     * @param content
     * @param callback
     */
    public static void feedback(String accountId, String title, String content,
                                MyHttp.ResultCallback<BaseRes>... callback) {

        MyHttp<BaseRes> http = new MyHttp<>(BaseRes.class);
        http.put("accountId", accountId);
        http.put("title", title);
        http.put("content", content);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_FEEDBACK);
    }


    /**
     * 我的媒体
     *
     * @param accountId
     * @param title
     * @param content
     * @param callback
     */
    public static void myAdverts(String accountId, String stationId,
                                 String typeId, String locationId,
                                 String directionId, String pageNum,
                                 String pageSize, MyHttp.ResultCallback<ResGetMyAdverts>... callback) {


        MyHttp<ResGetMyAdverts> http = new MyHttp<>(ResGetMyAdverts.class);
        http.put("accountId", accountId);
        http.put("stationId", stationId);
        http.put("typeId", typeId);
        http.put("locationId", locationId);
        http.put("directionId", directionId);
        http.put("pageNo", pageNum);
        http.put("pageSize", pageSize);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_GET_MY_ADVERTS);
    }

    /**
     * 获取客户端查询过滤基础数据
     *
     * @param accountId
     * @param title
     * @param content
     * @param callback
     */
    public static void getCusSearchBaseData(String accountId, MyHttp.ResultCallback<ResGetCusSearchBaseData>... callback) {


        MyHttp<ResGetCusSearchBaseData> http = new MyHttp<>(ResGetCusSearchBaseData.class);
        http.put("accountId", accountId);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_GET_CUS_SEARCH_DATA);
    }

    /**
     * 获取客户端巡检记录
     *
     * @param accountId
     * @param title
     * @param content
     * @param callback
     */
    public static void getCusInspHistory(String accountId, String pageNum, String pageSize, MyHttp.ResultCallback<ResGetCusInspHistory>... callback) {


        MyHttp<ResGetCusInspHistory> http = new MyHttp<>(ResGetCusInspHistory.class);
        http.put("accountId", accountId);
        http.put("pageNo", pageNum);
        http.put("pageSize", pageSize);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_GET_CUS_INSP_HISTORY);
    }

    /**
     * 获取客户端巡检记录
     *
     * @param accountId
     * @param title
     * @param content
     * @param callback
     */
    public static void getAdminAllStations(String accountId, MyHttp.ResultCallback<ResGetCusSearchBaseData>... callback) {


        MyHttp<ResGetCusSearchBaseData> http = new MyHttp<>(ResGetCusSearchBaseData.class);
        http.put("accountId", accountId);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_GET_ADMIN_ALL_STATIONS);
    }

    /**
     * 获取客户端巡检记录
     *
     * @param accountId
     * @param title
     * @param content
     * @param callback
     */
    public static void getCusUpAdvertList(String accountId, String pageNum, String pageSize,
                                          MyHttp.ResultCallback<ResGetCusUpAdvertList>... callback) {


        MyHttp<ResGetCusUpAdvertList> http = new MyHttp<>(ResGetCusUpAdvertList.class);
        http.put("accountId", accountId);
        http.put("pageNo", pageNum);
        http.put("pageSize", pageSize);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_GET_CUS_UP_ADVERT);
    }

    /**
     * 4.	获取上刊统计信息
     *
     * @param accountId
     * @param title
     * @param content
     * @param callback
     */
    public static void getCusUpAdvertTJ(String accountId, MyHttp.ResultCallback<BaseRes>... callback) {


        MyHttp<BaseRes> http = new MyHttp<>(BaseRes.class);
        http.put("accountId", accountId);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_GET_CUS_UP_ADVERTS_TONGJI);
    }

    /**
     * 4.	客户端获取合同详情
     *
     * @param accountId
     * @param title
     * @param content
     * @param callback
     */
    public static void getCusContractInfo(String contractId, MyHttp.ResultCallback<ResGetContractInfo>... callback) {


        MyHttp<ResGetContractInfo> http = new MyHttp<>(ResGetContractInfo.class);
        http.put("id", contractId);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_GET_CONTRACT_INFO);
    }

    /**
     * 4.	管理端获取合同详情
     *
     * @param accountId
     * @param title
     * @param content
     * @param callback
     */
    public static void getAdminContractInfo(String contractId, MyHttp.ResultCallback<ResGetContractInfo>... callback) {


        MyHttp<ResGetContractInfo> http = new MyHttp<>(ResGetContractInfo.class);
        http.put("id", contractId);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_GET_ADMIN_CONTRACT_INFO);
    }

    /**
     * 4.	获取上刊统计信息
     *
     * @param accountId
     * @param title
     * @param content
     * @param callback
     */
    public static void getNoticeList(String contractId, String pageNum, String pageSize,
                                     MyHttp.ResultCallback<ResGetNoticeList>... callback) {


        MyHttp<ResGetNoticeList> http = new MyHttp<>(ResGetNoticeList.class);
        http.put("id", contractId);
        http.put("pageNo", pageNum);
        http.put("pageSize", pageSize);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_GET_NOTICE_LIST);
    }

    /**
     * 管理端获得媒体列表
     *
     * @param accountId
     * @param title
     * @param content
     * @param callback
     */
    public static void adminAdverts(String accountId, String stationId,
                                    String typeId, String locationId,
                                    String directionId, String pageNum,
                                    String pageSize, MyHttp.ResultCallback<ResGetMyAdverts>... callback) {


        MyHttp<ResGetMyAdverts> http = new MyHttp<>(ResGetMyAdverts.class);
        http.put("accountId", accountId);
        http.put("stationId", stationId);
        http.put("typeId", typeId);
        http.put("locationId", locationId);
        http.put("directionId", directionId);
        http.put("pageNo", pageNum);
        http.put("pageSize", pageSize);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_GET_ADMIN_ADVERTS);
    }


    /**
     * 获取管理员上刊提醒列表
     *
     * @param accountId
     * @param title
     * @param content
     * @param callback
     */
    public static void getAdminUpAdvertList(String accountId, String pageNum, String pageSize,
                                            MyHttp.ResultCallback<ResConstructionNoti>... callback) {


        MyHttp<ResConstructionNoti> http = new MyHttp<>(ResConstructionNoti.class);
        http.put("accountId", accountId);
        http.put("pageNo", pageNum);
        http.put("pageSize", pageSize);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_GET_ADMIN_INSP_HISTORY);
    }

    /**
     * 获取管理端首页数据
     *
     * @param accountId
     * @param title
     * @param content
     * @param callback
     */
    public static MyHttp<ResGetAdminIndexData> getAdminIndexData(String accountId,
                                                                 MyHttp.ResultCallback<ResGetAdminIndexData>... callback) {


        MyHttp<ResGetAdminIndexData> http = new MyHttp<>(ResGetAdminIndexData.class);
        http.put("accountId", accountId);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_GET_ADMIN_INDEX_DATA);
        return http;
    }

    /**
     * 获取管理端首页数据
     *
     * @param accountId
     * @param title
     * @param content
     * @param callback
     */
    public static void getAdminInspInfo(String accountId, String date, String stationId,
                                        MyHttp.ResultCallback<ResGetAdminInspInfo>... callback) {


        MyHttp<ResGetAdminInspInfo> http = new MyHttp<>(ResGetAdminInspInfo.class);
        http.put("accountId", accountId);
        http.put("date", date);
        http.put("stationId", stationId);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_GET_ADMIN_INSP_INFO);
    }


    /**
     * 检测版本升级
     *
     * @param versionCode:版本号
     * @param callback
     */
    public static void upgrade(String versionCode, MyHttp.ResultCallback<ResUpgrade>... callback) {

        MyHttp<ResUpgrade> http = new MyHttp<>(ResUpgrade.class);
        http.put("device", "android");
        http.put("versionCode", versionCode);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_GET_UPGRADE);
    }


    /**
     * 获取上刊施工》未完成，已完成
     *
     * @param versionCode:版本号
     * @param callback
     */
    public static void getCunstructionByType(String accountId, String type, int pageNo, int pageSize, MyHttp.ResultCallback<ResAdsConstruction>... callback) {

        MyHttp<ResAdsConstruction> http = new MyHttp<>(ResAdsConstruction.class);
        http.put("accountId", accountId);
        http.put("type", type);
        http.put("pageNo", pageNo + "");
        http.put("pageSize", pageSize + "");
        http.put("location", MyLocation.latitude+","+MyLocation.longitude);
        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_GET_CONSTRUCTION);
    }

    /**
     * 施工登记
     *
     * @param versionCode:版本号
     * @param callback
     */
    public static void cunstructionSubmit(String id,
                                          String accountId,
                                          String resourceid,
                                          String status,
                                          String remarks,
                                          String imageUrl,
                                          String date,
                                          MyHttp.ResultCallback<BaseRes>... callback) {

        MyHttp<BaseRes> http = new MyHttp<>(BaseRes.class);
        http.put("id", id);
        http.put("accountId", accountId);
        http.put("resourceid", resourceid);
        http.put("status", status);
        http.put("remarks", remarks);
        http.put("imageUrl", imageUrl);
        http.put("date", date);
        http.put("location", MyLocation.latitude+","+MyLocation.longitude);

        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_CONSTRUCTION_SUBMIT);
    }

    /**
     * 客户端付款提醒
     *
     * @param versionCode:版本号
     * @param callback
     */
    public static void getFuKuanList(
            String accountId,
            String pageNo,
            String pageSize,
            MyHttp.ResultCallback<ResFuKuanList>... callback) {

        MyHttp<ResFuKuanList> http = new MyHttp<>(ResFuKuanList.class);
        http.put("accountId", accountId);
        http.put("pageNo", pageNo);
        http.put("pageSize", pageSize);


        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_FUKUAN_LIST);
    }

    /**
     * 管理端付款提醒
     *
     * @param versionCode:版本号
     * @param callback
     */
    public static void getAdminFuKuanList(
            String accountId,
            String pageNo,
            String pageSize,
            MyHttp.ResultCallback<ResFuKuanList>... callback) {

        MyHttp<ResFuKuanList> http = new MyHttp<>(ResFuKuanList.class);
        http.put("accountId", accountId);
        http.put("pageNo", pageNo);
        http.put("pageSize", pageSize);


        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_ADMIN_FUKUAN_LIST);
    }

    /**
     * 管理端付款提醒
     *
     * @param versionCode:版本号
     * @param callback
     */
    public static void getAdminCuikuan(
            String accountId,
            String planIds,
            MyHttp.ResultCallback<BaseRes>... callback) {

        MyHttp<BaseRes> http = new MyHttp<>(BaseRes.class);
        http.put("accountId", accountId);
        http.put("planIds", planIds);


        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_ADMIN_CUI_KUAN);
    }

    /**
     * 管理端付款提醒
     *
     * @param versionCode:版本号
     * @param callback
     */
    public static void getRepariList(
            String accountId,
            String repairState,
            String pageNo,
            String pageSize,
            MyHttp.ResultCallback<ResGetRepairList>... callback) {

        MyHttp<ResGetRepairList> http = new MyHttp<>(ResGetRepairList.class);
        http.put("accountId", accountId);
        http.put("repairState", repairState);
        http.put("stationId", "");
        http.put("locationId", "");
        http.put("carnoId", "");
        http.put("marshallingId", "");
        http.put("multiple", "");
        http.put("pageNo", pageNo);
        http.put("pageSize", pageSize);



        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_GET_REPAIR_LIST);
    }



    /*
     *
     * 批量巡检
     * */
    public static void repairFeedbackBatch(String accountId, String taskId, String fileName, String remarks,MyHttp.ResultCallback<BaseRes>... callback) {
        MyHttp<BaseRes> http = new MyHttp<>(BaseRes.class);
        http.put("accountId", accountId);
        http.put("taskId", taskId);
        http.put("remarks", remarks);
        http.put("location", MyLocation.latitude+","+MyLocation.longitude);
        http.put("fileName", fileName);

        if (callback.length > 0)
            http.ResultCallback(callback[0]);
        http.post(API.URL_INSP_REPAIRBACKBATCH);
    }

}
