package com.chen.mybatisreload.example.standardapp.mapper;

import com.chen.mybatisreload.example.standardapp.entity.User;

/**
 * 用户表映射。
 *
 * @author chen
 */
public interface UserMapper {
    User selectByPrimaryKey(long id);
}
