package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserServer;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

/**
 * @author ：Hzhang
 * @date ：Created in 2020/4/10 16:56
 * @description：
 * @modified By：
 * @version: $
 */
public class UserServerImpl implements UserServer {
    private UserDao userDao = new UserDaoImpl();
    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {
        // 根据用户名查询用户
        User registUser = userDao.findUserByUsername(user.getUsername());
        // 判断用户是否存在
        if(registUser != null){
            return false;
        }
        else{
            // 设置用户激活状态
            user.setStatus("N");
            // 设置用户激活码
            user.setCode(UuidUtil.getUuid());
            //保存用户注册信息
            userDao.save(user);
            // 发送激活邮件
            String content = "黑马旅游网，<a href='http://localhost:8080/travel/user/active?code=" + user.getCode() + "'>点击进行账号激活</a>";
            MailUtils.sendMail(user.getEmail(), content, "激活邮件");
        }
        return true;
    }

    /**
     * 根据状态码查找用户
     * @param code
     * @return
     */
    @Override
    public User getUser(String code) {
        User user = null;
        if(code != null){
            user = userDao.findUserByCode(code);
        }
        return user;
    }

    /**
     * 跟新用户状态码
     * @param user
     */
    @Override
    public void updateStatus(User user) {
        userDao.updateStatus(user);
    }

    /**
     * 根据账号密码查找用户
     * @param user
     * @return
     */
    @Override
    public User getLoginUser(User user) {
        return userDao.getLoginUser(user);
    }

    /**
     * 判断用户信息是否有效
     * @param username
     * @param password
     * @return
     */
    @Override
    public boolean judgeUser(String username, String password) {
        return userDao.judgeUser(username, password);
    }
}
