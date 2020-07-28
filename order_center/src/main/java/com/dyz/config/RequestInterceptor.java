package com.dyz.config;

import com.dyz.Jwt.JwtResponseData;
import com.dyz.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName RequestInterceptor
 * @Author
 * @Date 2020/7/28
 * @description
 */
@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private JwtResponseData jwtResponseData;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        ServletOutputStream outputStream = response.getOutputStream();
        jwtResponseData = (JwtResponseData) jwtService.testAll(request);
        if (jwtResponseData.getCode() != 500){
            return true;
        }else{
            outputStream.write(jwtResponseData.getMsg().getBytes());
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
