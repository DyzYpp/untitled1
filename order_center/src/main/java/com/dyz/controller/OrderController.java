package com.dyz.controller;

import com.dyz.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName OrderController
 * @Author
 * @Date 2020/7/28
 * @description
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @RequestMapping("/getOrder")
    @ResponseBody
    public String order(){
        return  "订单服务调用成功";
    }

    @RequestMapping("/")
    @ResponseBody
    public String test(){
        return  "订单服务代理成功";
    }
}
