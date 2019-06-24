package com.hblolj.security.social.qq.connect;

import com.hblolj.security.social.qq.api.QQ;
import com.hblolj.security.social.qq.api.QQUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author: hblolj
 * @Date: 2019/3/18 9:29
 * @Description:
 *  1. 测试 Api 的使用
 *  2. 获取用户唯一标识用来构建 ConnectionKey
 *  3. 绑定解绑
 *  4. 向 Service Provider 发送消息更新用户信息
 * @Version:
 **/
@Slf4j
public class QQAdapter implements ApiAdapter<QQ>{

    /**
     * 测试 Api 获取服务是否可以正常使用
     * @param api
     * @return
     */
    @Override
    public boolean test(QQ api) {
        return true;
    }

    /**
     * 用户授权获取到授权码，通过授权码获取到 AccessToken后，Create Connection 时，会调用这里
     * Create Connection 主要步骤
     * 1. super(apiAdapter);
     * 2. this.serviceProvider = serviceProvider;
     * 3. initAccessTokens(accessToken, refreshToken, expireTime);
     * 4. initApi(); -> 初始化 Api
     * 5. initApiProxy();
     * 6. initKey(providerId, providerUserId); -> 在这里当 providerUserId 为 null 时会调用本方法获取 providerUserId
     * @param api
     * @param connectionValues
     */
    @Override
    public void setConnectionValues(QQ api, ConnectionValues connectionValues) {

        QQUserInfo userInfo = api.getUserInfo();

        log.info("UserInfo: {}", userInfo.toString());

        connectionValues.setDisplayName(userInfo.getNickname());
        connectionValues.setImageUrl(userInfo.getFigureurl_qq_1());
        // 用户个人主页地址，qq 没有个人主页，所以这里不设置
        connectionValues.setProfileUrl(null);
        // 用户在服务商(QQ)上的唯一标示(openId)
        connectionValues.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        // 绑定解绑使用
        return null;
    }

    @Override
    public void updateStatus(QQ api, String s) {
        // 给服务商发送消息更新用户在服务商上的状态，QQ 没有改功能 do nothing
    }
}
