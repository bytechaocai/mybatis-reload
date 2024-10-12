package com.chen.mybatisreload.example.standardapp.utils;

import com.chen.mybatisreload.example.standardapp.entity.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * 假数据工具。
 *
 * @author chen
 */
public class FakeDataUtil {
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmsssss");
    private static Random random = new Random();
    private static final String[] FIRST_NAME_ARRAY = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王"};

    public static String getUsername() {
        return FIRST_NAME_ARRAY[random.nextInt(FIRST_NAME_ARRAY.length - 1)] + "某";
    }

    public static int getAge() {
        return random.nextInt(80) + 1;
    }

    public static Date getBirthday(int age) {
        int offset = -random.nextInt(365);
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DAY_OF_YEAR, offset);
        instance.add(Calendar.YEAR, age);
        return instance.getTime();
    }

    public static String getId() {
        return format.format(new Date());
    }

    public static User getUser() {
        User user = new User();
        user.setId(FakeDataUtil.getId());
        user.setUsername(FakeDataUtil.getUsername());
        user.setAge(FakeDataUtil.getAge());
        user.setBirthday(FakeDataUtil.getBirthday(user.getAge()));
        user.setDeleteStatus(0);
        user.setCreateTime(new Date());
        return user;
    }
}
