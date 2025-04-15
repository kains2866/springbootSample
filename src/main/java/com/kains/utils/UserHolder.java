package com.kains.utils;

/**
 * @author kains
 * @date 2021/12/20
 */


import com.kains.pojo.entity.UserEntity;

/**
 * 此类用于保存当前登录的用户信息
 */
public class UserHolder {
    private static final ThreadLocal<UserEntity> userHolder = new ThreadLocal<>();

    //防止实例化
    private UserHolder() {}

    public static void setUser(UserEntity user) {
        userHolder.set(user);
    }

    public static UserEntity getUser() {
        return userHolder.get();
    }

    public static void removeUser() {
        userHolder.remove();
    }
}
//TODO：完善Utils类：日期格式化、UUID生成、文件上传下载、邮件发送、短信发送、验证码生成、Excel