package com.hblolj.security.social;

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

    public CustomSpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    @Override
    protected <T> T postProcess(T object) {

        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);

        filter.setFilterProcessesUrl(filterProcessesUrl);

        return (T) filter;
    }
}
