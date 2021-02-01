package com.bl.utli;

import com.bl.demo.subSocketClient;

import java.net.Socket;
import java.util.HashMap;

/**
 * @author 韦海涛
 * @version 1.0
 * @date 2021/2/1 20:12
 */
public class SocketParam {
    public static HashMap<String, subSocketClient> DeviceCode2SocketMap = new HashMap<>();
    public static int PortNum = 7880;
    public static String TypeRegister = "01";
    public static String TypeUpdateData = "02";
    public static String SendSuccess = "OK";
    public static String SendError = "ERROR";
}
