package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserServer {
    /**
     * 注册用户
     * @param user
     * @return
     */
    boolean regist(User user);

    /**
     * 根据激活码查看用户是否存在
     * @param code
     * @return
     */
    User getUser(String code);

    /**
     * 跟新用户状态码
     * @param user
     */
    void updateStatus(User user);

    /**
     * 根据账号密码查找用户
     * @param user
     * @return
     */
    User getLoginUser(User user);

    /**
     * 判断用户信息是否有效
     * @param username
     * @param password
     * @return
     */
    boolean judgeUser(String username, String password);
}
