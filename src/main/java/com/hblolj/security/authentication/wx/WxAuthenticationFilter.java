package com.hblolj.security.authentication.wx;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: hblolj
 * @Date: 2019/3/16 11:25
 * @Description:
 * @Version:
 **/
public class WxAuthenticationFilter extends AbstractAuthenticationProcessingFilter{

    // TODO: 2019/3/15 该参数可以抽取成配置，最后通过配置文件进行修改，这样作为共用组件只需要实现一个 default，具体值可以有调用者指定
    private String wxParameter = "wxOpenId";

    private boolean postOnly = true;

    /**
     * 通过构造函数指定该 Filter 要拦截的 url 和 httpMethod
     */
    protected WxAuthenticationFilter() {
        // TODO: 2019/3/15 pattern 可以抽取成配置，最后通过配置文件进行修改，这样作为共用组件只需要实现一个 default，具体值可以有调用者指定
        super(new AntPathRequestMatcher("/authentication/wx", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        // 当设置该 filter 只拦截 post 请求时，符合 pattern 的非 post 请求会触发异常
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {

            // 1. 从请求中获取参数 mobile + smsCode
            String openId = obtainMobile(request);
            if (openId == null){
                openId = "";
            }

            openId = openId.trim();

            // 2. 封装成 Token 调用 AuthenticationManager 的 authenticated 方法，该方法中根据 Token 的类型去调用对应 Provider 的 authenticated
            WxAuthenticationToken token = new WxAuthenticationToken(wxParameter);
            this.setDetails(request, token);

            // 3. 返回 authenticated 方法的返回值
            return this.getAuthenticationManager().authenticate(token);
        }
    }

    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(wxParameter);
    }

    protected void setDetails(HttpServletRequest request, WxAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public String getWxParameter() {
        return wxParameter;
    }

    public void setWxParameter(String wxParameter) {
        Assert.hasText(wxParameter, "mobileParameter parameter must not be empty or null");
        this.wxParameter = wxParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}
