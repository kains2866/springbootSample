package com.kains.pojo.param;
/**
 * @author kains
 * @date 2021/11/15
 */

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录参数类,也就是VO
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户登录参数")
public class UserLoginParam {

    @Schema(description = "用户名", required = true)
    private String username;
    @Schema(description = "密码", required = true)
    private String password;
}
