package com.bl.demo;
import com.alibaba.fastjson.JSONObject;
import com.bl.utli.PubicMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.bl.demo.TCPServer.tcpServer;
import static com.bl.utli.SocketParam.SendError;
import static com.bl.utli.SocketParam.SendSuccess;


@RestController
public class TotolController {
    @RequestMapping(value = "/Device/PostCommand", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String PostCommand(@RequestBody String GetJson) throws Exception {
        JSONObject getJson = JSONObject.parseObject(GetJson);
        String deviceCode = getJson.getString("deviceCode");
        String sendData = getJson.getString("sendData");
        String result = tcpServer.socketSendData(deviceCode,sendData);
        if(result.equals(SendSuccess)){
            return "下发成功";
        }else if(result.equals(SendError)){
            return "下发失败";
        }
        return "下发失败";
    }

    @RequestMapping(value = "/Device/Test", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String Test() throws Exception {

        return "OK";
    }
}