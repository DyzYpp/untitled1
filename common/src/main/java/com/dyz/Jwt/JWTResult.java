package com.dyz.Jwt;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

/**
 * @ClassName JWTResult
 * @Author
 * @Date 2020/7/27
 * @description
 */
@Component
public class JWTResult {

    private Integer code;

    private boolean Success;

    private Claims claims;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public Claims getClaims() {
        return claims;
    }

    public void setClaims(Claims claims) {
        this.claims = claims;
    }

    @Override
    public String toString() {
        return "JWTResult{" +
                "code=" + code +
                ", Success=" + Success +
                ", claims=" + claims +
                '}';
    }
}
