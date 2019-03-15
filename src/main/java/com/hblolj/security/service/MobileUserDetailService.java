package com.hblolj.security.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author: hblolj
 * @Date: 2019/3/15 14:08
 * @Description:
 * @Version:
 **/
@Component("mobileUserDetailService")
public class MobileUserDetailService implements UserDetailsService{

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        // TODO: 2019/3/15 按手机号查询用户信息
        return new User("4000368163", "123", true, true, true,
                true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
