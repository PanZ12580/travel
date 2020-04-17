package cn.itcast.travel.web.servlet.noUsed;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserServer;
import cn.itcast.travel.service.impl.UserServerImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        String checkCode = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        ResultInfo resultInfo = new ResultInfo();
        if(checkCode != null && checkCode.equalsIgnoreCase(check)){
            UserServer userServer = new UserServerImpl();
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
                        Cookie cookie = new Cookie("JSESSIONID", session.getId());
                        response.addCookie(cookie);
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
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream os = response.getOutputStream();
        mapper.writeValue(os, resultInfo);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
