package com.python.api.bean.threadLocal;

import com.python.api.entity.User;

/**
 * 后台用户本地守护线程类
 */
public class UserLocal {

    private static ThreadLocal<User> UserLocalThreadLocal = new ThreadLocal<User>();

    /**
     * 设置用户信息
     */
    public static void setUser(User user) {
        UserLocalThreadLocal.set(user);
    }

    /**
     * 获取登录用户信息
     *
     * @return
     */
    public static User getUser() {
        return UserLocalThreadLocal.get();
    }
}
