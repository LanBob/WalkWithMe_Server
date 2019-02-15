package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domain.PersonSettingDao;
import com.domain.ResponseResult;
import com.mapper.PersonSettingMapper;
import com.service.IPersonSettingService;
import com.util.JSONUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * 个人信息设置操作
 *
 * @author ownlove
 */

@Component
@WebServlet("/app/person_setting")
public class PersonSetting extends HttpServlet {

    @Autowired
    private PersonSettingDao person_settingDao;

    @Autowired
    private IPersonSettingService personSettingService;

    @Autowired
    private ResponseResult<String> result;

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
        System.out.println("login");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String json = req.getParameter("person_setting");
        person_settingDao = JSONUtil.toBean(json, PersonSettingDao.class);
        System.out.println(person_settingDao);
        PrintWriter print = null;
        personSettingService.insert(person_settingDao);
        result.setCode(1);
        result.setData("success");
        result.setMessage("ok");
        print = resp.getWriter();
        print.print(JSONUtil.toJson(result));
    }

    /*
        private void insert(Person_setting_dao dao) {
            // 修改
            @Cleanup
            SqlSession session = MyBatisUtil.getSession();
            Person_setting_mapper mapper = session.getMapper(Person_setting_mapper.class);
            mapper.update(dao);
            session.commit();
        }

        private void update(Person_setting_dao dao) {
            // 增加
            @Cleanup
            SqlSession session = MyBatisUtil.getSession();
            Person_setting_mapper mapper = session.getMapper(Person_setting_mapper.class);
            mapper.update(dao);
            session.commit();
        }
    */
    private boolean check_token(String token) {
        if ("token".equals(token)) {
            return true;
        }
        return false;
    }

}
