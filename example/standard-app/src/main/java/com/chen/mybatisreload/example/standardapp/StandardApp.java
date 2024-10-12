package com.chen.mybatisreload.example.standardapp;

import com.chen.mybatisreload.example.standardapp.entity.User;
import com.chen.mybatisreload.example.standardapp.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class StandardApp {
    public static void main(String[] args) {
        String resource = "mybatis-config.xml";
        InputStream is = StandardApp.class.getResourceAsStream("/" + resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectByPrimaryKey(12345);
        System.out.println(user);
    }
}
