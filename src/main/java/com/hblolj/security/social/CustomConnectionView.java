package com.hblolj.security.social;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author: hblolj
 * @Date: 2019/3/19 17:31
 * @Description:
 * @Version:
 **/
@Slf4j
public class CustomConnectionView extends AbstractView{

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        httpServletResponse.setContentType("text/html;charset=UTF-8");

        if (model.get("connections") == null){
            // 解绑
            httpServletResponse.getWriter().write("<h3>解绑成功</h3>");
        }else {
            httpServletResponse.getWriter().write("<h3>绑定成功</h3>");
        }
    }
}
