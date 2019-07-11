package com.hblolj.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author: hblolj
 * @Date: 2019/6/26 9:48
 * @Description:
 * @Version:
 **/
@Configuration
public class TokenConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private UserDetailsService formUserDetailService;

    @Bean
    @ConditionalOnProperty(prefix = "hblolj.security.oauth2", name = "storeType", havingValue = "redis")
    public TokenStore redisTokenStore(){return new RedisTokenStore(redisConnectionFactory);}

    @Bean
    @ConditionalOnProperty(prefix = "hblolj.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
    public TokenStore jwtTokenStore(JwtAccessTokenConverter jwtAccessTokenConverter){
        return new CustomJwtTokenStore(jwtAccessTokenConverter);
    }

    @Bean
    @ConditionalOnProperty(prefix = "hblolj.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

        // 使用 JWT 后，默认的流程只是校验 Token 的合法性，会使用 Token 中的 user_name 去走 UserDetailService 查询，
        // 默认 DefaultUserAuthenticationConverter 中的 UserDetailService 为 null，这里如果设置了 UserDetailService，
        // 则每次使用 Jwt Token 访问接口都会去进行查询，会有额外性能消耗，但是查询出的用户信息是最新的。

        // user_name 什么时候被 put 进 jwt token 的呢?
        // 也是 DefaultUserAuthenticationConverter 的 convertUserAuthentication 方法，在 Encode 之前，
        // 将 Authentication 的 Name 加入到 Paramter Map 中。具体时机在 JwtAccessTokenConverter 的 enhance 方法的 219 行。
        // 属于 Token Enhancer 阶段，最后转换为 JWT。

//        DefaultAccessTokenConverter accessTokenConverter = (DefaultAccessTokenConverter) converter.getAccessTokenConverter();
//        DefaultUserAuthenticationConverter userAuthenticationConverter = new DefaultUserAuthenticationConverter();
//        userAuthenticationConverter.setUserDetailsService(formUserDetailService);
//        accessTokenConverter.setUserTokenConverter(userAuthenticationConverter);

        // 设置签名，防止 jwt 被篡改
        converter.setSigningKey("zard");
        return converter;
    }

    @Bean
    @ConditionalOnProperty(prefix = "hblolj.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
    @ConditionalOnMissingBean(name = "jwtTokenEnhance")
    public TokenEnhancer jwtTokenEnhance(){
        return new DefaultJwtTokenEnhance();
    }
}
