package com.servlet;

import com.domain.HeadImage;
import com.domain.IsGoodMan;
import com.domain.ResponseResult;
import com.service.IIsGoodManService;
import com.util.JSONUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * Created by ownlove on 2019/2/28.
 */
@Component
@WebServlet("/app/isgoodman")
public class IsGoodManServlet extends HttpServlet {

    //获得磁盘文件条目工厂
    DiskFileItemFactory factory;

    @Autowired
    private IIsGoodManService iIsGoodManService;

    @Autowired
    private ResponseResult<List<IsGoodMan>> responseResult;

    @Override
    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        code = 0: 查询还没有评分的
//        code = 1；查询已经评分了的
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String code = req.getParameter("code");
        PrintWriter printWriter = null;
        if("0".equals(code)){
            List<IsGoodMan> notScoreList = iIsGoodManService.listAllNotScore();
            responseResult.setCode(1);
            responseResult.setMessage("ok");
            responseResult.setData(notScoreList);
            printWriter = resp.getWriter();
            printWriter.write(JSONUtil.toJson(responseResult));
        }else if("1".equals(code)){
            List<IsGoodMan> alreadyScoreList = iIsGoodManService.listAllAlreadyScore();
            responseResult.setCode(1);
            responseResult.setMessage("ok");
            responseResult.setData(alreadyScoreList);
            printWriter = resp.getWriter();
            printWriter.write(JSONUtil.toJson(responseResult));
        }else {
            responseResult.setCode(1);
            responseResult.setMessage("ok");
            responseResult.setData(null);
            printWriter = resp.getWriter();
            printWriter.write(JSONUtil.toJson(responseResult));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        save(req,resp);
    }

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

    private void save(HttpServletRequest req, HttpServletResponse resp) {
        //获得磁盘文件条目工厂
        if (factory == null) {
            factory = new DiskFileItemFactory();
        }

        //改为/user/local/upload
        String path = "D:\\upload";

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
        String json = "";
        try {
            List<FileItem> list = (List<FileItem>) upload.parseRequest(req);
            for (FileItem item : list) {
                //获取属性名字
                String name = item.getFieldName();
                //如果获取的 表单信息是普通的文本信息
                if (item.isFormField()) {
                    //获取用户具体输入的字符串,因为表单提交过来的是 字符串类型的
                    json = item.getString();//phone在这里
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
                    System.out.println("上传成功：" + path +"\\"+ filename);
                }
            }
            IsGoodMan isGoodMan = new IsGoodMan();
            isGoodMan = JSONUtil.toBean(json, IsGoodMan.class);
            if(isGoodMan.getIntroduce() != null && isGoodMan.getIntroduce().trim() != ""){
                if(isGoodMan.getUserId() ==null)
                    isGoodMan.setUserId("100");
                int i = iIsGoodManService.insert(isGoodMan);
                if (i == 1) {
                    feedBack(resp, 1);
                } else {
                    feedBack(resp, 0);
                }
            }else {
                feedBack(resp,0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传失败");
        }

    }
}
