package com.kains.utils;

/**
 * @author kains
 * @date 2021/12/20
 */

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * MD5加密工具类
 */
@Slf4j
public class MD5Util {

    // 定义允许的字符集
    private static final String CHAR_STRING =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+{}:\"<>?";

    // 私有构造函数，防止实例化
    private MD5Util(){}

    /**
     * 对字符串进行MD5加密
     * @param raw 待加密字符串
     * @return 加密后的字符串
     */
    public static String md5(String raw){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(raw.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(b & 0xff);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            return sb.toString();
        }catch (Exception e){
            log.info("MD5 algorithm not supported");
        }
        return null;
    }

    /**
     * 加盐MD5加密(该方法用于注册存储,最终结果为64位字符串，包含32位MD5值和32位盐值)
     * @param raw 待加密字符串
     * @return 加密后的字符串
     */
    public static String md5WithSalt(String raw) {
        return md5WithSalt(raw, generateSalt());
    }

    /**
     * 指定盐值MD5加密(该方法用于验证,最终结果为64位字符串，包含32位MD5值和32位盐值)
     * @param raw 待加密字符串
     * @param salt 盐值字符串
     * @return 加密后的字符串
     */
    public static String md5WithSalt(String raw, String salt) {
        String salted = raw + salt;
        return md5(salted)+salt;
    }

    /**
     * 生成盐值
     * @return 盐值字符串(32位)
     */
    private static String generateSalt() {
        StringBuilder sb = new StringBuilder(32);
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 32; i++) {
            // 生成一个索引，从CHAR_STRING中随机选择一个字符
            int index = random.nextInt(CHAR_STRING.length());
            sb.append(CHAR_STRING.charAt(index));
        }
        return sb.toString();
    }
}
