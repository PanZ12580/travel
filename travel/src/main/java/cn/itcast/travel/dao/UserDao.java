package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    /**
     * 根据用户名查询用户
     */
    User findUserByUsername(String username);

    /**
     * 保存用户注册数据
     */
    void save(User user);

    /**
     * 根据激活码查询用户
     * @param code
     * @return
     */
    User findUserByCode(String code);

    /**
     * 更新用户状态码为“Y”
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
     * 查询是否有对应的用户信息
     * @param username
     * @param password
     * @return
     */
    boolean judgeUser(String username, String password);
}
