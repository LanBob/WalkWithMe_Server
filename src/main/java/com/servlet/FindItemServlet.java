package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.IFindItemService;

import com.domain.FindViewDao;
import com.domain.ResponseResult;
import com.util.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


/**
 * 查看view_show，发现页面
 *
 * @author ownlove
 */
@Component
@WebServlet(urlPatterns = "/get/findItem")
public class FindItemServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    FindViewDao dao;

    @Autowired
    private IFindItemService findItemService;

    @Autowired
    private ResponseResult<List<FindViewDao>> result;

    final Logger logger = LoggerFactory.getLogger(FindItemServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    // 只处理get请求
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		findItemService = ContextUtil.getBean(FindItemServiceImpl.class);
//		result = ContextUtil.getBean(ResponseResult.class);
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");
        String types = req.getParameter("type");
        String userId = req.getParameter("userId");
        System.out.println("type" + types + "userId" + userId);
        if(types != null){
            int type = Integer.valueOf(types);
            PrintWriter pw = null;
            List<FindViewDao> list = findItemService.getFindViewByType(type);
            result.setCode(1);
            result.setMessage("success");
            result.setData(list);
            pw = resp.getWriter();
            pw.print(JSONUtil.toJson(result));
        }else if(userId != null){
            List<FindViewDao> ownList = findItemService.getFindViewByUserId(userId);
            result.setCode(1);
            result.setMessage("success");
            result.setData(ownList);
            PrintWriter pw = null;
            pw = resp.getWriter();
            pw.print(JSONUtil.toJson(result));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doPost(req, resp);
    }

}