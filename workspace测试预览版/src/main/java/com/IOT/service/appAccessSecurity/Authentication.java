package com.IOT.service.appAccessSecurity;

import java.util.HashMap;
import java.util.Map;

import com.IOT.utils.Constant;
import com.IOT.utils.HttpsUtil;
import com.IOT.utils.JsonUtil;
import com.IOT.utils.StreamClosedHttpResponse;
import com.Redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  Auth:
 *  
 *  This interface is called by an NA for access authentication when the NA accesses open APIs of the IoT platform for the first time. 
 */

public class Authentication {
    @Autowired
    RedisService redisService;
    @SuppressWarnings("unchecked")
	public void FirstLogin() throws Exception {
        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();
        //修改Constant中的部分参数
        String appId = Constant.APPID;
        String secret = Constant.SECRET;
        String urlLogin = Constant.APP_AUTH;

        Map<String, String> param = new HashMap<>();
        param.put("appId", appId);
        param.put("secret", secret);

        StreamClosedHttpResponse responseLogin = httpsUtil.doPostFormUrlEncodedGetStatusLine(urlLogin, param);
        Map<String, String> data = new HashMap<>();
        data = JsonUtil.jsonString2SimpleObj(responseLogin.getContent(), data.getClass());
        String accessToken = data.get("accessToken");
        Constant.accessToken = accessToken;
    }
}
