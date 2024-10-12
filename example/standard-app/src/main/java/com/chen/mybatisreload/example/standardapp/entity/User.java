package com.chen.mybatisreload.example.standardapp.entity;

import java.util.Date;

/**
 * 用户实体类。
 *
 * @author chen
 */
public class User {
    /**
     * 用户标识。
     */
    private String id;
    /**
     * 用户名。
     */
    private String username;
    /**
     * 年龄。
     */
    private int age;
    /**
     * 出生日期。
     */
    private Date birthday;
    /**
     * 删除状态，0未删除，1已删除。
     */
    private int deleteStatus;
    /**
     * 创建时间。
     */
    private Date createTime;
    /**
     * 更新时间。
     */
    private Date updateTime;
    /**
     * 删除时间。
     */
    private Date deleteTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }
}
