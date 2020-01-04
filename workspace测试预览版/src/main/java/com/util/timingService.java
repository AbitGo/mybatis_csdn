package com.util;

import com.DeviceTask.ChangeStatusPojos;
import com.DeviceTask.TaskService;
import com.IOT.service.appAccessSecurity.Authentication;
import com.IOT.utils.Constant;
import com.IOT.utils.HttpsUtil;
import com.IOT.utils.JsonUtil;
import com.IOT.utils.StreamClosedHttpResponse;
import com.Redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class timingService {
    @Autowired
    RedisService redisService;
    @Autowired
    TaskService taskService;

    //5秒执行一次
    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() throws Exception {
        String accessToken = Constant.accessToken;
        if(accessToken.equals("accessToken"))
        {
            //代表并没有鉴权成功,所以需要进行再次鉴权,
            new Authentication().FirstLogin();
            System.out.println("reportCurrentTime:reportCurrentTime"+Constant.accessToken);
        }


        List<ChangeStatusPojos> ListParams = new ArrayList<ChangeStatusPojos>();
        Set<String> StartTimeSet = null;
        Set<String> EndTimeSet= null;

        //直接获取0<任务的时间<当前时间的数量
        //通过分数返回有序集合指定区间内的成员个数
        double nowTime = (double)System.currentTimeMillis()/1000;
        StartTimeSet = redisService.GetKeysRangeByScore("StartTime",0,nowTime);
        EndTimeSet = redisService.GetKeysRangeByScore("EndTime",0,nowTime);

        //如果result为0的话那就意味着当前没有需要处理的代码不要进行下一步了.
        if(StartTimeSet.isEmpty() && EndTimeSet.isEmpty()){
            //无的状态返回值,直接终止改函数
            return;
        }

        for(String params:StartTimeSet)
        {
            ChangeStatusPojos changeStatusPojos = new ChangeStatusPojos();
            changeStatusPojos.setStatus(1);
            changeStatusPojos.setTaskCode(params);
            ListParams.add(changeStatusPojos);
//            System.out.println("StartTimeSet:"+params);
        }
        for(String params:EndTimeSet)
        {
            ChangeStatusPojos changeStatusPojos = new ChangeStatusPojos();
            changeStatusPojos.setStatus(2);
            changeStatusPojos.setTaskCode(params);
            ListParams.add(changeStatusPojos);
//            System.out.println("EndTime:"+params);
        }
        int result = taskService.updateTaskByTaskCode(ListParams);
        if(result != 0) {
            //更改好之后则直接删除redis内的ZSet键值
            if(!StartTimeSet.isEmpty())
                redisService.RemoveZSetValue("StartTime",StartTimeSet.toArray(new String[StartTimeSet.size()]));
            if(!EndTimeSet.isEmpty())
            redisService.RemoveZSetValue("EndTime",EndTimeSet.toArray(new String[EndTimeSet.size()]));
            return;
        }
        else {
            //对遗留杂质redis数据进行清洗,(即清洗过期100s以上的数据)
            Set<String> otherData1 = redisService.GetKeysRangeByScore("StartTime",0,nowTime-100000);
            Set<String> otherData2= redisService.GetKeysRangeByScore("EndTime",0,nowTime-100000);
            if(!otherData1.isEmpty())
                redisService.RemoveZSetValue("StartTime",StartTimeSet.toArray(new String[otherData1.size()]));
            if(!otherData2.isEmpty())
                redisService.RemoveZSetValue("EndTime",EndTimeSet.toArray(new String[otherData2.size()]));
            return;
        }
    }

    //每30分钟刷新鉴权
    @Scheduled(cron = "0 0/30 * * * ?")
    public void RefreshToken() throws Exception {
        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        String refreshToken = getRefreshToken(httpsUtil);
        String appId = Constant.APPID;
        String secret = Constant.SECRET;
        String urlRefreshToken = Constant.REFRESH_TOKEN;

        Map<String, Object> param_reg = new HashMap<>();
        param_reg.put("appId", appId);
        param_reg.put("secret", secret);
        param_reg.put("refreshToken", refreshToken);

        String jsonRequest = JsonUtil.jsonObj2Sting(param_reg);
        StreamClosedHttpResponse bodyRefreshToken = httpsUtil.doPostJsonGetStatusLine(urlRefreshToken, jsonRequest);
        Map<String, String> data = new HashMap<>();
        data = JsonUtil.jsonString2SimpleObj(bodyRefreshToken.getContent(), data.getClass());
        String accessToken = data.get("accessToken");

        Constant.accessToken = accessToken;
        System.out.println("RefreshToken:accessToken:" + accessToken);
    }

    @SuppressWarnings("unchecked")
    public static String getRefreshToken(HttpsUtil httpsUtil) throws Exception {

        String appId = Constant.APPID;
        String secret = Constant.SECRET;
        String urlLogin = Constant.APP_AUTH;
        Map<String, String> paramLogin = new HashMap<>();
        paramLogin.put("appId", appId);
        paramLogin.put("secret", secret);
        StreamClosedHttpResponse responseLogin = httpsUtil.doPostFormUrlEncodedGetStatusLine(urlLogin, paramLogin);

        Map<String, String> data = new HashMap<>();
        data = JsonUtil.jsonString2SimpleObj(responseLogin.getContent(), data.getClass());
        return data.get("refreshToken");
    }
}
