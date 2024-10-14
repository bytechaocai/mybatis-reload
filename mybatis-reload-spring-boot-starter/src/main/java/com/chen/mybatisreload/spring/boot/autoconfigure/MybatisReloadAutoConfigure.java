package com.chen.mybatisreload.spring.boot.autoconfigure;

import com.chen.mybatisreload.core.MyBatisReloadService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConditionalOnProperty(name = "mybatis.reload.enable", value = "true")
@ConditionalOnBean(SqlSessionFactory.class)
@AutoConfiguration
@EnableConfigurationProperties(MybatisReloadProperties.class)
public class MybatisReloadAutoConfigure {
    @Bean
    public MyBatisReloadService getMyBatisReloadService(@Autowired SqlSessionFactory sqlSessionFactory) {
        return new MyBatisReloadService(sqlSessionFactory);
    }
}
