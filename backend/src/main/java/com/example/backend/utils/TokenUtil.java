package com.example.backend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend.pojo.User;

import java.util.Date;

//https://www.cnblogs.com/zxcoder/p/11964433.html token生成以及拦截
public class TokenUtil {

    private static final long EXPIRE_TIME= 10*60*60*1000;
    private static final String TOKEN_SECRET="2X6E";  //密钥盐

    /**
     * 签名生成
     */
    public static String sign(User user){
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("username", user.getUsername())
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 签名验证
     */
    public static boolean verify(String token){
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            verifier.verify(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    /**
     * 返回当前token对应的用户名
     */
    public static String getUserName(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("username").asString();
        } catch (Exception e) {
            return null;
        }
    }
}