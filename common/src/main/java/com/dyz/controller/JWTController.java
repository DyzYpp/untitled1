package com.dyz.controller;

import com.dyz.Jwt.JWTSubject;
import com.dyz.Jwt.JwtResponseData;
import com.dyz.Jwt.JwtUtils;
import com.dyz.Util.CookieUtil;
import com.dyz.Util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName JWTController
 * @Author
 * @Date 2020/7/28
 * @description
 */
@Controller
@RequestMapping("/jwt")
public class JWTController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CookieUtil cookieUtil;

    /*
     * @Description 登录功能
     * @param name 用户名
     * @param password 密码
     * @return java.lang.Object
     **/
    @RequestMapping(value = "/login")
    @ResponseBody
    public Object login(@RequestBody() Map<String, String> map,HttpServletResponse response) {
        String name = map.get("username");
        String password = map.get("password");
        JwtResponseData jwtResponseData = null;
        if (name.trim() != "admin" && password.trim() != "123456") {
            JWTSubject jwtSubject = new JWTSubject(name);
            String token = JwtUtils.createToken(UUID.randomUUID().toString(), "Dyz", JwtUtils.generaSubject(jwtSubject), 60 * 60 * 1000);
            String setCookie = cookieUtil.setCookie(response);
            jwtResponseData = new JwtResponseData();
            jwtResponseData.setCode(200);
            jwtResponseData.setData(setCookie);
            jwtResponseData.setMsg("登录成功");
            jwtResponseData.setToken(null);
            Map<String,String> stringMap = new HashMap<>();
            stringMap.put("token",token);
            stringMap.put("name",name);
            stringMap.put("password",password);
            redisUtil.hmset(setCookie,stringMap);
        } else {
            jwtResponseData = new JwtResponseData();
            jwtResponseData.setCode(500);
            jwtResponseData.setData(null);
            jwtResponseData.setMsg("登录失败");
            jwtResponseData.setToken(null);
        }
        return jwtResponseData;
    }

    /*
     * @Description 测试请求
     * @param request
     * @return java.lang.Object
     **/
    @ResponseBody
    @RequestMapping("/test")
    public String test(HttpServletResponse response){
        String s = cookieUtil.setCookie(response);
        return "公共服务调用成功";
    }
}
