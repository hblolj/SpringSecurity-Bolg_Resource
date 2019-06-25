package com.hblolj.security.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author: hblolj
 * @Date: 2019/3/18 14:07
 * @Description:
 * @Version:
 **/
public class CustomSpringSocialConfigurer extends SpringSocialConfigurer{

    // 自定义 SocialAuthenticationFilter 拦截的 Url
    private String filterProcessesUrl;

    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    public CustomSpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    @Override
    protected <T> T postProcess(T object) {

        // 父类 SpringSocialConfigurer 的 107 行 addFilterBefore 调用，参数是 Filter，需要返回 Filter
        // 因为继承了 SpringSocialConfigurer，所以会 在 configure 方法调用时，被调用
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        // 给 filter 设置拦截目标
        filter.setFilterProcessesUrl(filterProcessesUrl);
        // 给 filter 设置成功处理器
        if (socialAuthenticationFilterPostProcessor != null){
            socialAuthenticationFilterPostProcessor.process(filter);
        }
        return (T) filter;
    }

    public SocialAuthenticationFilterPostProcessor getSocialAuthenticationFilterPostProcessor() {
        return socialAuthenticationFilterPostProcessor;
    }

    public void setSocialAuthenticationFilterPostProcessor(SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor) {
        this.socialAuthenticationFilterPostProcessor = socialAuthenticationFilterPostProcessor;
    }
}
