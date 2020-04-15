package cn.itcast.travel.util;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：Hzhang
 * @date ：Created in 2020/4/14 17:14
 * @description：Session上下文，用以根据sessionId获取session
 * @modified By：
 * @version: $
 */
public class SessionContextUtils {
    private static volatile SessionContextUtils sessionContextUtils;// volatile关键字保证有序性
    private Map<String, HttpSession> sessionMap;

    private SessionContextUtils(){
        sessionMap = new ConcurrentHashMap<>();// ConcurrentHashMap线程安全
    }

    /**
     * 双重锁机制确保效率较高的线程安全
     * @return
     */
    public static SessionContextUtils getInstance(){
        if(sessionContextUtils == null){
            synchronized(SessionContextUtils.class){
                if(sessionContextUtils == null){
                    sessionContextUtils = new SessionContextUtils();
                }
            }
        }
        return sessionContextUtils;
    }

    public void addSession(HttpSession session){
        if(session != null){
            sessionMap.put(session.getId(), session);
        }
    }

    public void delSession(HttpSession session){
        if(session != null){
            sessionMap.remove(session.getId());
        }
    }

    public HttpSession getSession(String sessionId){
        return sessionMap.get(sessionId);
    }
}
