package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserServer;
import cn.itcast.travel.service.impl.UserServerImpl;
import cn.itcast.travel.util.JWTUtils;
import cn.itcast.travel.util.SessionContextUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserServer userServer = new UserServerImpl();
    /**
     * 登录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        String check = parameterMap.get("check")[0];
        HttpSession session = request.getSession();

        ResultInfo resultInfo = new ResultInfo();
        if(checkCode(session, check)){
            User loginUser = userServer.getLoginUser(user);
            if(loginUser == null){
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("用户名或密码错误！");
            }
            else if(!"Y".equalsIgnoreCase(loginUser.getStatus())){
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("你的账号尚未激活！");
            }
            else{
                if(parameterMap.get("checkbox") != null && parameterMap.get("checkbox").length != 0){
                    String flag = parameterMap.get("checkbox")[0];
                    if("on".equals(flag)){
                        // 在客户端持久化JSESSIONID
                        Cookie cookie1 = new Cookie("JSESSIONID", session.getId());
                        cookie1.setMaxAge(24*60*60);
                        cookie1.setPath("/");
                        response.addCookie(cookie1);
                        // 设置token到Cookie中
                        Cookie cookie2 = new Cookie("token", JWTUtils.sign(loginUser.getUsername(), loginUser.getPassword()));
                        cookie2.setMaxAge(24*60*60);
                        cookie2.setPath("/");
                        response.addCookie(cookie2);
                    }
                }
                session.setAttribute("user", loginUser);
                resultInfo.setFlag(true);
            }
        }
        else{
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误！");
        }
        writeValue(resultInfo, response);
    }

    /**
     * 注册
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取数据
        Map<String, String[]> map = request.getParameterMap();
        // 封装javaBean
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        HttpSession session = request.getSession();
        String check = request.getParameter("check");
        ResultInfo resultInfo = new ResultInfo();
        if(checkCode(session, check)){
            // 调用UserSever完成注册
            if(userServer.regist(user)){
                resultInfo.setFlag(true);
            }
            else{
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("注册失败，用户名已存在！");
            }
            // 将Info序列化为json
            writeValue(resultInfo, response);
        }
        else{
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误！");
            writeValue(resultInfo, response);
        }
    }

    /**
     * 退出
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    /**
     * 查找session中的用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void findUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Cookie[] cookies = request.getCookies();
        HttpSession session = null;
        User user = null;
        if(cookies != null && cookies.length != 0){
            for (Cookie cookie : cookies) {
                if("JSESSIONID".equals(cookie.getName())){
                    // 获取用户对象
                    SessionContextUtils sessionContextUtils = SessionContextUtils.getInstance();
                    session = sessionContextUtils.getSession(cookie.getValue());
                    if(session != null){
                        Enumeration<String> names = session.getAttributeNames();
                        while(names.hasMoreElements()){
                            if("user".equals(names.nextElement())){
                                user = (User)session.getAttribute("user");
                            }
                        }
                    }
                }
            }
        }
        writeValue(user, response);
    }

    /**
     * 激活邮箱
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String code = request.getParameter("code");
        User user = userServer.getUser(code);
        if(user != null){
            if(user.getStatus().equalsIgnoreCase("Y")){
                String content = "您已成功激活过，<a href='http://localhost:8080/travel/login.html'>点击跳转到登录界面</a>";
                response.getWriter().write(content);
                return;
            }
            userServer.updateStatus(user);
            response.setContentType("text/html;charset=utf-8");
            String content = "激活成功，<a href='http://localhost:8080/travel/login.html'>点击跳转到登录界面</a>";
            response.getWriter().write(content);
        }
    }

    /**
     * 校验验证码
     * @param session
     * @return
     */
    private boolean checkCode(HttpSession session, String check){
        String checkCode = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if(checkCode != null && checkCode.equalsIgnoreCase(check)){
            return true;
        }
        return false;
    }

}
