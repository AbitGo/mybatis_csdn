package com.IOT.service.commandDelivery;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.druid.sql.ast.statement.SQLForeignKeyImpl;
import org.apache.http.HttpResponse;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.IOT.utils.Constant;
import com.IOT.utils.HttpsUtil;
import com.IOT.utils.JsonUtil;
import com.IOT.utils.StreamClosedHttpResponse;

//该文件是创建设备下发命令
public class CreateDeviceCommand {
    @SuppressWarnings("unchecked")
    public static void main(String args[]) throws Exception {
        CreateDeviceCommand();
    }

    public static void CreateDeviceCommand() throws Exception {
        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();
        String accessToken = login(httpsUtil);

        String urlCreateDeviceCommand = Constant.CREATE_DEVICE_CMD;
        String appId = Constant.APPID;
        String callbackUrl = Constant.REPORT_CMD_EXEC_RESULT_CALLBACK_URL;

        //由电信平台提供deviceID
        String deviceId = "d061be7c-825e-417a-b74b-a619080af847";

        Integer expireTime = 0;
        Integer maxRetransmit = 3;

        String serviceId_lock_CTRL = "lock_CTRL";
        String method_lock_ctrl = "lock_ctrl";
        ObjectNode paras_1 = JsonUtil.convertObject2ObjectNode("{\"lock_ctrl\":\"1\"}");
        ObjectNode paras_0 = JsonUtil.convertObject2ObjectNode("{\"lock_ctrl\":\"0\"}");

        Map<String, Object> paramCommand = new HashMap<>();
        paramCommand.put("serviceId", serviceId_lock_CTRL);
        paramCommand.put("method", method_lock_ctrl);
        paramCommand.put("paras", paras_0);

//        if(Onoff.equals("1")) {
//            paramCommand.put("paras", paras_1);
//        }else {
//            paramCommand.put("paras", paras_0);
//        }
        
        Map<String, Object> paramCreateDeviceCommand = new HashMap<>();
        paramCreateDeviceCommand.put("deviceId", deviceId);
        paramCreateDeviceCommand.put("command", paramCommand);
        paramCreateDeviceCommand.put("callbackUrl", callbackUrl);
        paramCreateDeviceCommand.put("expireTime", expireTime);
        paramCreateDeviceCommand.put("maxRetransmit", maxRetransmit);
        
        String jsonRequest = JsonUtil.jsonObj2Sting(paramCreateDeviceCommand);
                
        Map<String, String> header = new HashMap<>();
        header.put(Constant.HEADER_APP_KEY, appId);
        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);
        
        HttpResponse responseCreateDeviceCommand = httpsUtil.doPostJson(urlCreateDeviceCommand, header, jsonRequest);

        String responseBody = httpsUtil.getHttpResponseBody(responseCreateDeviceCommand);

        System.out.println("response:"+responseBody);
//        System.out.println("CreateDeviceCommand, response content:");
//        System.out.println(responseCreateDeviceCommand.getStatusLine());
//        System.out.println(responseBody);
//        System.out.println();
    }

    /**
     * Authentication.get token
     */
    @SuppressWarnings("unchecked")
    public static String login(HttpsUtil httpsUtil) throws Exception {

        String appId = Constant.APPID;
        String secret = Constant.SECRET;
        String urlLogin = Constant.APP_AUTH;

        Map<String, String> paramLogin = new HashMap<>();
        paramLogin.put("appId", appId);
        paramLogin.put("secret", secret);

        StreamClosedHttpResponse responseLogin = httpsUtil.doPostFormUrlEncodedGetStatusLine(urlLogin, paramLogin);
//
//        System.out.println("app auth success,return accessToken:");
//        System.out.println(responseLogin.getStatusLine());
//        System.out.println(responseLogin.getContent());
//        System.out.println();

        Map<String, String> data = new HashMap<>();
        data = JsonUtil.jsonString2SimpleObj(responseLogin.getContent(), data.getClass());
        return data.get("accessToken");
    }

}
