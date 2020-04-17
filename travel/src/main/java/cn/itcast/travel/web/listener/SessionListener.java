package cn.itcast.travel.web.listener;

import cn.itcast.travel.util.SessionContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener
public class SessionListener implements HttpSessionListener{
    private SessionContextUtils sc = SessionContextUtils.getInstance();

    /**
     * 创建session时自动添加进sessionMap
     * @param httpSessionEvent
     */
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        sc.addSession(httpSessionEvent.getSession());
    }

    /**
     * 销毁session时自动从sessionMap中去除该session
     * @param httpSessionEvent
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        sc.delSession(httpSessionEvent.getSession());
    }
}
