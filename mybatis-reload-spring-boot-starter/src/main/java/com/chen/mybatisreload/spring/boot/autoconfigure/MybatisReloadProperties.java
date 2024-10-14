package com.chen.mybatisreload.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mybatis.reload")
public class MybatisReloadProperties {
    /**
     * 是否启用自动加载。
     */
    private boolean enabled;
}
