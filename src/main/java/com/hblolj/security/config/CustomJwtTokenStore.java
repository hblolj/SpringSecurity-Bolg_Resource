package com.hblolj.security.config;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author: hblolj
 * @Date: 2019/6/26 17:07
 * @Description:
 * @Version:
 **/
public class CustomJwtTokenStore extends JwtTokenStore{

    /**
     * Create a JwtTokenStore with this token enhancer (should be shared with the DefaultTokenServices if used).
     * @param jwtTokenEnhancer
     */
    public CustomJwtTokenStore(JwtAccessTokenConverter jwtTokenEnhancer) {
        super(jwtTokenEnhancer);
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        // TODO: 2019/6/26 重写逻辑，根据 authentication 查询 Token，Token 存储在 Redis 中，与用户一一对应，失效就删除
        return super.getAccessToken(authentication);
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        // TODO: 2019/6/26 读取 RefreshToken 的调用时机在 refreshToken 时，会先查询 Refresh Token
        // TODO: 2019/6/26 在这里我们可以做前置的 RefreshToken 校验
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 获取当前用户，然后获取当前用户存储在 Redis 中的 RefreshToken，比较传递的 TokenValue 与 RefreshToken 的版本号是否一致
        return super.readRefreshToken(tokenValue);
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        super.storeAccessToken(token, authentication);
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        super.removeAccessToken(token);
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        super.storeRefreshToken(refreshToken, authentication);
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        super.removeRefreshToken(token);
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        // TODO: 2019/6/26 Token 刷新时，删除所有之前通过该 Refresh Token 构造的 Token
        super.removeAccessTokenUsingRefreshToken(refreshToken);
    }
}
