package com.dyz.service;

import org.springframework.stereotype.Service;

/**
 * @ClassName UserService
 * @Author
 * @Date 2020/7/28
 * @description
 */
@Service
public class UserService {
    public String test(){
        return "不同服务之间相互调用成功!";
    }
}
