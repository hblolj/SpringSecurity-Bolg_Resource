package com.hblolj.security.logout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import sun.rmi.runtime.Log;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: hblolj
 * @Date: 2019/3/16 10:28
 * @Description:
 * @Version:
 **/
@Slf4j
public class DefaultLogoutSuccessHandler implements LogoutSuccessHandler{

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        log.info("Logout Success!");
    }
}
