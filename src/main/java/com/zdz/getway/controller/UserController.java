package com.zdz.getway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangdezhi
 * @date 2020-06-09
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/hello")
    public String hello(){
        return "======";
    }

}
