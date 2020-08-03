package com.dyz.controller;

import com.auth0.jwt.JWT;
import com.dyz.Jwt.JWTResult;
import com.dyz.Jwt.JwtResponseData;
import com.dyz.Jwt.JwtUtils;
import com.dyz.Util.CookieUtil;
import com.dyz.Util.RedisUtil;
import com.dyz.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName OrderController
 * @Author
 * @Date 2020/7/28
 * @description
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private JwtResponseData jwtResponseData;

    @RequestMapping("/getOrder")
    @ResponseBody
    public Object order(HttpServletRequest request, HttpServletResponse response){
        jwtResponseData = new JwtResponseData();
        String utilCookie = cookieUtil.getCookie(request);
        if (utilCookie == null){
            jwtResponseData.setCode(500);
            jwtResponseData.setMsg("请先登录!");
            return jwtResponseData;
        }
        Map<String, String> stringMap = redisUtil.hmget(utilCookie);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String token = stringMap.get("token");
        String username = stringMap.get("name");
        String password = stringMap.get("password");
        JWTResult jwtResult = JwtUtils.validateJWT(token);
        Map<String,String> userInfo = new HashMap<String, String>();
        userInfo.put("username",username);
        userInfo.put("id","1");
        if (jwtResult.isSuccess() && username.equals("admin") && password.equals("123456")){
            jwtResponseData.setCode(200);
            jwtResponseData.setMsg("验证成功!");
        }else{
            jwtResponseData.setCode(500);
            jwtResponseData.setMsg("验证失败,请重新登录");
        }
        jwtResponseData.setData(userInfo);
        return  jwtResponseData;
    }

    @RequestMapping("/")
    @ResponseBody
    public String test(){
        return  "订单服务代理成功";
    }


    @RequestMapping("/logout")
    @ResponseBody
    public Object logout(HttpServletRequest request,HttpServletResponse response){
        cookieUtil.clearCookie(request,response);
        jwtResponseData = new JwtResponseData();
        jwtResponseData.setMsg("退出成功");
        jwtResponseData.setCode(200);
        return jwtResponseData;
    }
}
