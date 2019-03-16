package com.hblolj.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author: hblolj
 * @Date: 2019/3/15 18:12
 * @Description: 指定全局默认的 UserDetailService 与 PasswordEncoder
 * @Version:
 **/
@Configuration
public class GlobalAuthenticationConfigurer extends GlobalAuthenticationConfigurerAdapter {

    private final UserDetailsService userService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public GlobalAuthenticationConfigurer(@Qualifier("formUserDetailService") UserDetailsService userDetailsService,
                                          PasswordEncoder passwordEncoder) {
        this.userService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
}
