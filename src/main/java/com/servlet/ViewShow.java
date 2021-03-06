package com.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domain.*;
import com.service.IFindViewService;
import com.service.IInterScoreService;
import com.service.IStarCollectionService;
import com.service.IViewShowService;
import com.util.StrUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.mapper.FindViewMapper;
import com.mapper.StarCollectionMapper;
import com.mapper.ViewShowMapper;
import com.util.JSONUtil;
import org.apache.commons.lang.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * 上传旅游信息页面
 */
@Component
@WebServlet(urlPatterns = "/app/up_view_show")
public class ViewShow extends HttpServlet {
    //获得磁盘文件条目工厂
    DiskFileItemFactory factory;
    private static final long serialVersionUID = 1L;

    @Autowired
    private ResponseResult<String> result;

    @Autowired
    private ViewShowDao view_showDao;

    @Autowired
    private FindViewDao find_viewDao;

    @Autowired
    private IViewShowService viewShowService;

    @Autowired
    private IFindViewService findViewService;

    @Autowired
    private StarCollectionDao star_collectionDao;

    @Autowired
    private IStarCollectionService starCollectionService;

    @Autowired
    private IInterScoreService interScoreService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    // 只处理get请求
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
//        1、个人中心，进行查询自己的ViewShow的状态,根据Score进行显示
//        2、管理员查询，对是60-100分的项目进行查询
//        3、给予相互导游验证的随机查询，查询拥有的一个人
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String code = req.getParameter("code");

        if ("0".equals(code)) {
//            管理员进行查询60-100分之间的ViewShow
            ResponseResult<List<ViewShowDao>> responseResult = new ResponseResult<>();
            List<ViewShowDao> list = viewShowService.getViewShowAlreadyScoreByOthers();
            responseResult.setData(list);
            responseResult.setCode(1);
            responseResult.setMessage("ok");
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(JSONUtil.toJson(responseResult));
        } else if ("1".equals(code)) {
//            个人中心进行查询
            String userId = req.getParameter("userId");
            System.out.println("1" + userId);
            ResponseResult<List<ViewShowDao>> responseResult = new ResponseResult<>();
            List<ViewShowDao> list = viewShowService.getViewShowByUserId(userId);
            responseResult.setData(list);
            responseResult.setCode(1);
            responseResult.setMessage("ok");
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(JSONUtil.toJson(responseResult));
        } else if ("2".equals(code)) {
//          进行评分操作，并将开放findView


        }
    }

    // 只处理POST请求
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        try {
            save(req, resp);//处理结果
            result.setCode(1);
            result.setMessage("success");
            result.setData(view_showDao.getId() + "");
            PrintWriter pw = resp.getWriter();
            pw.print(JSONUtil.toJson(result));
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(0);
            result.setMessage("success");
            result.setData(view_showDao.getId() + "");
            PrintWriter pw = resp.getWriter();
            pw.print(JSONUtil.toJson(result));
        }

    }


    private void save(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //获得磁盘文件条目工厂
        if (factory == null) {
            factory = new DiskFileItemFactory();
        }
        //获取文件需要上传到的路径 

        //改为/user/local/upload
//        String path = "D:\\upload";
        String path = StrUtil.pathUrl;
        /*
            上传成功：D:\mysoftware\eclipseworkspace\Final\WebContent
        	upload\IMG_-1811728065.jpg
        */
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        factory.setRepository(new File(path));
        //设置 缓存的大小  
        factory.setSizeThreshold(1024 * 1024 * 10);
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> list = (List<FileItem>) upload.parseRequest(req);
        for (FileItem item : list) {
            //获取属性名字
            String name = item.getFieldName();
            //如果获取的 表单信息是普通的 文本 信息
            if (item.isFormField()) {
                //获取用户具体输入的字符串,因为表单提交过来的是 字符串类型的
                String json = item.getString();
//                    System.out.println(json);
                view_showDao = JSONUtil.toBean(json, ViewShowDao.class);//转为dao类型
//                    System.out.println(view_showDao.toString());
//                    save_view_show_dao(view_showDao);//保存操作
                viewShowService.insert(view_showDao);
            } else {
                //获取路径名
                String value = item.getName();
                //索引到最后一个反斜杠
                int start = value.lastIndexOf("\\");
                //截取 上传文件的 字符串名字，加1是 去掉反斜杠
                String filename = value.substring(start + 1);
                System.out.println("filename" + filename);
                req.setAttribute(name, filename);
                File fff = new File(path, filename);
                if (fff == null) filename += "null";
                //写到磁盘上
                item.write(fff);
                System.out.println("上传成功：" + path + filename);
            }
        }
    }

    //输出header的信息或者传输的信息
    private Map<String, String> getHeadersInfo(HttpServletRequest req) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = req.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}