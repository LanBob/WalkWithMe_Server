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
import com.util.StrUtil;
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

    // 个人主页的请求，通过ViewShowId进行返回
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String viewId = req.getParameter("id");
        if(viewId != null &&!"".equals(viewId) && StrUtil.isInteger(viewId)){
            Long id = Long.valueOf(viewId);
            PrintWriter pw = null;
            dao = viewShowService.get(id);
            System.out.println(dao);
            String json = JSONUtil.toJson(dao);
            view_show_daoResponseResult.setCode(1);
            view_show_daoResponseResult.setMessage("success");
            view_show_daoResponseResult.setData(dao);
            pw = resp.getWriter();
            pw.print(JSONUtil.toJson(view_show_daoResponseResult));
        }
    }

    // 只处理POST请求
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String code = req.getParameter("code");
        if("0".equals(code)){
//            进行删除操作
            String viewShowId = req.getParameter("viewShowId");
            System.out.println("0" + viewShowId);
            if(viewShowId!= null && viewShowId.trim()!=""){
                int i = viewShowService.delete(viewShowId);
                if(i != 0){
                    feedBack(resp,1,viewShowId);
                }else {
                    feedBack(resp,0,viewShowId);
                }
            }
        }
    }

    private String feedBack(HttpServletResponse resp, int result,String viewShowId) {

        ResponseResult<String> response = new ResponseResult<>();
        response.setCode(result);
        response.setData("insert ok");
        response.setMessage(viewShowId);
        try {
            PrintWriter pw = resp.getWriter();
            pw.print(JSONUtil.toJson(response));
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
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