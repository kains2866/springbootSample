package com.kains.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.kains.constant.MsgConstant;
import com.kains.pojo.Result;
import com.kains.pojo.entity.UserEntity;
import com.kains.utils.JwtUtil;
import com.kains.utils.UserHolder;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author kains
 * @date 2021/12/10
 */

/**
 * 登录拦截器
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //检查是否携带token
        String jwt = request.getHeader("token");
        if(!StringUtils.hasLength(jwt))
        {
            Result error = Result.error(MsgConstant.LOGIN_REQUIRED);
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }
        //解析token
        try{
            Claims claims = JwtUtil.parseJwt(jwt);
            if(claims!=null){
                String username = claims.get("username", String.class);
                Integer role = claims.get("role", Integer.class);
                UserEntity user = new UserEntity();
                user.setUsername(username);
                user.setRole(role);
                UserHolder.setUser(user);
            }
            return true;
        }catch (Exception e){
            log.error("token解析失败：{}",e.getMessage());
            Result error = Result.error(MsgConstant.LOGIN_REQUIRED);
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }

    }
}
