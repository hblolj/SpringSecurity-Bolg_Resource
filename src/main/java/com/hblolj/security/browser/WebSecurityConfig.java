package com.hblolj.security.browser;

import com.hblolj.security.authentication.mobile.SmsCodeAuthenticationConfig;
import com.hblolj.security.authentication.wx.WxAuthenticationConfig;
import com.hblolj.security.logout.DefaultLogoutSuccessHandler;
import com.hblolj.security.session.DefaultExpiredSessionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    @Autowired
    private SmsCodeAuthenticationConfig smsCodeAuthenticationConfig;

    @Autowired
    private WxAuthenticationConfig wxAuthenticationConfig;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.apply(smsCodeAuthenticationConfig) // 加载短信验证
                .and()
                .apply(wxAuthenticationConfig)
                .and()
                .formLogin() // 指定登录认证方式为表单登录
//                .loginPage("http://www.baidu.com") //指定自定义登录页面地址，一般前后端分离，这里就用不到了
                    .loginProcessingUrl("/authentication/form") // 自定义表单登录的 action 地址，默认是 /login
                    .successHandler(authenticationSuccessHandler)
                    .failureHandler(authenticationFailureHandler)
                .and()
                .sessionManagement()
                    .invalidSessionUrl("/session/invalid") // 设置 Session 失效后访问跳转的页面
                    .maximumSessions(1) // 设置同时在线的最大 Session 数
//                    .maxSessionsPreventsLogin(true) // 设置当 Session 数量达到最大数量时，阻止后面的登录
                    .expiredSessionStrategy(new DefaultExpiredSessionStrategy()) // 设置 Session 并发登录时处理策略
                    .and()
                    .and()
                .logout()
                    .logoutUrl("/quit") // 默认是 logout
                    .logoutSuccessUrl("http://www.baidu.com") // 设置退出成功跳转的页面
                    .logoutSuccessHandler(new DefaultLogoutSuccessHandler()) // 设置退出成功的处理策略
                    .deleteCookies("JSESSIONID") // 退出时删除客户端指定的 Cookie
                    .and()
                .authorizeRequests()
                    .antMatchers("/session/invalid").permitAll()
                    .antMatchers("/authentication/mobile").permitAll()
                    .antMatchers("/page/login.html").permitAll() // 允许登录页面不需要认证就可以访问，不然会死循环导致重定向次数过多
                    .anyRequest() // 对所有的请求
                    .authenticated() // 都进行认证
                .and()
                .csrf()
                    .disable();
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(authenticationEntryPoint); // 实现了 EntryPoint 对 loginPage 有覆盖作用，loginPage 不生效
    }
}
