package com.hblolj.security.browser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author: hblolj
 * @Date: 2019/3/14 10:07
 * @Description:
 * @Version:
 **/
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin() // 指定登录认证方式为表单登录
//                .loginPage("http://www.baidu.com") //指定自定义登录页面地址，一般前后端分离，这里就用不到了
                .loginProcessingUrl("/authentication/form") // 自定义表单登录的 action 地址，默认是 /login
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/page/login.html").permitAll() // 允许登录页面不需要认证就可以访问，不然会死循环导致重定向次数过多
                .anyRequest() // 对所有的请求
                .authenticated(); // 都进行认证
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(authenticationEntryPoint); // 实现了 EntryPoint 对 loginPage 有覆盖作用，loginPage 不生效
    }
}
