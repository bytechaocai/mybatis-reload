package com.chen.mybatisreload.example.standardapp;

import com.chen.mybatisreload.example.standardapp.entity.User;
import com.chen.mybatisreload.example.standardapp.mapper.UserMapper;
import com.chen.mybatisreload.example.standardapp.utils.FakeDataUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Date;

public class StandardApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(StandardApp.class);

    public static void main(String[] args) throws InterruptedException {
        String resource = "mybatis-config.xml";
        InputStream is = StandardApp.class.getResourceAsStream("/" + resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = FakeDataUtil.getUser();
            LOGGER.info("user:{}", user);
            userMapper.insert(user);
            user.setUpdateTime(new Date());
            userMapper.updateByPrimaryKeySelective(user);
            userMapper.selectByPrimaryKey(user.getId());
            user.setDeleteTime(new Date());
            user.setDeleteStatus(1);
            userMapper.updateByPrimaryKeySelective(user);
            sqlSession.commit();
            LOGGER.info("mapper接口测试完成");
            Thread.sleep(3000);
            user = FakeDataUtil.getUser();
            sqlSession.insert("userMapper.insert", user);
            user.setUpdateTime(new Date());
            sqlSession.update("userMapper.updateByPrimaryKeySelective", user);
            sqlSession.selectOne("userMapper.selectByPrimaryKey", user.getId());
            user.setDeleteTime(new Date());
            user.setDeleteStatus(1);
            sqlSession.update("userMapper.updateByPrimaryKeySelective", user);
            sqlSession.commit();
            LOGGER.info("mapper测试");
        }
    }
}
