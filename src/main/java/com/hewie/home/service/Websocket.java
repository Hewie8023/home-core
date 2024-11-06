package com.hewie.home.service;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint(value = "/websocket/{username}")
@Component
public class Websocket {
    private static final Logger log = LoggerFactory.getLogger(Websocket.class);
    //静态变量，用来记录当前在线连接数。设计为安全的
    private static int onlineCount = 0;
    //concurrentHashMap分段锁，用来存放每个客户端对应的Websocket对象。
    private static Map<String, Websocket> clients = new ConcurrentHashMap<String, Websocket>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private String username;

    /**
     * 连接建立成功调用的方法
     * @param username
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) {
        this.username = username;
        this.session = session;
        Websocket.onlineCount++;
        log.info("有一连接进入！当前在线人数为" + onlineCount);
        clients.put(username, this);
        log.info(clients.keySet().toString());
    }

    @Autowired
    private ScheduledService scheduledService;

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        clients.remove(username);
        Websocket.onlineCount--;
        log.info("有一连接关闭！当前在线人数为" + onlineCount);
        log.info("close websocket ... username = " + username);
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        System.out.println("收到客户端的消息"+message);
        if(!message.equals("ping")) {
            sendMessage(message);
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("WebSocket发生错误：" + throwable.getMessage());
    }

    public static void sendMessage(String message) {
        // 向所有连接websocket的客户端发送消息
        // 可以修改为对某个客户端发消息
        for (Websocket item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }

    public static void sendMessageToOne(String message, String username) {
        // 向所有连接websocket的客户端发送消息
        // 可以修改为对某个客户端发消息
        clients.get(username).session.getAsyncRemote().sendText(message);
    }


}
