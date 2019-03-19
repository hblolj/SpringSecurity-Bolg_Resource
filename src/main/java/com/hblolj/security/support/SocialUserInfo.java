package com.hblolj.security.support;

import lombok.Data;

/**
 * @author: hblolj
 * @Date: 2019/3/19 14:13
 * @Description:
 * @Version:
 **/
@Data
public class SocialUserInfo {

    private String providerId;

    private String providerUserId;

    private String nickName;

    private String headImg;

    public SocialUserInfo() {
    }

    public SocialUserInfo(String providerId, String providerUserId, String nickName, String headImg) {
        this.providerId = providerId;
        this.providerUserId = providerUserId;
        this.nickName = nickName;
        this.headImg = headImg;
    }
}
