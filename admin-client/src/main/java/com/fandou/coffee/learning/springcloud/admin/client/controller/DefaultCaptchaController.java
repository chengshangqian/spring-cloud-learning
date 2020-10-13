package com.fandou.coffee.learning.springcloud.admin.client.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@SessionAttributes(value = {Constants.KAPTCHA_SESSION_KEY,Constants.KAPTCHA_SESSION_DATE})
public class DefaultCaptchaController {
    @Autowired
    private Producer defaultCaptchaProducer;

    @GetMapping("/captcha.jpg")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException{

        // 设置请求结果有效时间以及缓存设置
        response.setDateHeader("Expires", 0);
        // 设置多个标准的HTTP/1.1 no-cache头.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // 设置多个IE扩展的HTTP/1.1 no-cache头(使用addHeader方式).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // 设置标准的HTTP/1.0 no-cache头.
        response.setHeader("Pragma", "no-cache");

        // 响应类型：图片
        response.setContentType("image/jpeg");

        // 验证码文本
        String captchaText = defaultCaptchaProducer.createText();

        // 放入会话中
        model.addAttribute(Constants.KAPTCHA_SESSION_KEY,captchaText);
        // 生成时间：用于后续计算验证码是否过期
        model.addAttribute(Constants.KAPTCHA_SESSION_DATE,System.currentTimeMillis());

        // 生成验证码图片
        BufferedImage bi = defaultCaptchaProducer.createImage(captchaText);

        ServletOutputStream out = response.getOutputStream();

        // 返回图片
        ImageIO.write(bi, "jpg", out);
    }
}
