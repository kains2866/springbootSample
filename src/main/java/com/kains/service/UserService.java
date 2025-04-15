package com.kains.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kains.pojo.entity.UserEntity;
import com.kains.pojo.param.UserLoginParam;
import com.kains.pojo.param.UserRegisterParam;

/**
 * @author kains
 * @date 2021/12/10
 */

/**
 * 用户服务接口,实现了Mybatis-Plus的IService接口
 */
public interface UserService extends IService<UserEntity> {
    void register(UserRegisterParam userRegisterParam);

    String login(UserLoginParam userLoginParam);

}
