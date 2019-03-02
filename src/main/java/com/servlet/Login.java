package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.StrUtil;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.session.SqlSession;

import com.domain.ResponseResult;
import com.domain.User;
import com.mapper.UserMapper;
import com.util.JSONUtil;

import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * 密码登录需要的request参数： username:Long password:String
 * login_change:boolean（0是登录，1是修改密码） flag：暂时没有
 * <p>
 * 使用标志变量，login_change,0是登录，1是修改密码
 * 使用一个标志变量，flag，当flag为0的时候，是验证码登录，当flag时1的时候，是密码登录
 *
 * @author ownlove String path =
 *         req.getSession().getServletContext().getRealPath("");获取到的就是Context配置
 *         的信息
 */

@Component
@WebServlet("/login")
public class Login extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String token = null;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private User user;

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
        String json = req.getParameter("data");
        String code = req.getParameter("code");
        System.out.println(json);
        System.out.println(code);

        Map m = JSONUtil.toMap(json);
        if ("0".equals(code)) {//注册业务
            String username = m.get("username").toString();
            String md5 = m.get("password").toString();
            Long id = Long.valueOf(username);
            user.setId(id);
            user.setPassword(md5);
            User u_check = null;

            //如果已经存在，注册失败
            User u = userMapper.get(id);
            if(u != null){
                if(u.getPassword() != null){
                    feedBack(resp,0);//失败
                }
            }else {//否则注册成功
                userMapper.insert(user);
                u_check = userMapper.get(id);
            }
            if (u_check != null) {//如果再次获取成功，返货成功
                feedBack(resp, 1);
            } else {
                feedBack(resp, 0);
            }
        } else if ("1".equals(code)) {//密码登录页面
            String username = m.get("username").toString();
            String password = m.get("md5").toString();

            if (username != null) {
                // username Md5
                User u_c = userMapper.get(Long.valueOf(username));
                if (u_c != null) {
                    if (password.equals(u_c.getPassword())) {
                        feedBack(resp, 1);//如果账号和密码正确
                    } else {
                        feedBack(resp, 0);//如果密码不正确
                    }
                } else {
                    feedBack(resp, 0);//如果账户不存在
                }
            }

        } else if ("2".equals(code)) {//验证码登录页面
            String username = m.get("username").toString();//手机号

            if (username != null && !StrUtil.isBlank(username)) {
                User u_c = userMapper.get(Long.valueOf(username));
                if (u_c != null) {
                    feedBack(resp, 1);//如果存在该用户
                } else {
                    feedBack(resp, 0);//如果不正确
                }
            } else {
                feedBack(resp, 0);//如果不正确
            }
        }else if("3".equals(code)){//通过验证码修改密码
            System.out.println("code " + code);

            String username = m.get("username").toString();//手机号
            String newPassWord = m.get("password").toString();
            System.out.println("username " + username  + "passwrod " + newPassWord);
//            User u = userMapper.get(Long.valueOf(username));
            Long phone = Long.valueOf(username);
            User u = new User();
            u.setId(phone);
            u.setPassword(newPassWord);
            userMapper.change_password(u);
            User newUser = userMapper.get(phone);
            if(newUser.getPassword().equals(newPassWord)){
                feedBack(resp, 1);//如果账号和密码正确
            }else {
                feedBack(resp, 0);//如果账号和密码正确
            }
        }

    }


    /**
     * @param resp
     * @param result
     * @return
     */
    private String feedBack(HttpServletResponse resp, int result) {

        ResponseResult<String> response = new ResponseResult<>();
        response.setCode(result);
        response.setData("insert ok");
        response.setMessage("success");
        try {
            PrintWriter pw = resp.getWriter();
            pw.print(JSONUtil.toJson(response));
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
