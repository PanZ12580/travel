package cn.itcast.travel.web.servlet.noUsed;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserServer;
import cn.itcast.travel.service.impl.UserServerImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeServlet")
public class ActiveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        UserServer userServer = new UserServerImpl();
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
