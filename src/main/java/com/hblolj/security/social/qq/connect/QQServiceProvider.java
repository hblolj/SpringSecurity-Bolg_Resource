package com.hblolj.security.social.qq.connect;

import com.hblolj.security.social.qq.api.QQ;
import com.hblolj.security.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * @author: hblolj
 * @Date: 2019/3/18 9:20
 * @Description:
 * @Version:
 **/
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ>{

    private String appId;

    // 引导用户跳转到 QQ 上认证的链接，获取到授权码
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";

    // 使用授权码从 QQ 上获取 accessToken 的请求链接
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    /**
     * @param appId QQ 互联平台上申请的应用的 AppId
     * @param appSecret QQ 互联平台上申请的应用的 AppSecret
     */
    public QQServiceProvider(String appId, String appSecret) {
        super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }

}
