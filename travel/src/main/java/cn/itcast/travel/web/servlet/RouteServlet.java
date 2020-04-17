package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavouriteServer;
import cn.itcast.travel.service.RouteServer;
import cn.itcast.travel.service.impl.FavouriteServerImpl;
import cn.itcast.travel.service.impl.RouteServerImpl;
import cn.itcast.travel.util.SessionContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    RouteServer routeServer = new RouteServerImpl();

    /**
     * 获取存取cid、当前页面、每页记录数、总页数、当前页面记录list的PageBean对象并序列化为json
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void getRoutes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cidStr = request.getParameter("cid");
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        // 默认cid为0
        int cid = 0;
        if(cidStr != null && cidStr.length() != 0 && !"null".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }
        // 默认当前页面为1
        int currentPage= 1;
        if(currentPageStr != null && currentPageStr.length() != 0){
            currentPage = Integer.parseInt(currentPageStr);
        }
        // 默认每页显示5条记录
        int pageSize = 5;
        if(pageSizeStr != null && pageSizeStr.length() != 0){
            pageSize = Integer.parseInt(pageSizeStr);
        }

        PageBean<Route> pb = routeServer.pageQuery(cid, currentPage, pageSize, rname);
        // 将PageBean序列化为json
        writeValue(pb, response);
    }

    /**
     * 获取线路详细信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void getDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        Route route = routeServer.findOne(rid);
        writeValue(route, response);
    }

    /**
     * 判断该线路是否已被用户收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void isFavourite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User)request.getSession().getAttribute("user");
        int uid = 0;
        if(user != null){
            uid = user.getUid();
        }
        else{
            return;
        }
        FavouriteServer favouriteServer = new FavouriteServerImpl();
        boolean flag = favouriteServer.isFavourite(Integer.parseInt(rid), uid);
        writeValue(flag, response);
    }

    /**
     * 添加收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void addFavourite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User)request.getSession().getAttribute("user");
        int uid = 0;
        if(user != null){
            uid = user.getUid();
        }
        else{
            return;
        }
        FavouriteServer favouriteServer = new FavouriteServerImpl();
        favouriteServer.addFavourite(Integer.parseInt(rid), uid);
    }
}
