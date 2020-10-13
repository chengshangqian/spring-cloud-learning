package com.fandou.coffee.learning.springcloud.admin.client.filter;

import com.fandou.coffee.learning.springcloud.admin.client.exception.VerificationCaptchaException;
import com.google.code.kaptcha.Constants;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 验证码过滤器
 */
public class VerificationCaptchaFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获得请求路径的uri
        String uri = request.getRequestURI();

        // 拦截POST请求登录
        String method = request.getMethod().toUpperCase();

        // 如果是登录且是POST方法
        if (uri.endsWith("/login") && RequestMethod.POST.toString().equals(method)) {
            try {
                // 校验验证码
                verificationCaptcha(request);
                // 验证通过，继续登录
                filterChain.doFilter(request,response);
            }
            catch (VerificationCaptchaException e) {
                // 校验失败，抛出异常
                ((AuthenticationFailureHandler) (req, res, exception) -> {
                    res.setContentType("application/json;charset=UTF-8");
                    res.setStatus(401);
                    res.getWriter().write("{\"error_code\":401,\"name\":\"" +
                            exception.getClass() +
                            ",\"message\":\"" +
                            exception.getMessage() + "\"}");
                }).onAuthenticationFailure(request,response,e);
            }
        }
        else{
            // 其它非登录情况
            filterChain.doFilter(request,response);
        }
    }

    private void verificationCaptcha(HttpServletRequest request) throws VerificationCaptchaException {
        // 会话
        HttpSession session = request.getSession();

        // 会话中的验证码：如果存在，获取后清除
        String captchaSession = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);// 服务器保存的验证码
        if(!StringUtils.isEmpty(captchaSession)){
            session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
        }

        // 验证码生成时间：用于计算验证码是否过期
        Long captchaMillionsSession = (Long) session.getAttribute(Constants.KAPTCHA_SESSION_DATE);// 验证码生成时间:用于判断有效期
        if(!StringUtils.isEmpty(captchaMillionsSession)){
            // 获取后先清除
            session.removeAttribute(Constants.KAPTCHA_SESSION_DATE);

            // 计算验证码是否过期
            long duration = (1000 * 60 * 5); // 假设有效期为5分钟
            if(duration - (System.currentTimeMillis() - captchaMillionsSession) < 0){
                // 验证码失效
                throw new VerificationCaptchaException();
            }
        }

        // 对验证码校验：验证失败抛出异常
        String captcha = request.getParameter("captcha");// 用户输入的验证码
        if(StringUtils.isEmpty(captcha) || StringUtils.isEmpty(captchaSession) || !captcha.equals(captchaSession)){
            throw new VerificationCaptchaException();
        }
    }
}
