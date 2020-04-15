package cn.itcast.travel.web.servlet.noUsed;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserServer;
import cn.itcast.travel.service.impl.UserServerImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        String checkCode = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        ResultInfo resultInfo = new ResultInfo();
        if(checkCode != null && checkCode.equalsIgnoreCase(request.getParameter("check"))){
            // 调用UserSever完成注册
            UserServer userServer = new UserServerImpl();
            if(userServer.regist(user)){
                resultInfo.setFlag(true);
            }
            else{
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("注册失败，用户名已存在！");
            }
            // 将Info序列化为json
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(resultInfo);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
        }
        else{
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误！");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(resultInfo);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
