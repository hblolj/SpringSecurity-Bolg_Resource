package com.hblolj.security.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: hblolj
 * @Date: 2019/3/19 17:02
 * @Description:
 * @Version:
 **/
@Component("connect/status")
public class CustomConnectionStatusView extends AbstractView{

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 查询出当前用户在系统中的第三方服务的绑定的绑定情况
     * @param model
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws Exception
     */
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

//        model.addAttribute("providerIds", this.connectionFactoryLocator.registeredProviderIds());
//        model.addAttribute("connectionMap", connections);

        // 获取到用户在当前系统中所有第三方登录关联记录
        Map<String, List<Connection<?>>> connections = (Map<String, List<Connection<?>>>) model.get("connectionMap");
        Map<String, Boolean> result = new HashMap<>();
        for (String key : connections.keySet()) {
            result.put(key, org.apache.commons.collections.CollectionUtils.isNotEmpty(connections.get(key)));
        }

        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
