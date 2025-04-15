package com.kains.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kains.mapper.UserEntityMapper;
import com.kains.pojo.entity.UserEntity;
import com.kains.pojo.param.UserLoginParam;
import com.kains.pojo.param.UserRegisterParam;
import com.kains.service.UserService;
import com.kains.utils.DefaultNameGenerateUtil;
import com.kains.utils.JwtUtil;
import com.kains.utils.MD5Util;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kains
 * @date 2021/12/20
 */


/**
 * 实现类，进行实际的业务逻辑处理
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserEntityMapper, UserEntity> implements UserService {


    @Override
    public void register(UserRegisterParam userRegisterParam) {
        //先检查是否有相同用户名
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserEntity::getUsername, userRegisterParam.getUsername());
        UserEntity exitsUser = this.baseMapper.selectOne(queryWrapper);
        if (exitsUser != null) {
            throw new RuntimeException("用户名已存在");
        }
        //生成用户信息
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRegisterParam.getUsername());
        //密码使用加盐加密存储
        userEntity.setPassword(MD5Util.md5WithSalt(userRegisterParam.getPassword()));
        userEntity.setRole(userRegisterParam.getRole());
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setStatus(1);
        //给用户一个随机姓名
        userEntity.setName(DefaultNameGenerateUtil.generateUsername());
        this.baseMapper.insert(userEntity);
    }

    @Override
    public String login(UserLoginParam param) {
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getUsername, param.getUsername());
        UserEntity userEntity = this.baseMapper.selectOne(wrapper);
        if (userEntity == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        //取盐值
        String salt = userEntity.getPassword().substring(32,64);
        //验证密码
        param.setPassword(MD5Util.md5WithSalt(param.getPassword(), salt));
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserEntity::getUsername, param.getUsername());
        queryWrapper.eq(UserEntity::getPassword, param.getPassword());
        userEntity = this.baseMapper.selectOne(queryWrapper);
        if(userEntity.getStatus()==1){
            throw new RuntimeException("用户还未审核通过,请联系管理员");
        }
        if(userEntity.getStatus()==-1){
            throw new RuntimeException("用户已被封禁,请联系管理员");
        }

        if (userEntity!= null) {
            // 登录成功后，生成token并返回
            Map<String, Object> claims = new HashMap<>();
            claims.put("username",userEntity.getUsername());
            claims.put("role",userEntity.getRole());
            String token = JwtUtil.generateJwt(claims);
            return token;
        }
        return null;
    }

}