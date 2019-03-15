package com.hblolj.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: hblolj
 * @Date: 2019/3/13 16:06
 * @Description:
 * @Version:
 **/
@RestController
public class TestController {

    @RequestMapping("/hello")
    public String hello(){
        return "Hello Spring Security";
    }
}
