package com.hblolj.security.social.qq.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * @author: hblolj
 * @Date: 2019/3/16 16:12
 * @Description:
 * @Version:
 **/
@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ{

    /**
     * 整体流程
     * accessToken 是用户授权从 QQ 服务器中获取
     * 使用 accessToken 获取用户 openId
     * 然后使用用户 openId 获取用户基本信息
     */

    // 根据 access_token 获取 openId
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;

    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accessToken, String appId) {
        // 这里父类一个参数的构造函数，会默认调用两个参数的构造函数，策略会自定选择 TokenStrategy.AUTHORIZATION_HEADER
        // 将请求参数放到请求头中，但是通过 QQ 互联开放平台的文档，我们发现其要求参数需要放到参数中，所以这里手动指定下策略
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        // 通过 accessToken 获取 openId
        String url = String.format(URL_GET_OPENID, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);
        log.info("OpenId Result: {}", result);
        // eg: callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} )
        String openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
        log.info("OpenId Substring: {}", openId);
        this.openId = openId;

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public QQUserInfo getUserInfo() {

        // 父类的构造函数已经帮我们将 accessToken 放在请求中
        String url = String.format(URL_GET_USERINFO, appId, openId);

        String result = getRestTemplate().getForObject(url, String.class);

        try {
            QQUserInfo userInfo = objectMapper.readValue(result, QQUserInfo.class);
            userInfo.setOpenId(this.openId);
            return userInfo;
        } catch (IOException e) {
            throw new RuntimeException("获取用户 QQ 基本信息失败!");
        }
    }
}
