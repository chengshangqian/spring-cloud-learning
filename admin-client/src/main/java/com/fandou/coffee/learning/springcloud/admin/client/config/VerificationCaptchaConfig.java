package com.fandou.coffee.learning.springcloud.admin.client.config;

import com.fandou.coffee.learning.springcloud.admin.client.filter.VerificationCaptchaFilter;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 图片验证码配置类
 */
@Configuration
public class VerificationCaptchaConfig{

    @Bean
    public Producer captcha(){
        // 验证码参数
        Properties properties = new Properties();
        // 验证码图片宽度
        properties.setProperty(Constants.KAPTCHA_IMAGE_WIDTH,"150");
        // 验证码图片高度
        properties.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT,"50");
        // 验证码字符集
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING,"0123456789");
        // 验证码字符长度
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH,"4");

        Config config = new Config(properties);

        // 默认的验证码图片生成器
        DefaultKaptcha captchaProducer = new DefaultKaptcha();
        captchaProducer.setConfig(config);

        return captchaProducer;
    }

    /**
     * 图片验证码过滤器
     *
     * @return
     */
    @Bean
    public VerificationCaptchaFilter verificationCaptchaFilter(){
        return new VerificationCaptchaFilter();
    }
}
