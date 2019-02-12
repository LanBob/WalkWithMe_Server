package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domain.CollectionDao;
import com.domain.ResponseResult;
import com.domain.Star;
import com.domain.UserFollowDao;
import com.mapper.CollectionMapper;
import com.mapper.StarMapper;
import com.mapper.UserFollowMapper;
import com.service.ICollectionService;
import com.service.IStarService;
import com.service.IUserFollowService;
import com.util.JSONUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Component
@WebServlet("/get/who_star_collection")
public class WhoStarCollection extends HttpServlet {


    @Autowired
    private IStarService starService;

    @Autowired
    private ResponseResult<Integer> responseResult;

    @Autowired
    private ICollectionService collectionService;

    @Autowired
    private IUserFollowService userFollowService;

    private static final long serialVersionUID = 1L;

    @Override
    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String c = req.getParameter("code");
        String view_or_follower = req.getParameter("first");
        String who_or_followed = req.getParameter("second");
        int code = 0;
        if (c != null) {
            code = Integer.parseInt(c);
        }
        PrintWriter pw = null;
        if (code == 0) {// code = 0 ,Star
            System.out.println("star");
            Map map = new HashMap<>();
            if (view_or_follower != null) {
                map.put("view_show_id", Long.valueOf(view_or_follower));
            }
            if (who_or_followed != null) {
                map.put("who_star", Long.valueOf(who_or_followed));
            }
            Star dao = starService.get(map);
            responseResult.setCode(1);
            if (dao != null) {//存在了
                responseResult.setData(1);
            } else {
                responseResult.setData(0);
            }
            responseResult.setMessage("star");
            pw = resp.getWriter();
            pw.print(JSONUtil.toJson(responseResult));
        } else if (code == 1) {// code == 1 Collection
            System.out.println("collection");
            Map map = new HashMap<>();
            if (view_or_follower != null) {
                map.put("view_show_id", Long.valueOf(view_or_follower));
            }
            if (who_or_followed != null) {
                map.put("who_collection", Long.valueOf(who_or_followed));
            }
            CollectionDao dao = collectionService.get(map);
            responseResult.setCode(1);
            if (dao != null) {//存在了
                responseResult.setData(1);
            } else {
                responseResult.setData(0);
            }
            responseResult.setMessage("collection");

            pw = resp.getWriter();
            pw.print(JSONUtil.toJson(responseResult));
        } else {// code = 3 follow
            System.out.println("follow");
            Map map = new HashMap<>();
            if (view_or_follower != null) {
                map.put("follower", Long.valueOf(view_or_follower));
            }
            if (who_or_followed != null) {
                map.put("followed", Long.valueOf(who_or_followed));
            }
            UserFollowDao dao = userFollowService.get_user_follow(map);
            responseResult.setCode(1);
            if (dao != null) {//存在了
                responseResult.setData(1);//如果存在就设置为1
            } else {
                responseResult.setData(0);
            }
            responseResult.setMessage("follow");
            pw = resp.getWriter();
            pw.print(JSONUtil.toJson(responseResult));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
