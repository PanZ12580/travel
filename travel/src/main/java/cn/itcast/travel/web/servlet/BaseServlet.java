package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@WebServlet("/baseServlet")
public class BaseServlet extends HttpServlet {
    /**
     * 方法分发
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String methodName = uri.substring(uri.lastIndexOf("/")+1);
        try {
            Method method = this.getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true);
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void writeValue(Object obj, HttpServletResponse response){
        ObjectMapper mapper = new ObjectMapper();
        try {
            response.setContentType("application/json;charset=utf-8");
            mapper.writeValue(response.getOutputStream(), obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeValueAsString(Object obj, HttpServletResponse response){
        ObjectMapper mapper = new ObjectMapper();
        try {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(mapper.writeValueAsString(obj));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
