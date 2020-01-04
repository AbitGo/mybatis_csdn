package com.IOT.IOTController;


import com.DeviceTask.TaskController;
import com.Redis.RedisService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UpdataDeviceData {
    Logger logger = LoggerFactory.getLogger(UpdataDeviceData.class);
    @Autowired
    private RedisService redisService;

    //电信传回的数据
    @RequestMapping(value = "/IoT/updateDeviceData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<HttpStatus> updateDeviceData(@RequestBody String DataJson) throws Exception {
        JSONObject DatJSON = JSONObject.parseObject(DataJson);

//        System.out.println("updateDeviceData:"+DataJson);
//
//        logger.info("updateDeviceData: "+"-----Start-----");
//        logger.info("updateDeviceData: "+DataJson);
//        logger.info("updateDeviceData: "+"-----End-----");

        Map<String,Object> param = new HashMap<>();

        String deviceId = DatJSON.getString("deviceId");
        param.put("deviceId",deviceId);

        JSONObject serviceObject = JSONObject.parseObject(DatJSON.getString("service"));
        String serviceId = serviceObject.getString("serviceId");

        if(serviceId.equals("lock"))
        {
            JSONObject dataObject = JSONObject.parseObject(serviceObject.getString("data"));
            param.put("SS_staus",dataObject.getString("SS_staus"));
        }
        else if(serviceId.equals("MC_staus"))
        {
            JSONObject dataObject = JSONObject.parseObject(serviceObject.getString("data"));
            param.put("mc_staus",dataObject.getString("mc_staus"));
        }
        else if(serviceId.equals("shuijing"))
        {
            JSONObject dataObject = JSONObject.parseObject(serviceObject.getString("data"));
            param.put("shuijing",dataObject.getString("shuijing"));
        }
        else if(serviceId.equals("yangan"))
        {
            JSONObject dataObject = JSONObject.parseObject(serviceObject.getString("data"));
            param.put("yangan",dataObject.getString("yangan"));
        }
        else if(serviceId.equals("zhengdong"))
        {
            JSONObject dataObject = JSONObject.parseObject(serviceObject.getString("data"));
            param.put("zhengdong",dataObject.getString("zhengdong"));
        }
        else if(serviceId.equals("voltage"))
        {
            JSONObject dataObject = JSONObject.parseObject(serviceObject.getString("data"));
            param.put("voltage",dataObject.getString("voltage"));
        }
        else if(serviceId.equals("dbm"))
        {
            JSONObject dataObject = JSONObject.parseObject(serviceObject.getString("data"));
            param.put("dbm",dataObject.getString("dbm"));
        }
        else if(serviceId.equals("info_up"))
        {
            JSONObject dataObject = JSONObject.parseObject(serviceObject.getString("data"));
            param.put("temp",dataObject.getString("temp"));
            param.put("humi",dataObject.getString("humi"));
        }
        param.put("updataTime",System.currentTimeMillis()/1000);

        //增加hash的表结构
        Boolean result = redisService.AddHashKeyAndValue("deviceId:"+param.get("deviceId"),param);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //添加设备返回的代码
    @RequestMapping(value = "/IoT/addDevice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<HttpStatus> addDevice(@RequestBody String AddJson) throws Exception {
        JSONObject DataJson = JSONObject.parseObject(AddJson);
        System.out.println("Data:"+AddJson);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/IoT/commandConfirmData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<HttpStatus> commandConfirmData(@RequestBody String DataJson) throws Exception {
        System.out.println("commandConfirmData:"+DataJson);
        logger.info("commandConfirmData: "+"-----Start:"+System.currentTimeMillis()/1000+"-----");
        logger.info("commandConfirmData: "+DataJson);
        logger.info("commandConfirmData: "+"-----End-----");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/IoT/commandRspData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<HttpStatus> commandRspData(@RequestBody String DataJson) throws Exception {
        System.out.println("commandRspData:"+DataJson);
        logger.info("commandRspData: "+"-----Start:"+System.currentTimeMillis()/1000+"-----");
        logger.info("commandRspData: "+DataJson);
        logger.info("commandRspData: "+"-----End-----");
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
