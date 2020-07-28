package com.dyz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName OrderController
 * @Author
 * @Date 2020/7/28
 * @description
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/getUser")
    @ResponseBody
    public String user(){
        return "用户服务调用成功";
    }

}
