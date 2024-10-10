package com.chen.mybatisreload.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        System.out.println("hello");
        System.out.println("乱码测试");
        LOGGER.info("乱码test");
    }
}
