package com.chen.mybatisreload.example.tomcat.service;

import com.chen.mybatisreload.example.standardapp.entity.User;
import com.chen.mybatisreload.example.standardapp.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public User queryUserById(String id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
