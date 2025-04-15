package com.kains.pojo.param;

/**
 * @author kains
 * @date 2021/11/15
 */


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 用户注册参数
 */

@Data
@Schema( description = "用户注册参数")
public class UserRegisterParam {

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_]{6,10}$",
            message = "用户名必须为6-10位字母、数字或下划线")
    private String username;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20,message = "密码长度必须在6-20位之间")
    private String password;

    @Schema(description = "角色类型")
    @NotNull(message = "角色类型不能为空")
    private Integer role;
}