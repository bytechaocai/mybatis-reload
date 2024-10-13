package com.chen.mybatisreload.example.tomcat.controller;

import com.chen.mybatisreload.core.MyBatisReloadService;
import com.chen.mybatisreload.core.bean.ReloadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class MyBatisController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisController.class);
    @Resource
    private MyBatisReloadService myBatisService;

    @GetMapping("/reloadMapperInterface")
    public ReloadContext reloadMapperInterface() {
        LOGGER.info("重新加载所有映射接口");
        ReloadContext reloadResult = myBatisService.reloadAllMapperInterface();
        LOGGER.info("加载完成");
        return reloadResult;
    }

    @PostMapping(value = "/reloadMapper", consumes = "application/json")
    public ReloadContext reloadMapper(@RequestBody ReloadContext request) {
        return myBatisService.reloadMapper(request);
    }
}
