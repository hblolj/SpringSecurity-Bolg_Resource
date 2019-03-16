package com.hblolj.security.authentication.wx;

import com.hblolj.security.authentication.mobile.SmsCodeAuthenticationFilter;
import com.hblolj.security.authentication.mobile.SmsCodeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author: hblolj
 * @Date: 2019/3/16 11:25
 * @Description:
 * @Version:
 **/
@Component
public class WxAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    private UserDetailsService wxUserDetailService;

    @Override
    public void configure(HttpSecurity builder) throws Exception {

        // 1. 初始化 WxAuthenticationFilter
        WxAuthenticationFilter filter = new WxAuthenticationFilter();
        filter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);

        // 2. 初始化 WxAuthenticationProvider
        WxAuthenticationProvider provider = new WxAuthenticationProvider();
        provider.setUserDetailsService(wxUserDetailService);

        // 3. 将设置完毕的 Filter 与 Provider 添加到配置中，将自定义的 Filter 加到 UsernamePasswordAuthenticationFilter 之前
        builder.authenticationProvider(provider).addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
