package cn.itcast.travel.web.filter;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserServer;
import cn.itcast.travel.service.impl.UserServerImpl;
import cn.itcast.travel.util.SessionContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 自动登录判断
 */
@WebFilter("/*")
public class LoginFilter implements Filter {
    private UserServer userServer = new UserServerImpl();
    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0){
            for (Cookie cookie : cookies) {
                if("JSESSIONID".equals(cookie.getName())){
                    // 获取用户信息
                    String sessionId = cookie.getValue();
                    SessionContextUtils sessionContextUtils = SessionContextUtils.getInstance();
                    HttpSession session = sessionContextUtils.getSession(sessionId);
                    User user = null;
                    if(session != null){
                        user = (User)session.getAttribute("user");
                    }
                    // 判断用户信息是否有效
                    if(user != null){
                        boolean flag = userServer.judgeUser(user.getUsername(), user.getPassword());
                        if(flag){
                            HttpSession reqSession = request.getSession();
                            reqSession.getId();
                            reqSession.setAttribute("user", user);
                        }
                        else {
                            sessionContextUtils.delSession(session);
                        }
                    }
                }
            }
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
