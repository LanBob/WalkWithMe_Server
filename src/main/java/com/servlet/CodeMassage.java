package com.servlet;

import com.domain.ResponseResult;
import com.service.IMessageCode;
import com.service.impl.MessageCodeImpl;
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
 * Created by ownlove on 2019/2/19.
 */
@Component
@WebServlet(urlPatterns = "/get/messageCode")
public class CodeMassage extends HttpServlet{

    @Autowired
    private IMessageCode messageCode;

    @Override
    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");
        String phone = req.getParameter("phone");
        System.out.println(phone);
        String code = messageCode.sendCodeToUser(phone);
//        String code = "1158";
        PrintWriter pw = resp.getWriter();
        ResponseResult<String> responseResult = new ResponseResult<>();
        if(code != null && code != ""){
            responseResult.setCode(1);
            responseResult.setMessage("s");
            responseResult.setData(code);
        }else{
            responseResult.setCode(0);
            responseResult.setMessage("s");
            responseResult.setData("");
        }
        System.out.println(responseResult);
        pw.write(JSONUtil.toJson(responseResult));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
