package com.hblolj.security.controller;

import com.hblolj.security.utils.IPUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: hblolj
 * @Date: 2019/3/13 16:06
 * @Description:
 * @Version:
 **/
@RestController
public class TestController {

    @RequestMapping("/hello")
    public String hello(HttpServletRequest request){
        String ipAddr = IPUtil.getIpAddr(request);
        System.out.println(ipAddr);
        return "Hello Spring Security";
    }
}
