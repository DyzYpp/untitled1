package com.dyz.controller;

import com.dyz.websocket.MyWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName WebSocketController
 * @Author
 * @Date 2020/7/29
 * @description
 */
@Controller
@RequestMapping(value = "/websocket")
public class WebSocketController {

    @Autowired
    private MyWebSocket myWebSocket;

    @PostMapping(value = "/sendSocketMsg")
    @ResponseBody
    public void returnMsg(){
            String string = "接收到服务端" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "发送的消息";
            myWebSocket.sendMessage(string);
    }
}
