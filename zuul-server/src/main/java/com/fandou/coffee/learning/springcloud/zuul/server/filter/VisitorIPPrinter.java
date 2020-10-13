package com.fandou.coffee.learning.springcloud.zuul.server.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 来访者的IP打印：路有前执行过滤器
 */
@Component
public class VisitorIPPrinter extends ZuulFilter {

    /**
     * 过滤器类型
     * pre - 前置过滤器，在请求被路由前执行，通常用于处理身份认证，日志记录等；
     * route - 在路由执行后，服务调用前被调用；
     * post - 在route或error执行后被调用，一般用于收集服务信息，统计服务性能指标等，也可以对response结果做特殊处理；
     * error - 任意一个filter发生异常的时候执行或远程服务调用没有反馈的时候执行（超时），通常用于处理异常。
     * static - 见ZuulFilter的子类StaticResponseFilter
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 同类型过滤器中的执行顺序：越小越优先执行
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否开启过滤
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤的业务逻辑: 打印以下来访者的IP地址
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("VisitorIPPrinter is running...");

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        String servletPath = request.getServletPath();

        System.out.println("servletPath => " + servletPath);

        // 不处理非consumer的请求
        if(!servletPath.startsWith("/api")){
            return null;
        }

        System.out.println("remoteAddress => " + request.getRemoteAddr());

        // 返回值属于后续扩展保留，目前返回null即可
        return null;
    }
}
