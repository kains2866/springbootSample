package com.kains.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kains
 * @date 2021/12/10
 */


/**
 * 自定义OpenAPI配置
 * 对于实体pojo类，需要在类上添加@Schema注解，并设置title、description、example等属性。
 * 对于接口方法，需要在接口方法上添加@Operation注解，并设置summary、description、requestBody、response等属性。
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("？？？ API 文档")
                        .version("1.0.0")
                        .description("？？？系统API文档"));
    }
}
