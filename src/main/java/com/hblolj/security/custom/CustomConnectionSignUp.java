package com.hblolj.security.custom;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * @author: hblolj
 * @Date: 2019/3/19 15:29
 * @Description:
 * @Version:
 **/
@Component
public class CustomConnectionSignUp implements ConnectionSignUp {

    @Override
    public String execute(Connection<?> connection) {
        // TODO: 2019/3/19 根据用户在第三方服务商的信息给用户在业务服务器上注册一个账户
        // 返回用户在业务系统上的唯一标识，这里不做实际操作，模拟返回用户在第三方服务器上 nickName
        return connection.getDisplayName();
    }
}
