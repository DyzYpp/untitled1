package com.dyz.Jwt;

import org.springframework.stereotype.Component;

/**
 * @ClassName JwtResponseData
 * @Author
 * @Date 2020/7/27
 * @description
 */
@Component
public class JwtResponseData {

    private Integer code;

    private Object data;

    private String msg;

    private String token;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "JwtResponseData{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
