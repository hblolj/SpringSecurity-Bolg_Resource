package com.hblolj.security.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author: hblolj
 * @Date: 2019/3/16 9:13
 * @Description: Session 并发登录时的处理策略
 * @Version:
 **/
public class DefaultExpiredSessionStrategy implements SessionInformationExpiredStrategy{

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        event.getResponse().setCharacterEncoding("UTF-8");
        event.getResponse().getWriter().write("并发登录!");
    }
}
