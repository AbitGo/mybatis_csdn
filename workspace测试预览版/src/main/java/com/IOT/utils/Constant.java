/**
 * File Name: com.huawei.utils.Constant.java
 *
 * Copyright Notice:
 *      Copyright  1998-2008, Huawei Technologies Co., Ltd.  ALL Rights Reserved.
 *
 *      Warning: This computer software sourcecode is protected by copyright law
 *      and international treaties. Unauthorized reproduction or distribution
 *      of this sourcecode, or any portion of it, may result in severe civil and
 *      criminal penalties, and will be prosecuted to the maximum extent
 *      possible under the law.
 */
package com.IOT.utils;

public class Constant {

    //please replace the IP and Port of the IoT platform environment address, when you use the demo.
    public static final String BASE_URL = "https://180.101.147.89:8743";

    //修改以下两个参数，由电信平台提供.
    public static final String APPID = "xxxx";
    public static final String SECRET = "xxxx";
    //暂时储存的数据
    public static String accessToken = "accessToken";
    /*
     *IP and port of callback url.
     *请将这个url改成你的云服务器网址
     */
    public static final String CALLBACK_BASE_URL = "http://1.1.1.1:8181";

    /*
     * complete callback url.
     * please replace uri, when you use the demo.
     */
    public static final String DEVICE_ADDED_CALLBACK_URL = CALLBACK_BASE_URL + "/IoT/addDevice";
    public static final String DEVICE_INFO_CHANGED_CALLBACK_URL = CALLBACK_BASE_URL + "/IoT/updateDeviceInfo";
    public static final String DEVICE_DATA_CHANGED_CALLBACK_URL = CALLBACK_BASE_URL + "/IoT/updateDeviceData";
    public static final String DEVICE_DELETED_CALLBACK_URL = CALLBACK_BASE_URL + "/IoT/deletedDevice";
    public static final String MESSAGE_CONFIRM_CALLBACK_URL = CALLBACK_BASE_URL + "/IoT/commandConfirmData";
    public static final String SERVICE_INFO_CHANGED_CALLBACK_URL = CALLBACK_BASE_URL + "/IoT/updateServiceInfo";
    public static final String COMMAND_RSP_CALLBACK_URL = CALLBACK_BASE_URL + "/IoT/commandRspData";
    public static final String DEVICE_EVENT_CALLBACK_URL = CALLBACK_BASE_URL + "/IoT/DeviceEvent";
    public static final String RULE_EVENT_CALLBACK_URL = CALLBACK_BASE_URL + "/IoT/RulEevent";
    public static final String DEVICE_DATAS_CHANGED_CALLBACK_URL = CALLBACK_BASE_URL + "/IoT/updateDeviceDatas";


    /*
     * Specifies the callback URL for the command execution result notification.
     * For details about the execution result notification definition.
     *
     * please replace uri, when you use the demo.
     */
    public static final String REPORT_CMD_EXEC_RESULT_CALLBACK_URL = CALLBACK_BASE_URL + "/na/iocm/devNotify/v1.1.0/reportCmdExecResult";

//    //本地环境
//    public static String SELFCERTPATH = "/src/resource/cert/outgoing.CertwithKey.pkcs12";
//    public static String TRUSTCAPATH = "/src/resource/cert/ca.jks";

    //正式环境
    public static String SELFCERTPATH = "/cert/outgoing.CertwithKey.pkcs12";
    public static String TRUSTCAPATH = "/cert/ca.jks";

    //Password of certificates.
    public static String SELFCERTPWD = "IoM@1234";
    public static String TRUSTCAPWD = "Huawei@123";


    //*************************** The following constants do not need to be modified *********************************//

    /*
     * request header
     * 1. HEADER_APP_KEY
     * 2. HEADER_APP_AUTH
     */
    public static final String HEADER_APP_KEY = "app_key";
    public static final String HEADER_APP_AUTH = "Authorization";
    
    /*
     * Application Access Security:
     * 1. APP_AUTH
     * 2. REFRESH_TOKEN
     */
    public static final String APP_AUTH = BASE_URL + "/iocm/app/sec/v1.1.0/login";
    public static final String REFRESH_TOKEN = BASE_URL + "/iocm/app/sec/v1.1.0/refreshToken";

    public static final String REGISTER_DIRECT_CONNECTED_DEVICE = BASE_URL + "/iocm/app/reg/v1.1.0/deviceCredentials";
    public static final String MODIFY_DEVICE_INFO = BASE_URL + "/iocm/app/dm/v1.4.0/devices";
    public static final String QUERY_DEVICE_ACTIVATION_STATUS = BASE_URL + "/iocm/app/reg/v1.1.0/deviceCredentials";
    public static final String DELETE_DIRECT_CONNECTED_DEVICE = BASE_URL + "/iocm/app/dm/v1.4.0/devices";


    public static final String SUBSCRIBE_SERVICE_NOTIFYCATION = BASE_URL + "/iocm/app/sub/v1.2.0/subscriptions";

    public static final String CREATE_DEVICE_CMD = BASE_URL + "/iocm/app/cmd/v1.4.0/deviceCommands";



}
