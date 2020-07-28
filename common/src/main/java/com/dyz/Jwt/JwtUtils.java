package com.dyz.Jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @ClassName JwtUtils
 * @Author
 * @Date 2020/7/27
 * @description
 */
@Component
public class JwtUtils {

    private static final String JWT_SECERT = "myJwt";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static final int JWT_ERRCODE_EXPIRE = 1005; // token过期

    public static final int JWT_ERRORCODE_FAIL = 1006; // 验证失败

    public static SecretKey generalKey() {
        try {
            byte[] secertBytes = JWT_SECERT.getBytes("UTF-8");
            SecretKeySpec keySpec = new SecretKeySpec(secertBytes, 0, secertBytes.length, "AES");
            return keySpec;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * @Description 签发JWT 生成TOKEN
     * @param id jwt的唯一身份标识,主要用来作为一次性token
     * @param iss jwt签发者
     * @param subject jwt所面向的用户，PAYLOAD中记录的claims
     * @param times 有效期单位毫秒
     * @return java.lang.String
     **/
    public static String createToken(String id, String iss, String subject, long times) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long currentTimeMillis = System.currentTimeMillis();
        Date now = new Date(currentTimeMillis);
        SecretKey secretKey = generalKey();
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuer(iss)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, secretKey);
        if (times > 0) {
            long expMillis = currentTimeMillis + times;
            Date date = new Date(expMillis);
            builder.setExpiration(date);
        }
        return builder.compact();
    }

    /*
     * @Description 校验JWT
     * @param jwtStr 
     * @return com.xndc.mobile.Jwt.JWTResult
     **/
    public static JWTResult validateJWT(String jwtStr){
        JWTResult jwtResult = new JWTResult();
        Claims claims = null;
        try{
            claims = parseJWT(jwtStr);
            jwtResult.setSuccess(true);
            jwtResult.setClaims(claims);
        }catch (ExpiredJwtException e){
            jwtResult.setCode(JWT_ERRCODE_EXPIRE);
            jwtResult.setSuccess(false);
        }catch (SignatureException e){
            jwtResult.setCode(JWT_ERRORCODE_FAIL);
            jwtResult.setSuccess(false);
        }catch (Exception e){
            jwtResult.setCode(JWT_ERRORCODE_FAIL);
            jwtResult.setSuccess(false);
        }
        return jwtResult;
    }


    /*
     * @Description 解析JWT字符串
     * @param jwt 服务器为客户端生成的签名 就是token
     * @return io.jsonwebtoken.Claims
     **/
    public static Claims parseJWT(String jwt){
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    /*
     * @Description 生成subject信息
     * @param obj 要转换的对象
     * @return java.lang.String
     **/
    public static String generaSubject(Object obj){
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
