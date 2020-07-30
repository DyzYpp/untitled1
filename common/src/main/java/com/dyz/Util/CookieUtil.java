package com.dyz.Util;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @ClassName CookieUtil
 * @Author
 * @Date 2020/7/30
 * @description
 */
@Component
public class CookieUtil {

    private static final String LOGIN_FLAG = "LOG_COK";

    public  String setCookie(HttpServletResponse response){
        String uuid = UUID.randomUUID().toString();
        Cookie cookie = new Cookie(LOGIN_FLAG,uuid);
        cookie.setPath("/");
        response.addCookie(cookie);
        return uuid;
    }

    public  String getCookie(HttpServletRequest request){
        if (request.getCookies() != null){
            for (Cookie cookie : request.getCookies()) {
                cookie.setPath("/");
                if (cookie.getName().equals(LOGIN_FLAG)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
