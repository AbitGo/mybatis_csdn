package com.bl.demo;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.*;

import static com.bl.utli.SocketParam.*;

public class TCPServer extends Thread {
    ServerSocket serverSocket = null;
    public static TCPServer tcpServer;

    @PostConstruct
    public void init(){
        tcpServer = this;
        tcpServer.start();
    }

    public String socketSendData(String deviceCode,String sendData){
        //不为空时
        subSocketClient temp = DeviceCode2SocketMap.get(deviceCode);
        if(null!=temp){
            return temp.sendSocketData(sendData,deviceCode);
        }else{
            return SendError;
        }
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PortNum);
        } catch (IOException e) {
            System.out.println("the port cannot open.");
        }
        while(true){
            try {
                System.out.println("wait. .. ...");
                //使用accept()是阻塞方法
                Socket socketTemp = serverSocket.accept();
                new subSocketClient(this.serverSocket,socketTemp).start();
                //为每个连接都创建一个线程客户端
            } catch (IOException e) {
                System.out.println("happening");
            }
        }
    }
}
