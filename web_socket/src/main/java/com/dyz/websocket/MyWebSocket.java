package com.dyz.websocket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName MyWebSocket
 * @Author
 * @Date 2020/7/29
 * @description
 */
@Component
@ServerEndpoint(value = "/websocket")
public class MyWebSocket {

    private static final Logger log = LoggerFactory.getLogger(MyWebSocket.class);

    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    // concurrent包的线程安全Map，用来存放每个客户端对应的MyWebSocket对象。
    private static ConcurrentHashMap<String, MyWebSocket> webSocketMap = new ConcurrentHashMap<String, MyWebSocket>();

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketMap.put(session.getId(), this);
        addOnlineCount();
        log.info("WebSocket连接成功");
        sendMessage("接收到服务端" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "发送的消息");
    }

    /**
     * 接受到客户端发送的消息
     */
    @OnMessage
    public  void onMessage(Session session,String str){
        try {
            log.info(str);
            session.getBasicRemote().sendText(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketMap.remove(session.getId());
        subOnlineCount();
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 向当前客户端发送消息
     * @param message
     */
    public void sendMessage(String message) {
        try {
            if (this.session == null){
                for (String s : webSocketMap.keySet()) {
                    webSocketMap.get(s).session.getBasicRemote().sendText(message);
                }
            }else {
                this.session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向当前客户端发送消息
     */
//    @Scheduled(fixedDelay=5*1000)
    public void sendMessage2() {
        try {
            String message = "接收到服务端" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "发送的消息";
            this.session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向当前客户端发送对象
     * @param obj
     */
    public void sendObject(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String s = null;
        try {
            s = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.sendMessage(s);
    }

    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message) {
        for (Map.Entry<String, MyWebSocket> entry : webSocketMap.entrySet()) {
            MyWebSocket value = entry.getValue();
            value.sendMessage(message);
        }
    }


    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }
}
