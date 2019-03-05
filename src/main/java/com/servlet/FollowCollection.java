package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domain.*;
import com.mapper.CollectionMapper;
import com.mapper.FindViewMapper;
import com.mapper.StarCollectionMapper;
import com.mapper.StarMapper;
import com.mapper.UserFollowMapper;
import com.service.*;
import com.service.impl.StarCollectionServiceImpl;
import com.sun.tracing.dtrace.Attributes;
import com.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * 关注或者收藏
 * code是0，是star，code是1，是collection ,code 是3，是关注
 *
 * @author ownlove
 *         <p>
 *         返回改UserID已经Star过的旅游信息页面
 */

@Component
@WebServlet(urlPatterns = "/app/follow_collection")
public class FollowCollection extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IStarService starService;

    @Autowired
    private IFindViewService findViewService;

    @Autowired
    private ResponseResult<List<FindViewDao>> rs;

    @Autowired
    private ICollectionService collectionService;

    @Autowired
    private IUserFollowService userFollowService;


    @Autowired
    private IPersonDaoService personDaoService;

    @Autowired
    private ResponseResult<List<PersonSettingDao>> personResult;

    @Autowired
    private IStarCollectionService starCollectionService;

    @Autowired
    private ResponseResult<StarCollectionDao> star_collectionResponseResult;

    @Autowired
    private Star star;

    @Autowired
    private UserFollowDao user_followDao;
    @Autowired
    private IPersonSettingService personSettingService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    /**
     * code = 3,Follow
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    // 只处理get请求
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userID = null;
        int code = -1;
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        String follower = req.getParameter("userId");
        String c = req.getParameter("code");
        String v_id = req.getParameter("view_show_id");

        Long view_show_id = 0L;

        if (follower != null) {
            userID = Long.valueOf(follower);
        }
        if (c != null) {
            code = Integer.parseInt(c);
        }
        if (v_id != null || "".equals(v_id)) {
            view_show_id = Long.valueOf(v_id);
        }

        PrintWriter pw = null;
        if (code == 0) {// star:Parameters:uerId,code=0
            List<Long> list_view_show_id = starService.listStarViewShowIdByUserId(userID);
            List<FindViewDao> find_viewDao_list = new ArrayList<>();
            System.out.println("star拥有以下几个");
            for (Long da : list_view_show_id) {
                System.out.println(da);
            }
            // =================得到这个view_show_id
            for (Long long1 : list_view_show_id) {
                FindViewDao dao = findViewService.get_by_view_showID(long1);
                if (!find_viewDao_list.contains(dao))
                    find_viewDao_list.add(dao);
            }
            rs.setCode(1);
            rs.setData(find_viewDao_list);
            rs.setMessage("ok");
            pw = resp.getWriter();
            pw.print(JSONUtil.toJson(rs));
//				pw.close();
        } else if (code == 1) {// collection:Parameters:code=1,UserID
            List<Long> collection_list = collectionService.list_collection(userID);
            List<FindViewDao> find_viewDao_list = new ArrayList<>();

            System.out.println("collection");
            for (Long da : collection_list) {
                System.out.println(da);
            }

            for (Long long1 : collection_list) {
                FindViewDao dao = findViewService.get_by_view_showID(long1);
                if (!find_viewDao_list.contains(dao))
                    find_viewDao_list.add(dao);
            }
            rs.setCode(1);
            rs.setData(find_viewDao_list);
            rs.setMessage("ok");
            pw = resp.getWriter();
            pw.print(JSONUtil.toJson(rs));
        } else if (code == 3) {// 关注:Parameter:code=3,userId
//改
            List<Long> list_followed_id = userFollowService.get_followed_id(userID);
            List<PersonSettingDao> listPersonSetting = new ArrayList<>();
            for (Long long1 : list_followed_id) {
                String id = String.valueOf(long1);
                PersonSettingDao personSetting = personSettingService.getById(id);
                if (!listPersonSetting.contains(personSetting)){
                    listPersonSetting.add(personSetting);
                }
            }
//            List<PersonDao> list_person = new ArrayList<>();
//            for (Long long1 : list_followed_id) {
//                PersonDao dao = personDaoService.get(long1);
//                if (!list_person.contains(dao)){
//                    list_person.add(dao);
//                    System.out.println("sout " + dao);
//                }
//            }
            personResult.setCode(1);
            personResult.setData(listPersonSetting);
            personResult.setMessage("ok");
            pw = resp.getWriter();
            pw.print(JSONUtil.toJson(rs));


        } else {//否则，4，就返回Star_collection:Parameters:get请求，view_show_id，code = 4
            //返回这个ViewShowID所Star数和Collection数目
            StarCollectionDao dao = starCollectionService.get(view_show_id);
            star_collectionResponseResult.setCode(1);
            star_collectionResponseResult.setData(dao);
            star_collectionResponseResult.setMessage("success");
            pw = resp.getWriter();
            pw.print(JSONUtil.toJson(rs));
        }

    }

    /**
     * Star或者Collection、Follow的行为实现
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // ============================Commom部分
        int code = 1;
        req.setCharacterEncoding("utf-8");
        // 获取相关的参数
        String view_show_id = req.getParameter("view_show_id");
        String who_star = req.getParameter("who_star");
        String who_collection = req.getParameter("who_collection");
        String follower = req.getParameter("follower");
        String followed = req.getParameter("followed");

        String c = req.getParameter("code");

        if (c != null) {
            code = Integer.parseInt(c);
            // ============================================Star
            if (view_show_id != null) {
                if (who_star != null) {
                    if (code == 1) {// insert:Parameters: who_star、view_show_id code = 1
                        star.setView_show_id(Long.valueOf(view_show_id));// 取决于View_show_id和WhoStar
                        star.setWho_star(Long.valueOf(who_star));
                        starService.insert(star);
                        //=======================star+1
                        starCollectionService.update_add_star(Long.valueOf(view_show_id));
                    } else {// delete :Parameters: who_star、view_show_id code = 0
                        Map map = new HashMap<>();
                        map.put("view_show_id", Long.valueOf(view_show_id));
                        map.put("who_star", Long.valueOf(who_star));
                        starService.delete(map);
                        starCollectionService.update_sub_star(Long.valueOf(view_show_id));
                    }
                }
                // ============================================

                // ============================================Collection
                else {
                    Long who_collect = 1L;
                    CollectionDao dao = new CollectionDao();// 取决于View_show_id 和who
                    System.out.println(who_collection);
                    if (who_collection != null)
                        who_collect = Long.valueOf(who_collection);
                    if (code == 1) {// insert :Parameters:view_show_id,who_cellection,code == 1
                        dao.setView_show_id(Long.valueOf(view_show_id));
                        dao.setWho_collection(who_collect);
                        collectionService.insert(dao);
                        //=============collection+1
                        starCollectionService.update_add_collection(Long.valueOf(view_show_id));
                    } else { // delete:Parameters::Parameters:view_show_id,who_cellection,code == 0
                        Map map = new HashMap<>();
                        map.put("view_show_id", Long.valueOf(view_show_id));
                        map.put("who_collection", who_collect);
                        collectionService.delete(map);
                        //=============collection-1
                        starCollectionService.update_sub_collection(Long.valueOf(view_show_id));
                    }
                }
            }
            // ============================================
            // ===========follow=================================
            else {
                Long foer = 1L;
                Long foed = 1L;
                if (follower != null)
                    foer = Long.valueOf(follower);
                if (followed != null)
                    foed = Long.valueOf(followed);
                if (code == 1) {// insert:parameters:follower，followed，code ==1
                    user_followDao.setFollowed(foed);
                    user_followDao.setFollower(foer);
                    userFollowService.insert(user_followDao);
                } else {// delete：parameters:follower，followed，code ==0
                    Map map = new HashMap<>();
                    map.put("follower", foed);
                    map.put("followed", foer);
                    userFollowService.delete(map);
                }
            }
        }
    }
}
