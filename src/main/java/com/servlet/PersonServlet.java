package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domain.PersonDao;
import com.domain.ResponseResult;
import com.mapper.PersonDaoMapper;
import com.service.IPersonDaoService;
import com.util.JSONUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Component
@WebServlet(urlPatterns = "/person")
public class PersonServlet extends HttpServlet {

    //	@Autowired
//	private PersonDaoMapper person_dao_mapper;
    @Autowired
    private IPersonDaoService personDaoService;

    @Autowired
    private ResponseResult<PersonDao> person_daoResponseResult;

    /**
     *
     */
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
        PrintWriter pw;
        Long id = Long.valueOf(req.getParameter("id"));
        PersonDao dao = personDaoService.get(id);
        person_daoResponseResult.setData(dao);
        person_daoResponseResult.setCode(1);
        person_daoResponseResult.setMessage("success");
        String json = JSONUtil.toJson(person_daoResponseResult);
        resp.setCharacterEncoding("UTF-8");
        pw = resp.getWriter();
        pw.write(json);
        System.out.println(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

}

