package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author ：Hzhang
 * @date ：Created in 2020/4/10 17:05
 * @description：
 * @modified By：
 * @version: $
 */
public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @Override
    public User findUserByUsername(String username) {
        String sql = "select * from tab_user where username = ?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (DataAccessException e) {
        }
        return user;
    }

    /**
     * 保存用户注册数据
     * @param user
     */
    @Override
    public void save(User user) {
        String sql = "insert into tab_user(username, password, name, birthday, sex, telephone, email, status, code) " +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(sql, user.getUsername(), user.getPassword(), user.getName(),
                user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(),
                user.getStatus(), user.getCode());
    }

    /**
     * 根据激活码查找用户
     * @param code
     * @return
     */
    @Override
    public User findUserByCode(String code) {
        String sql = "select * from tab_user where code = ?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 跟新用户激活码
     * @param user
     */
    @Override
    public void updateStatus(User user) {
        String sql = "update tab_user set status = 'Y' where uid = ?";
        template.update(sql, user.getUid());
    }

    /**
     * 根据账号密码查找用户
     * @param user
     * @return
     */
    @Override
    public User getLoginUser(User user) {
        String sql = "select * from tab_user where username = ? and password = ?";
        User loginUser = null;
        try {
            loginUser = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername(), user.getPassword());
        } catch (DataAccessException e) {
        }
        return loginUser;
    }

    /**
     * 查询是否有对应的用户信息
     * @param username
     * @param password
     * @return
     */
    @Override
    public boolean judgeUser(String username, String password) {
        String sql = "select * from tab_user where username = ? and password = ?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        } catch (DataAccessException e) {
        }
        return user != null;
    }
}
