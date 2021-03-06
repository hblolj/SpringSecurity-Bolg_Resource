package com.hblolj.security.authentication.wx;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author: hblolj
 * @Date: 2019/3/16 11:26
 * @Description:
 * @Version:
 **/
public class WxAuthenticationToken extends AbstractAuthenticationToken{

    private final Object principal;

    public WxAuthenticationToken(Object openId) {
        super((Collection)null);
        this.principal = openId;
        this.setAuthenticated(false);
    }

    public WxAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    public Object getPrincipal() {
        return this.principal;
    }
}
