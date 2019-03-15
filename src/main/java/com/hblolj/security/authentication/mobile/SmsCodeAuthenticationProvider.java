package com.hblolj.security.authentication.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author: hblolj
 * @Date: 2019/3/15 10:58
 * @Description: 短信验证码认证的真正校验逻辑，实际上是按手机号查询用户，短信验证码过滤器在这之前
 * @Version:
 **/
public class SmsCodeAuthenticationProvider implements AuthenticationProvider{

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        SmsCodeAuthenticationToken token = (SmsCodeAuthenticationToken) authentication;

        // 对用户进行认证
        UserDetails userDetails = userDetailsService.loadUserByUsername((String) token.getPrincipal());
        if (userDetails == null){
            throw new InternalAuthenticationServiceException("未找到对应的用户信息!");
        }

        // 构造新的 Token，采用该构造函数时，会默认将 authenticated 参数置为 true
        SmsCodeAuthenticationToken authenticationToken = new SmsCodeAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationToken.setDetails(token.getDetails());

        // TODO: 2019/3/15 如果认证方式与密码相关，这里可以对密码进行校验 @PasswordEncoder

        // TODO: 2019/3/15 可以校验账号状态: 启用、冻结等等
//        userDetails.isAccountNonExpired(); 账号是否过期
//        userDetails.isAccountNonLocked(); 账号有无冻结
//        userDetails.isCredentialsNonExpired(); 账号密码是否过期
//        userDetails.isEnabled(); 账号是否启用

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        // aClass 是 authenticate 方法参数的类型
        // 此处判断 aClass 是否是该 Provider 对应的 Token 的子类或者子接口，只有通过了，才会调用 authenticate 方法去认证
        return SmsCodeAuthenticationToken.class.isAssignableFrom(aClass);
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
