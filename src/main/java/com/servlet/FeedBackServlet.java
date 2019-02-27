package com.servlet;

import com.domain.FeedBackDao;
import com.domain.ResponseResult;
import com.service.IFeedBackService;
import com.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ownlove on 2019/2/27.
 */
@WebServlet("/app/feedBack")
public class FeedBackServlet extends HttpServlet {

    @Autowired
    private IFeedBackService feedBackService;

    @Autowired
    private FeedBackDao feedBackDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String userId = req.getParameter("userId");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        ResponseResult<String> responseResult = new ResponseResult<>();
        if (userId != null || title != null || content != null) {
            feedBackDao.setUserId(userId);
            feedBackDao.setTitle(title);
            feedBackDao.setContent(content);
            System.out.println(feedBackDao);
            int i = feedBackService.insert(feedBackDao);
            if (i != 0) {
                feedBack(resp, 1);
            } else {
                feedBack(resp, 0);
            }
        }
    }

    private String feedBack(HttpServletResponse resp, int result) {

        ResponseResult<String> response = new ResponseResult<>();
        response.setCode(result);
        response.setData("ok");
        response.setMessage("success");
        PrintWriter pw = null;
        try {
            pw = resp.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.print(JSONUtil.toJson(response));
        pw.close();
        return "";
    }
}
