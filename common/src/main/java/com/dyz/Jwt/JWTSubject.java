package com.dyz.Jwt;

/**
 * @ClassName JWTSubject
 * @Author
 * @Date 2020/7/27
 * @description
 */
public class JWTSubject {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JWTSubject(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return "JWTSubject{" +
                "name='" + name + '\'' +
                '}';
    }
}
