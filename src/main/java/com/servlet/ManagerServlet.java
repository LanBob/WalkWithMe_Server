package com.servlet;

import com.domain.IsGoodMan;
import com.domain.ResponseResult;
import com.service.IIsGoodManService;
import com.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
 * Created by ownlove on 2019/3/2.
 */
@Component
@WebServlet("/app/manager")
public class ManagerServlet extends HttpServlet{

    @Autowired
    private IIsGoodManService iIsGoodManService;
    @Autowired
    private ResponseResult<String> responseResult;

    @Override
    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
//        进行打分操作
        String userId = req.getParameter("userId");
        String score = req.getParameter("score");
        IsGoodMan isGoodMan = iIsGoodManService.get(userId);
        PrintWriter pw;
        if(userId != null && score != null){
            isGoodMan.setScore(Integer.valueOf(score));
            iIsGoodManService.update(isGoodMan);

            responseResult.setData(isGoodMan.getUserId());
            responseResult.setCode(1);
            responseResult.setMessage("1");
            pw = resp.getWriter();
            pw.write(JSONUtil.toJson(responseResult));
        }else {
            responseResult.setData("1");
            responseResult.setCode(0);
            responseResult.setMessage("1");
            pw = resp.getWriter();
            pw.write(JSONUtil.toJson(responseResult));
        }
    }
}
