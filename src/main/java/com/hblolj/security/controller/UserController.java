package com.hblolj.security.controller;

import com.hblolj.security.support.SocialUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: hblolj
 * @Date: 2019/3/19 14:01
 * @Description:
 * @Version:
 **/
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @PostMapping("/regist")
    public void regist(@RequestParam String username, @RequestParam String password, HttpServletRequest request){

        log.info("regist");

        // TODO: 2019/3/19 注册用户
        // 不管是注册还是绑定，都会获取到用户在业务系统中的唯一标识
        // 注册完成后进行绑定
        providerSignInUtils.doPostSignUp(username, new ServletWebRequest(request));
    }

    @GetMapping("/getSocialUserInfo")
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request){

        log.info("getSocialUserInfo");

        SocialUserInfo socialUserInfo = new SocialUserInfo();

        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));

        socialUserInfo.setProviderId(connection.getKey().getProviderId());
        socialUserInfo.setProviderUserId(connection.getKey().getProviderUserId());
        socialUserInfo.setNickName(connection.getDisplayName());
        socialUserInfo.setHeadImg(connection.getImageUrl());

        return socialUserInfo;
    }
}
