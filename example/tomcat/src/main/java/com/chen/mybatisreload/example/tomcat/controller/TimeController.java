package com.chen.mybatisreload.example.tomcat.controller;

import com.chen.mybatisreload.core.TimeApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/time")
    public String time() {
        TimeApi timeApi = new TimeApi();
        return timeApi.getCurrentTime();
    }
}
