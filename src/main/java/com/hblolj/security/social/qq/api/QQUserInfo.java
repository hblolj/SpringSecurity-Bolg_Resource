package com.hblolj.security.social.qq.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author: hblolj
 * @Date: 2019/3/16 16:12
 * @Description: 根据 QQ 互联平台抽象出的 QQ 用户基础信息 @link http://wiki.connect.qq.com/get_user_info
 * @Version:
 **/
@Data
public class QQUserInfo {

    private String openId;

    private String ret;

    private String msg;

    private String nickname;

    private String figureurl;

    private String figureurl_1;

    private String figureurl_2;

    private String figureurl_qq_1;

    private String figureurl_qq_2;

    private String gender;

    @Override
    public String toString() {
        return "QQUserInfo{" +
                "openId='" + openId + '\'' +
                ", ret='" + ret + '\'' +
                ", msg='" + msg + '\'' +
                ", nickname='" + nickname + '\'' +
                ", figureurl='" + figureurl + '\'' +
                ", figureurl_1='" + figureurl_1 + '\'' +
                ", figureurl_2='" + figureurl_2 + '\'' +
                ", figureurl_qq_1='" + figureurl_qq_1 + '\'' +
                ", figureurl_qq_2='" + figureurl_qq_2 + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
