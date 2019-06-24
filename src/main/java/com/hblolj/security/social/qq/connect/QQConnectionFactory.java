package com.hblolj.security.social.qq.connect;

import com.hblolj.security.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @author: hblolj
 * @Date: 2019/3/18 10:01
 * @Description: 使用自定义的 ServiceProvider 与 Adapter 构建自定义 ConnectionFactory
 * @Version:
 **/
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ>{

    /**
     * @param providerId 服务提供商唯一标识
     * @param appId 本服务在服务提供商上注册的 appId
     * @param appSecret 本服务在服务提供商上注册的 appSecret
     */
    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        // serviceProvider 自定义服务提供商代理，获取从服务提供商获取数据的 Api 实现
        // apiAdapter 数据适配器，适配从服务商获取到用户基本信息到 Connection
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }
}
