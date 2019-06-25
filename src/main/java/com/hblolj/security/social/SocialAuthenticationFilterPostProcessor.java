package com.hblolj.security.social;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * @author: hblolj
 * @Date: 2019/6/25 15:57
 * @Description:
 * @Version:
 **/
public interface SocialAuthenticationFilterPostProcessor {

    void process(SocialAuthenticationFilter socialAuthenticationFilter);
}
