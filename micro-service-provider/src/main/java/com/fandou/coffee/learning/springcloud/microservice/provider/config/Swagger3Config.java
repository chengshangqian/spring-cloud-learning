package com.fandou.coffee.learning.springcloud.microservice.provider.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger在线API文档配置类
 * Swagger2 3.x版本的首页为/swagger-ui/index.html，2.x版本首页为/swagger-ui.html
 */
@Configuration
@EnableOpenApi
public class Swagger3Config {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.OAS_30) // Swagger2 2.x版本参数使用DocumentationType.SWAGGER_2
                .apiInfo(apiInfo())
                .select()

                // 指定包路径方式：该路径下所有的Controller类将被解析生成API文档
                //.apis(RequestHandlerSelectors.basePackage("com.fandou.coffee.learning.springcloud.microservice.provider.controller"))

                // 扫描注解方式：只有这些注解了@Api的类或注解了@ApiOperation的方法才扫描解析生成API文档
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class)
                       .and(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)))

                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("问候服务API文档")
                .description("问候服务API，主要用来向来访的用户打招呼.")
                .version("1.0")
                .build();
    }
}
