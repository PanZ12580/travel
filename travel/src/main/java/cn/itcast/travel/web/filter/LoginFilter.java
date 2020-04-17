package cn.itcast.travel.web.filter;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserServer;
import cn.itcast.travel.service.impl.UserServerImpl;
import cn.itcast.travel.util.JWTUtils;
import cn.itcast.travel.util.SessionContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 自动登录判断
 */
public class LoginFilter implements Filter {
    private UserServer userServer = new UserServerImpl();
    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        Cookie[] cookies = request.getCookies();
        String token = null;
        User user = null;
        HttpSession session = null;
        boolean flag = false;
        if(cookies != null && cookies.length != 0){
            for (Cookie cookie : cookies) {
                if("token".equals(cookie.getName())){
                    // 获取token
                    token = cookie.getValue();
                }
                if("JSESSIONID".equals(cookie.getName())){
                    // 获取用户对象
                    SessionContextUtils sessionContextUtils = SessionContextUtils.getInstance();
                    session = sessionContextUtils.getSession(cookie.getValue());
                    if(session != null){
                        Enumeration<String> names = session.getAttributeNames();
                        while(names.hasMoreElements()){
                            if("user".equals(names.nextElement())){
                                user = (User)session.getAttribute("user");
                                flag = true;
                            }
                        }
                    }
                }
            }
        }
        // 校验token
        if(flag){
            if(token == null || user == null || !JWTUtils.verify(token, user.getUsername(), user.getPassword())){
                session.invalidate();
                SessionContextUtils.getInstance().delSession(session);
            }
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
