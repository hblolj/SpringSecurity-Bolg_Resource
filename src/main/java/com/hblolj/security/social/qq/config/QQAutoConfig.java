package com.hblolj.security.social.qq.config;

import com.hblolj.security.properties.QQProperties;
import com.hblolj.security.properties.SecurityProperties;
import com.hblolj.security.social.CustomConnectionView;
import com.hblolj.security.social.qq.connect.QQConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

import javax.annotation.Resource;

/**
 * @author: hblolj
 * @Date: 2019/3/18 11:08
 * @Description: 只有配置了 appId，该配置项才会生效
 * @Version:
 **/
@Order(2)
@Configuration
@ConditionalOnProperty(prefix = "hblolj.security.social.qq", name = "appId")
public class QQAutoConfig extends SocialAutoConfigurerAdapter{

    /**
     * 这里用 Resource 指定 name，因为在 SpringSocial 把下面有一个同名的 SecurityProperties
     */
    @Resource(name = "customSecurityProperties")
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {

        QQProperties qqProperties = securityProperties.getSocial().getQq();

        return new QQConnectionFactory(qqProperties.getProviderId(), qqProperties.getAppId(), qqProperties.getAppSecret());
    }

    /**
     * 可以自定义一个名称为 qqConnectedView 的 Bean 来覆盖此默认实现
     * callbackConnect 是解绑成功
     * @return
     */
    @Bean({"connect/callbackConnect"})
    @ConditionalOnMissingBean(name = "qqConnectView")
    public View qqConnectView(){
        return new CustomConnectionView();
    }

    /**
     * callbackConnected 是绑定成功
     * @return
     */
    @Bean({"connect/callbackConnected"})
    @ConditionalOnMissingBean(name = "qqConnectedView")
    public View qqConnectedView(){
        return new CustomConnectionView();
    }
}
