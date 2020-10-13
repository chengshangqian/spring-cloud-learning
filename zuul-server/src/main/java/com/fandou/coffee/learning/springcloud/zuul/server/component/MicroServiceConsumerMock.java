package com.fandou.coffee.learning.springcloud.zuul.server.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 路由熔断提供者实现，针对服务路由失败，启动熔断功能
 */
@Component
public class MicroServiceConsumerMock implements FallbackProvider {

    /**
     * 返回需要配置熔断功能的路由服务
     *
     * @return
     */
    @Override
    public String getRoute() {
        // 返回具体的serviceId，如果是所有路由服务配置熔断功能，返回字符串*即可
        return "micro-service-consumer";
    }

    /**
     * 可以根据异常类型动态决定处理方式。
     *
     * @param route
     * @param cause
     * @return
     */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        System.out.println("ClientHttpResponse fallbackResponse(Throwable cause)");
        if(cause instanceof NullPointerException){
            List<Map<String, Object>> result = new ArrayList<>();
            Map<String, Object> data = new HashMap<>();
            data.put("message", "网关超时，请稍后重试");
            result.add(data);

            ObjectMapper mapper = new ObjectMapper();

            String msg = "";
            try {
                msg = mapper.writeValueAsString(result);
            } catch (JsonProcessingException e) {
                msg = "";
            }

            return this.executeFallback(HttpStatus.GATEWAY_TIMEOUT,
                    msg, "application", "json", "utf-8");
        }else{
            return this.fallbackResponse();
        }
    }

    /**
     * fallback逻辑。在早期版本中使用。
     * Edgware版本之后，ZuulFallbackProvider接口过期，提供了新的子接口FallbackProvider
     * 子接口中提供了方法ClientHttpResponse fallbackResponse(Throwable cause)。
     * 优先调用子接口新定义的fallback处理逻辑。
     */
    private ClientHttpResponse fallbackResponse() {
        System.out.println("ClientHttpResponse fallbackResponse()");

        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("message", "服务正忙，请稍后重试");
        result.add(data);

        ObjectMapper mapper = new ObjectMapper();

        String msg = "";
        try {
            msg = mapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            msg = "";
        }

        return this.executeFallback(HttpStatus.OK, msg,
                "application", "json", "utf-8");
    }

    /**
     * 具体处理过程。
     * @param status 容错处理后的返回状态，如200正常GET请求结果，201正常POST请求结果，404资源找不到错误等。
     *  使用spring提供的枚举类型对象实现。HttpStatus
     * @param contentMsg 自定义的响应内容。就是反馈给客户端的数据。
     * @param mediaType 响应类型，是响应的主类型， 如： application、text、media。
     * @param subMediaType 响应类型，是响应的子类型， 如： json、stream、html、plain、jpeg、png等。
     * @param charsetName 响应结果的字符集。这里只传递字符集名称，如： utf-8、gbk、big5等。
     * @return ClientHttpResponse 就是响应的具体内容。
     *  相当于一个HttpServletResponse。
     */
    private final ClientHttpResponse executeFallback(final HttpStatus status,
                                                     String contentMsg, String mediaType, String subMediaType, String charsetName) {
        return new ClientHttpResponse() {

            /**
             * 设置响应的头信息
             */
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders header = new HttpHeaders();
                MediaType mt = new MediaType(mediaType, subMediaType, Charset.forName(charsetName));
                header.setContentType(mt);
                return header;
            }

            /**
             * 设置响应体
             * zuul会将本方法返回的输入流数据读取，并通过HttpServletResponse的输出流输出到客户端。
             */
            @Override
            public InputStream getBody() throws IOException {
                String content = contentMsg;
                return new ByteArrayInputStream(content.getBytes());
            }

            /**
             * ClientHttpResponse的fallback的状态码 返回String
             */
            @Override
            public String getStatusText() throws IOException {
                return this.getStatusCode().getReasonPhrase();
            }

            /**
             * ClientHttpResponse的fallback的状态码 返回HttpStatus
             */
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return status;
            }

            /**
             * ClientHttpResponse的fallback的状态码 返回int
             */
            @Override
            public int getRawStatusCode() throws IOException {
                return this.getStatusCode().value();
            }

            /**
             * 回收资源方法
             * 用于回收当前fallback逻辑开启的资源对象的。
             * 不要关闭getBody方法返回的那个输入流对象。
             */
            @Override
            public void close() {
            }
        };
    }
}
