package com.kains.pojo.entity;

/**
 * @author kains
 * @date 2021/12/10
 */

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体类
 * 对应数据库表 users
 */

@TableName("users")
@Data
@Schema(name = "用户实体类", description = "用户实体类")
public class UserEntity {
    @Schema(description = "用户id")
    private int userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "角色类型")
    private int role;

    @Schema(description = "用户状态")
    private int status;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
}