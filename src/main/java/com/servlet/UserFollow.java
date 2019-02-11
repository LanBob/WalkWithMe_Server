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
import com.domain.UserFollowDao;
import com.mapper.UserFollowMapper;
import com.service.IUserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Component
@WebServlet(urlPatterns = "/app/follow")
public class UserFollow extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IUserFollowService userFollowService;

    @Autowired
    private UserFollowDao dao;

    @Autowired
    private ResponseResult rs;

    @Override
    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    // 只处理get请求
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    // 只处理POST请求
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long foer_id = null;
        Long foed_id = null;
        System.out.println("chat=============================");
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String follower = req.getParameter("follower");
        String followed = req.getParameter("followed");
        String code = req.getParameter("code");
        PrintWriter pw = null;

        if ("0".equals(code)) {
            if (follower != null) {
                foer_id = Long.valueOf(follower);
            }
            if (followed != null) {
                foed_id = Long.valueOf(followed);
            }
            Map map = new HashMap<>();
            map.put("follower", foer_id);
            map.put("followed", foed_id);
            userFollowService.delete(map);
        } else {
            if (follower != null) {
                foer_id = Long.valueOf(follower);
            }
            if (followed != null) {
                foed_id = Long.valueOf(followed);
            }
            dao.setFollower(foer_id);
            dao.setFollowed(foed_id);
            userFollowService.insert(dao);
            pw = resp.getWriter();
            rs.setCode(1);
            rs.setData("success");
            rs.setMessage("ok");
        }
    }

    private Map<String, String> getHeadersInfo(HttpServletRequest req) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = req.getHeader(key);
            System.out.println(key + ": " + value);
            map.put(key, value);
        }
        return map;
    }
}
