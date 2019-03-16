package com.hblolj.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: hblolj
 * @Date: 2019/3/16 9:09
 * @Description:
 * @Version:
 **/
@RequestMapping("/session")
@RestController
public class SessionController {

    @GetMapping("/invalid")
    public String sessionInvalid(){
        return "Session Invalid";
    }
}
