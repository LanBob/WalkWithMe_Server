package com.servlet;

import com.domain.InterScore;
import com.domain.ResponseResult;
import com.domain.ViewShowDao;
import com.mapper.InterScoreMapper;
import com.mapper.ViewShowMapper;
import com.service.IInterScoreService;
import com.util.JSONUtil;
import com.util.StrUtil;
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
import java.util.List;

/**
 * Created by ownlove on 2019/3/3.
 */
@Component
@WebServlet("/app/interScore")
public class InterScoreServlet extends HttpServlet{

    @Autowired
    private IInterScoreService interScoreService;

    @Autowired
    private ViewShowMapper viewShowMapper;
    @Override
    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String code = req.getParameter("code");
        System.out.println(code);
//      查询想要进行评分的操作
        if("0".equals(code)){
            String userId = req.getParameter("userId");
            System.out.println(userId);
            List<InterScore> list = interScoreService.listUserHavaAnyViewShow(userId);
            System.out.println(list);
            System.out.println("" + list.size());
            if(list != null && list.size() > 0){
//                如果这个人可以有必要进行评分
                ResponseResult<ViewShowDao> responseResult = new ResponseResult<>();
                List<InterScore> list1 = interScoreService.listCanBeScore(userId);
//              随机得到这个InterScore
                if(list1 != null && list1.size() > 0){
//                    如果有要进行评分的话
                    String viewId = list1.get(0).getViewShowId();
                    ViewShowDao viewShowDao = viewShowMapper.get(Long.valueOf(viewId));
                    feedBack(resp,1,viewShowDao);
                }else {
                    feedBack(resp,0,null);
                }
            }else {
                feedBack(resp,0,null);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String code = req.getParameter("code");
        if("0".equals(code)){
            String userId = req.getParameter("userId");
            String viewShowId = req.getParameter("viewShowId");
            String sco = req.getParameter("score");
            int score = Integer.valueOf(sco);
//            不应该是Mapper
            interScoreService.toScoreToViewId(viewShowId,userId,score);
            ResponseResult<String> responseResult = new ResponseResult<>();
            responseResult.setData("ok");
            responseResult.setCode(1);
            responseResult.setMessage("no");
            PrintWriter pw = resp.getWriter();
            pw.print(JSONUtil.toJson(responseResult));
        }
    }

    private String feedBack(HttpServletResponse resp, int result,ViewShowDao viewShowDao) {

        ResponseResult<ViewShowDao> response = new ResponseResult<>();
        response.setCode(result);
        response.setData(viewShowDao);
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
