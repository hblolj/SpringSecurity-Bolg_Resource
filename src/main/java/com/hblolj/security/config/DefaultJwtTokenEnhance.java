package com.hblolj.security.config;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: hblolj
 * @Date: 2019/6/26 16:31
 * @Description:
 * @Version:
 **/
public class DefaultJwtTokenEnhance implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        // Token 增强
        Map<String, Object> information = accessToken.getAdditionalInformation();
        if (null == information){
            information = new HashMap<>();
        }
        information.put("company", "昀尚智能");
        return accessToken;
    }
}
