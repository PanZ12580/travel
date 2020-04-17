package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryServer;
import cn.itcast.travel.service.impl.CategoryServerImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    CategoryServer categoryServer = new CategoryServerImpl();

    /**
     * 查询所有分类
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void findCategories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categoryList = categoryServer.findCategories();
        writeValue(categoryList, response);
    }
}
