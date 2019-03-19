package com.hblolj.security.social.qq.connect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * @author: hblolj
 * @Date: 2019/3/18 14:52
 * @Description:
 * @Version:
 **/
@Slf4j
public class QQOAuth2Template extends OAuth2Template{

    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        // 只有该值为 true 时，在使用授权码获取 accessToken 时，才会携带 client_id 与 client_secret，而 QQ 访问 accessToken 的接口是需要这两个参数的
        setUseParametersForClientAuthentication(true);
    }

    /**
     * 默认创建的 RestTemplate 不支持 html/text，继承覆盖添加，而 QQ 服务商返回是以改格式返回的，所以重写添加支持
     * @return
     */
    @Override
    protected RestTemplate createRestTemplate() {

        RestTemplate restTemplate = super.createRestTemplate();

        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));

        return restTemplate;
    }

    /**
     * 实际上向服务商发起请求 AccessToken 的方法，包括对获取到的结果进行格式解析。
     * 默认实现是从 json 中以键值对形式获取，但是 QQ 返回的是在字符串中
     * eg: access_token=FE04************************CCE2&expires_in=7776000&refresh_token=88E4************************BE14
     * 所以对该方法进行重写
     * @param accessTokenUrl
     * @param parameters
     * @return
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {

        String result = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        log.info("获取 accessToken 的响应: " + result);

        // 按照 & 分隔符进行切割
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(result, "&");

        // 取出 = 号后面的字符串
        String access_token = StringUtils.substringAfterLast(items[0], "=");
        Long expires_in = Long.parseLong(StringUtils.substringAfterLast(items[1], "="));
        String refresh_token = StringUtils.substringAfterLast(items[2], "=");

        return new AccessGrant(access_token, null, refresh_token, expires_in);
    }

    /**
     * 使用授权码获取 AccessToken 的方法，主要负责参数的拼装与设置
     * 如果默认参数与服务提供商的提供的接口参数不一致，需要重写该方法
     * @param authorizationCode
     * @param redirectUri
     * @param additionalParameters
     * @return
     */
    @Override
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> additionalParameters) {
        return super.exchangeForAccess(authorizationCode, redirectUri, additionalParameters);
    }
}
