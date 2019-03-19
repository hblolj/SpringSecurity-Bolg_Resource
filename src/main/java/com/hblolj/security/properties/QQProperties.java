package com.hblolj.security.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @author: hblolj
 * @Date: 2019/3/18 11:03
 * @Description:
 * @Version:
 **/
public class QQProperties extends SocialProperties{

    /**
     * 应用服务标识，QQ 默认为 qq
     */
    private String providerId = "qq";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
