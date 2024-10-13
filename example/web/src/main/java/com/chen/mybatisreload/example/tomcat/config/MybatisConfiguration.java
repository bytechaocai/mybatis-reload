package com.chen.mybatisreload.example.tomcat.config;

import com.chen.mybatisreload.core.MyBatisReloadService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class MybatisConfiguration {

    @Bean
    public MyBatisReloadService mybatisReloadService() {
        return new MyBatisReloadService();
    }

    @Bean
    public SqlSessionFactory getSqlSessionFactoryBean(@Autowired DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public DataSource getDataSource() {
        // 在tomcat中启动时，.data会在tomcat的bin目录下
        DriverManagerDataSource dataSource = new DriverManagerDataSource(System.getenv("url"));
        dataSource.setDriverClassName("org.h2.Driver");
        return dataSource;
    }

    @Bean
    public MapperScannerConfigurer getMapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.chen.mybatisreload.example.standardapp.mapper");
        return mapperScannerConfigurer;
    }
}
