package com.hblolj.security.social;

import com.hblolj.security.properties.SecurityConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author: hblolj
 * @Date: 2019/6/25 16:56
 * @Description:
 * @Version:
 **/
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor{

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (StringUtils.equals(beanName, "customSocialSecurityConfig")){
            CustomSpringSocialConfigurer config = (CustomSpringSocialConfigurer)bean;
            // 设置跳转到指定接口，而不是之前的注册页面。因为当前环境是 Token，不是 Web
            config.signupUrl(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL);
            return config;
        }
        return bean;
    }
}
