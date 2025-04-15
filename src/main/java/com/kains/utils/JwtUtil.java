package com.kains.utils;

/**
 * @author kains
 * @date 2021/12/20
 */

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * Jwt工具类
 */
public class JwtUtil {
    public static final String signKey = "KainsAndRamona";
    public static final Long expireTime = 24*60*60*1000*3L;

    /**
     * 生成jwt
     * @param claims
     * @return
     */
    public static String generateJwt(Map<String,Object> claims){
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, signKey)
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+expireTime))
                .compact();
    }

    /**
     * 解析jwt
     * @param jwt
     * @return
     */
    public static Claims parseJwt(String jwt){
        return Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}