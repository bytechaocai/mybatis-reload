package com.chen.mybatisreload.example.tomcat.controller;


import com.chen.mybatisreload.example.standardapp.entity.User;
import com.chen.mybatisreload.example.tomcat.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/queryUserById")
    public User queryUserById(@RequestParam String id) {
        return userService.queryUserById(id);
    }
}
