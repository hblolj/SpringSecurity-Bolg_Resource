package com.hblolj.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author: hblolj
 * @Date: 2019/3/14 10:40
 * @Description:
 * @Version:
 **/
@Component("formUserDetailService")
public class FormUserDetailService implements UserDetailsService, SocialUserDetailsService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 表单登录
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        // TODO: 2019/3/14 按参数 s 从数据库查找用户信息
        System.out.println("s formUserDetailService");
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 模拟从数据库中取出的密码是已经加密过的密码
        String password = passwordEncoder.encode("123");

        // 返回的是 org.springframework.security.core.userdetails 下 User 类
        // 构造方法传入的三个参数分别是用户名、密码、权限集合
        User user = new User(s, password, true, true, true,
                true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));

        return user;
    }

    /**
     * 社交登录
     * @param userId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {

        //        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 模拟从数据库中取出的密码是已经加密过的密码
        String password = passwordEncoder.encode("123");

        // 返回的是 org.springframework.security.core.userdetails 下 User 类
        // 构造方法传入的三个参数分别是用户名、密码、权限集合
        SocialUser user = new SocialUser(userId, password, true, true,
                true, true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));

        return user;
    }
}
