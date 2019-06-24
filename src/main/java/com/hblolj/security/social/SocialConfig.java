package com.hblolj.security.social;

import com.hblolj.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author: hblolj
 * @Date: 2019/3/18 10:10
 * @Description: SocialConfigurerAdapter 需要添加 spring-social-config 依赖
 * @Version:
 **/
@Order(1)
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter{

    @Resource(name = "socialDataSource")
    private DataSource dataSource;

    @Resource(name = "customSecurityProperties")
    private SecurityProperties securityProperties;

    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    /**
     * @param connectionFactoryLocator ConnectionFactory 定位器，系统种可能存在多种 ConnectionFactory( qq、微信、微博...)，
     *                                 选择一种来生成 Connection
     * @return
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        // dataSource 数据源
        // Encryptors.noOpText() 加密方式 - 不加密
        // 默认建表 sql 与 JdbcUsersConnectionRepository 类在用一个目录下
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        // 设置数据库表前缀，默认为 UserConnection，可以在这个基础上自定义前缀
        repository.setTablePrefix("yszn_");
        // 自定义默认注册逻辑，社交登录后在数据库中(按服务商类型 + 用户在服务商的唯一标识)没有找到对应的用户信息，
        // 调用自定义的 ConnectionSignUp 方法为用户在系统中注册一个账号
        if (connectionSignUp != null){
            repository.setConnectionSignUp(connectionSignUp);
        }
        return repository;
    }

    /**
     * SpringSocial 认证配置
     * 将该 Config 添加到 WebSecurityConfig 中
     * @return
     */
    @Bean
    public SpringSocialConfigurer customSocialSecurityConfig(){
        // return new SpringSocialConfigurer();
        CustomSpringSocialConfigurer configurer = new CustomSpringSocialConfigurer(securityProperties.getSocial().getFilterProcessesUrl());
        // 配置注册页面，当找不到用户时，会跳转到该页面
        configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        // 设置登录成功跳转地址
        configurer.postLoginUrl("/hello");
        return configurer;
    }

    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator){
        return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
    }

}
