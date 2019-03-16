package com.hblolj.security.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author: hblolj
 * @Date: 2019/3/16 11:26
 * @Description:
 * @Version:
 **/
@Component("wxUserDetailService")
public class WxUserDetailService implements UserDetailsService{

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO: 2019/3/15 按微信 OpenId 查询用户信息
        return new User("4000368163", "999", true, true, true,
                true, AuthorityUtils.commaSeparatedStringToAuthorityList("wx"));
    }
}
