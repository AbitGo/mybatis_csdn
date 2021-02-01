package com.bl.demo;

import com.alibaba.fastjson.JSONObject;

import javax.websocket.SendResult;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static com.bl.utli.SocketParam.*;

/**
 * @author 韦海涛
 * @version 1.0
 * @date 2021/2/1 20:53
 */
public class subSocketClient extends Thread {
    private ServerSocket serverSocket;
    private Socket socket;

    public subSocketClient(ServerSocket serverSocket, Socket socket) {
        this.serverSocket = serverSocket;
        this.socket = socket;
    }

    public String sendSocketData(String sendData,String deviceCode){
        try {
            BufferedReader br = null;
            PrintWriter pw = null;
            br = new BufferedReader(new InputStreamReader(System.in));
            pw = new PrintWriter(socket.getOutputStream(), true);
            //查看socket是否存在
            if (!socket.isClosed()) {
                pw.println(sendData);
                pw.flush();
                return SendSuccess;
            }else {
                //不存在则移除该socket
                DeviceCode2SocketMap.remove(deviceCode);
                return SendError;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return SendError;

    }

    @Override
    public void run() {
        try {
            //获取输入流
            InputStream inputStream = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            byte[] buf = new byte[1024];
            //接收数据
            int line = 0;
            while ((line = inputStream.read(buf)) != -1) {
                String param = new String(buf, 0, line);
                JSONObject jsonObject = JSONObject.parseObject(param);

                String type = jsonObject.getString("type");
                if(type.equals(TypeRegister)){
                    String deviceCode = jsonObject.getString("deviceCode");
                    DeviceCode2SocketMap.put(deviceCode,this);
                    //sendSocketData("OK",deviceCode);//测试
                }
                else if(type.equals(TypeUpdateData)){
                    String deviceCode = jsonObject.getString("deviceCode");
                    //这边方式存放数据
                    String deviceData = jsonObject.getString("deviceData");
                }

            }

            //线程停止10ms
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}