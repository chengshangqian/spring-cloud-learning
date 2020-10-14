package com.fandou.coffee.learning.springcloud.microservice.provider.controller;

import com.fandou.coffee.learning.springcloud.microservice.common.service.GreetingService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Swagger在线文档API演示
 */
@Api(tags = "Swagger在线文档API演示")
@RestController
@RequestMapping("/swagger3")
public class Swagger3Controller {
    // 日志
    private static final Logger LOGGER = LoggerFactory.getLogger(Swagger3Controller.class);

    // 问候服务
    @Autowired
    private GreetingService greetingService;

    // 打印端口，集群时判断负载均衡
    @Value("${server.port}")
    private String port;

    /**
     * 和访客说hello
     *
     * @param visitor
     * @return
     */
    @ApiOperation(value = "欢迎访客", notes = "对来访的互联网用户，返回合适的欢迎语.", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "visitor", value = "访客昵称", paramType = "path", required = true, dataTypeClass = String.class)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功", response = String.class)
    })
    @GetMapping("/hello/{visitor}")
    public String hello(@PathVariable("visitor") String visitor) {

        String result = String.format("server[%s] => %s", port, greetingService.greeting(visitor));

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(result);
        }

        return result;
    }

    /**
     * 和访客说hi
     *
     * @param visitor
     * @return
     */
    @ApiOperation(value = "欢迎访客", notes = "对来访的互联网用户，返回合适的欢迎语.")
    @ApiImplicitParams({@ApiImplicitParam(name = "visitor", value = "访客昵称")})
    @ApiResponses({@ApiResponse(code = 200, message = "获取成功")})
    @GetMapping("/hi/{visitor}")
    public String hi(@PathVariable("visitor") String visitor) {

        String result = String.format("server[%s] => %s", port, greetingService.greeting(visitor));

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(result);
        }

        return result;
    }

    /**
     * 忽略这个接口,不生成api文档
     *
     * @return
     */
    @ApiIgnore
    @GetMapping("/ignore")
    public String ignore() {
        return "ignore";
    }
}
