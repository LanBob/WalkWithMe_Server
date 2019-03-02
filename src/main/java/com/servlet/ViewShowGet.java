package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domain.ResponseResult;
import com.domain.ViewShowDao;
import com.mapper.ViewShowMapper;
import com.service.IViewShowService;
import com.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


/**
 * 旅游信息页面
 *
 * @author ownlove
 */
@Component
@WebServlet(urlPatterns = "/get/view_show")
public class ViewShowGet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    ViewShowDao dao;
    @Autowired
    private IViewShowService viewShowService;
    @Autowired
    private ResponseResult<ViewShowDao> view_show_daoResponseResult;

    @Override
    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    // 只处理get请求
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        Map map = getHeadersInfo(req);
        System.out.println(map);

        Long id = Long.valueOf(req.getParameter("id"));
        PrintWriter pw = null;
        dao = viewShowService.get(id);
        System.out.println(dao);
        String json = JSONUtil.toJson(dao);
        view_show_daoResponseResult.setCode(1);
        view_show_daoResponseResult.setMessage("success");
        view_show_daoResponseResult.setData(dao);
        pw = resp.getWriter();
        pw.print(JSONUtil.toJson(view_show_daoResponseResult));
        System.out.println(JSONUtil.toJson(view_show_daoResponseResult));
    }

    // 只处理POST请求
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doPost(req, resp);
    }

    //输出header的信息或者传输的信息
    private Map<String, String> getHeadersInfo(HttpServletRequest req) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = req.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}