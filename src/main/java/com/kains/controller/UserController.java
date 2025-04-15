package com.kains.controller;

/**
 * @author kains
 * @date 2021/12/20
 */


import com.kains.constant.MsgConstant;
import com.kains.pojo.Result;
import com.kains.pojo.param.UserLoginParam;
import com.kains.pojo.param.UserRegisterParam;
import com.kains.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户接口，仅做登录注册演示
 */

@RestController
@Slf4j
@RequestMapping("/v1")
@Tag(name = "用户接口")
public class UserController {
    @Autowired
    private UserService userService;


    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<String> register(@Valid @RequestBody UserRegisterParam param) {
        userService.register(param);
        return Result.success(MsgConstant.REGISTER_SUCCESS);
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<String> login(@RequestBody UserLoginParam param) {
        String token = userService.login(param);
        if (token == null) {
            return Result.error(MsgConstant.LOGIN_FAILED);
        }
        return Result.success(token);
    }

}
