package com.servlet;

import com.domain.CommentDao;
import com.domain.HeadImage;
import com.domain.PersonSettingDao;
import com.domain.ResponseResult;
import com.service.ICommentService;
import com.service.IHeadImageService;
import com.service.IPersonSettingService;
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
import java.util.List;

/**
 * Created by ownlove on 2019/2/25.
 */

@WebServlet("/app/comment")
public class CommentServlet extends HttpServlet{

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IHeadImageService headImageService;

    @Autowired
    private IPersonSettingService personSettingService;


    @Autowired
    ResponseResult<List<CommentDao>> responseResult;


    @Override
    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String viewShowId = req.getParameter("viewShowId");
        List<CommentDao> list = commentService.getCommentByViewShowId(viewShowId);
        PrintWriter printWriter;
        printWriter = resp.getWriter();
        if(list!= null && list.size() != 0){
            responseResult.setMessage("ok");
            responseResult.setCode(1);
            responseResult.setData(list);
            printWriter.write(JSONUtil.toJson(responseResult));
        }else {
            responseResult.setMessage("ok");
            responseResult.setCode(0);
            responseResult.setData(list);
            printWriter.write(JSONUtil.toJson(responseResult));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String userId = req.getParameter("userId");
        String viewShowId = req.getParameter("viewShowId");
        String comment = req.getParameter("comment");
        ResponseResult<String> result = new ResponseResult<>();
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        String defaultImage = null;
        String userName = null;
        String mytime = null;
        PrintWriter printWriter;
        printWriter = resp.getWriter();

        if(userId != null && viewShowId != null && comment != null){
            //通过UserId得到
            HeadImage headImage = headImageService.get(userId);
            if(headImage != null){
                defaultImage = headImage.getHead_image();
            }
            PersonSettingDao personSettingDao = personSettingService.getById(userId);
            if(personSettingDao != null){
                userName = personSettingDao.getAlias();
            }
            mytime = currentTimeMillis;
            CommentDao commentDao = new CommentDao();
            commentDao.setMytime(mytime);
            commentDao.setComment(comment);
            commentDao.setDefaultImage(defaultImage);
            commentDao.setUserName(userName);
            commentDao.setViewShowId(viewShowId);
            commentService.insert(commentDao);
            result.setCode(1);
            result.setMessage("ok");
            result.setData(null);
            printWriter.write(JSONUtil.toJson(result));
        }else {
            result.setCode(0);
            result.setMessage("no");
            result.setData(null);
            printWriter.write(JSONUtil.toJson(result));
        }
    }
}
