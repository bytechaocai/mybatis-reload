package com.chen.mybatisreload.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeApi.class);

    public String getCurrentTime() {
        LOGGER.info("获取当前时间");
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss").format(new Date());
    }
}
