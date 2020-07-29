package com.dyz.service;

import com.dyz.Jwt.JWTResult;
import com.dyz.Jwt.JwtResponseData;
import com.dyz.Jwt.JwtUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName JwtService
 * @Author
 * @Date 2020/7/28
 * @description
 */
@Service
public class JwtService {

    public Object testAll(HttpServletRequest request) {
        JwtResponseData jwtResponseData = new JwtResponseData();
        String header = request.getHeader("Authorization");
        if (header == null || header.equals("null") || header == "null"){
            jwtResponseData.setCode(400);
            jwtResponseData.setMsg("您还未登录");
            return jwtResponseData;
        }
        JWTResult jwtResult = JwtUtils.validateJWT(header);

        if (jwtResult.isSuccess()) {
            jwtResponseData.setCode(200);
            jwtResponseData.setData(jwtResult.getClaims().getSubject());
            String token = JwtUtils.createToken(jwtResult.getClaims().getId(), jwtResult.getClaims().getIssuer(), jwtResult.getClaims().getSubject(), 1 * 60 * 1000);
            jwtResponseData.setToken(token);
            return jwtResponseData;
        } else {
            jwtResponseData.setCode(500);
            jwtResponseData.setMsg("身份凭证已经过期!");
            return jwtResponseData;
        }
    }

}
