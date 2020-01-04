package com.DeviceTask;

import com.IOT.utils.Constant;
import com.IOT.utils.HttpsUtil;
import com.IOT.utils.JsonUtil;
import com.Redis.RedisService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.user.UserController;
import com.util.PubicMethod;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TaskController {
    Logger logger = LoggerFactory.getLogger(TaskController.class);
    @Autowired
    TaskService taskService;
    @Autowired
    RedisService redisService;

    @RequestMapping(value = "/Task/ActiveTheUnlockTask", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String PerformTheUnlockTask(@RequestBody String GetJSON) throws Exception {
        JSONObject getJson = JSONObject.parseObject(GetJSON);
        //申请任务唯一标识符
        String TaskCode = getJson.getString("TaskCode");
        Long CreateTime = System.currentTimeMillis()/1000;
        String UserCode = getJson.getString("UserCode");
        String serviceId = getJson.getString("serviceId");
        String method = getJson.getString("method");
        String paras = getJson.getString("paras");

        String RecordCode = "Rec" + PubicMethod.getAcademeCode();

        JSONObject jsonObject = new JSONObject();
        Map<String,Object> result = taskService.getTaskByCode(TaskCode);
        if(result==null)
        {
            jsonObject.put("msg","该任务不存在,请刷新");
            jsonObject.put("flag","0");
            return jsonObject.toString();
        }else{
            if((int)result.get("Activity")==0){
                jsonObject.put("msg","该任务还未审核通过,请通知上级管理员审核该任务");
                jsonObject.put("flag","0");
                return jsonObject.toString();
            }
            if((int) result.get("Status")==0){
                jsonObject.put("msg","该任务执行时间未达到");
                jsonObject.put("flag","0");
                return jsonObject.toString();
            }else if((int) result.get("Status")==2){
                jsonObject.put("msg","该任务执行时间已过期");
                jsonObject.put("flag","0");
                return jsonObject.toString();
            }
        }


        //Command code start
        String DeviceCode = (String) result.get("DeviceCode");
        String DeviceOwnner = (String) result.get("DeviceOwnner");

        String deviceId = taskService.SearchDianXinCodeByDeviceCode(DeviceCode);
        Map<String,String> CommandParam = new HashMap<>();
        CommandParam.put("deviceId",deviceId);
        CommandParam.put("serviceId",serviceId);
        CommandParam.put("method",method);
        CommandParam.put("paras",paras);
        String CommandReult = CreateDeviceCommand(CommandParam);

        //Command code end


        logger.info("PerformTheUnlockTask: "+System.currentTimeMillis()+"-----Start-----");
        logger.info("TaskCode: "+TaskCode+" UserCode: "+UserCode);
        logger.info("serviceId: "+serviceId+" paras: "+paras);
        logger.info("PerformTheUnlockTask: "+"-----End-----");

        //Command request failed
        if(CommandReult.equals("null"))
        {
            jsonObject.put("msg","执行下发失败,请查看任务是否有效");
            jsonObject.put("flag","0");
            return jsonObject.toString();
        }
        //if(CommandReult.equals("SENT"))
        //Command request success
        Map<String,Object> param = new HashMap<>();
        param.put("TaskCode",TaskCode);
        param.put("CreateTime",CreateTime);
        param.put("DeviceCode",DeviceCode);
        param.put("DeviceOwnner",DeviceOwnner);
        //执行者的id即用户id
        param.put("PerformerCode",UserCode);
        param.put("RecordCode",RecordCode);

        int results = taskService.addUnlockRecord(param);
        jsonObject.put("msg","执行下发成功,任务记录已保存");
        jsonObject.put("flag","1");
        if(results != 0)
        //申请任务通过审核
        {
            jsonObject.put("msg","执行下发成功,任务记录已保存");
            jsonObject.put("flag","1");
        }else{
            jsonObject.put("msg","执行下发成功,人物记录未保存,请联系管理员");
            jsonObject.put("flag","1");
        }
        return jsonObject.toString();
    }

    public String CreateDeviceCommand(Map<String,String> param) throws Exception {
        //param start
        String deviceId = param.get("deviceId");
        String serviceId = param.get("serviceId");
        String method = param.get("method");
        ObjectNode paras = JsonUtil.convertObject2ObjectNode(param.get("paras"));
        //param start

        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();
        String accessToken = Constant.accessToken;

        String urlCreateDeviceCommand = Constant.CREATE_DEVICE_CMD;
        String appId = Constant.APPID;
        String callbackUrl = Constant.REPORT_CMD_EXEC_RESULT_CALLBACK_URL;

        Integer expireTime = 0;
        Integer maxRetransmit = 3;
        Map<String, Object> paramCommand = new HashMap<>();
        paramCommand.put("serviceId", serviceId);
        paramCommand.put("method", method);
        paramCommand.put("paras", paras);

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
        JSONObject jsonObject = JSONObject.parseObject(responseBody);

        String status = (String)jsonObject.get("status");
        if(status==null){
            //该状态值为空,可以直接返回null字符串
            status="null";
        }
        return status;
    }


}
