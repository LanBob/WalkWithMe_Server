package com.servlet;

import com.domain.*;
import com.service.*;
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

    @Autowired
    private IViewShowService viewShowService;

    @Autowired
    private IInterScoreService interScoreService;

    @Autowired
    private IFindViewService findViewService;

    @Autowired
    private IStarCollectionService starCollectionService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//     管理员对已经进行了导游相互验证的viewShow进行再次评分，保存到findView
        String viewShowId = req.getParameter("viewShowId");
        String sco = req.getParameter("score");
        int score = Integer.valueOf(sco);

        ViewShowDao viewShowDao = viewShowService.get(Long.valueOf(viewShowId));
        InterScore interScore = interScoreService.get(viewShowId);
        int finalScore = viewShowDao.getScore() + score;
        ResponseResult<String> responseResult = new ResponseResult<>();
        if( finalScore > 100 && finalScore < 140){
//            失败的情况
            interScoreService.delete(interScore);
            responseResult.setMessage(String.valueOf(viewShowDao.getId()));
            responseResult.setCode(0);
            responseResult.setData("noPass");
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(JSONUtil.toJson(responseResult));
        }else if(finalScore >= 140){
//            如果是成功的情况
            viewShowDao.setScore(finalScore);
            int code = viewShowService.wantToUpdate(viewShowDao);
//            一下操作放到一个事务上
//            保存为FindViewShow
//            save_find_view_dao(viewShowDao);
            System.out.println("code " + code);

            if(code == 1){
                responseResult.setMessage(String.valueOf(viewShowDao.getId()));
                responseResult.setCode(1);
                responseResult.setData("pass");
                PrintWriter printWriter = resp.getWriter();
                System.out.println(JSONUtil.toJson(responseResult));
                printWriter.write(JSONUtil.toJson(responseResult));
            }else {
                responseResult.setMessage(String.valueOf(viewShowDao.getId()));
                responseResult.setCode(1);
                responseResult.setData("wrong");
                PrintWriter printWriter = resp.getWriter();
                printWriter.write(JSONUtil.toJson(responseResult));
            }

        }else {
//            管理员进行的导游相互验证模块
            int newScore = score + viewShowDao.getScore();
            viewShowDao.setScore(newScore);
            viewShowService.update(viewShowDao);
            interScore.setToBeScored("Y");
            interScoreService.update(interScore);
            System.out.println("Manager进行评分");
            responseResult.setMessage(String.valueOf(viewShowDao.getId()));
            responseResult.setCode(1);
            responseResult.setData("manager");
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(JSONUtil.toJson(responseResult));
        }

    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
//        导游验证进行打分操作
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String userId = req.getParameter("userId");
        String score = req.getParameter("score");
        IsGoodMan isGoodMan = iIsGoodManService.get(userId);
        PrintWriter pw;
//        导游验证进行操作
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

//
//    /**
//     * 保证只有这里能进行FindView的Insert操作
//     * @param viewShowDao
//     */
//    private void save_find_view_dao(ViewShowDao viewShowDao) {
//        FindViewDao find_viewDao = new FindViewDao();
//
//        find_viewDao.setId(viewShowDao.getId());
//        find_viewDao.setCity(viewShowDao.getCity());
//        find_viewDao.setMoney(viewShowDao.getMoney());
//        find_viewDao.setStar(0);
//        find_viewDao.setTitle(viewShowDao.getTitle());
//        find_viewDao.setType(viewShowDao.getType());
//        find_viewDao.setUser_id(viewShowDao.getUser_id());
//        find_viewDao.setDefaultpath(viewShowDao.getDefaultpath());
//        findViewService.insert(find_viewDao);
//
////        保存这些StarCollection操作
//        StarCollectionDao star_collectionDao = new StarCollectionDao();
//        star_collectionDao.setView_show_id(find_viewDao.getId());
//        star_collectionDao.setStar(0);
//        star_collectionDao.setCollection(0);
//        save_star_collection(star_collectionDao);
//    }
//
//
//    private void save_star_collection(StarCollectionDao dao) {
//        starCollectionService.insert(dao);
//    }