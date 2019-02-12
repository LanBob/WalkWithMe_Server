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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.domain.ResponseResult;
import com.util.JSONUtil;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet(urlPatterns = "/app/message")
public class ChatServlet extends HttpServlet {
	// 获得磁盘文件条目工厂
	DiskFileItemFactory factory;

	private static final long serialVersionUID = 1L;


	@Override
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				config.getServletContext());
	}
	// 只处理get请求
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	// 只处理POST请求
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Map<String, String> map = getHeadersInfo(req);
		PrintWriter pw = null;

		System.out.println("=============================");

		req.setCharacterEncoding("utf-8"); // 设置编码
		resp.setCharacterEncoding("utf-8");

		// 获得磁盘文件条目工厂
		if (factory == null) {
			factory = new DiskFileItemFactory();
		}
		// 获取文件需要上传到的路径

		String path = req.getSession().getServletContext().getContextPath();
		System.out.println("context path is " + path + ".");
		path += "/upload";

		System.out.println("real path is " + path);

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		factory.setRepository(new File(path));
		// 设置 缓存的大小
		factory.setSizeThreshold(1024 * 1024);
		// 文件上传处理
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			// 可以上传多个文件
			List<FileItem> list = (List<FileItem>) upload.parseRequest(req);
			for (FileItem item : list) {
				// 获取属性名字
				String name = item.getFieldName();
				// 如果获取的 表单信息是普通的 文本 信息
				if (item.isFormField()) {
					// 获取用户具体输入的字符串,因为表单提交过来的是 字符串类型的
					String value = item.getString();
					req.setAttribute(name, value);
				} else {
					// 获取路径名
					String value = item.getName();
					// 索引到最后一个反斜杠
					int start = value.lastIndexOf("\\");
					// 截取 上传文件的 字符串名字，加1是 去掉反斜杠，
					String filename = value.substring(start + 1);
					req.setAttribute(name, filename);
					File fff = new File(path, filename);
					if (fff == null) {
						filename += "null";
					}
					// 写到磁盘上
					item.write(fff);// 第三方提供的
					System.out.println("上传成功：" + filename);

					ResponseResult<String> result = new ResponseResult<>();
					result.setCode(1);
					result.setMessage("succees");
					result.setData(filename);
					System.out.println(JSONUtil.toJson(result));
					pw = resp.getWriter();
					pw.print(JSONUtil.toJson(result));// 将路径返回给客户端
					pw.close();
				}
			}

		} catch (Exception e) {
			System.out.println("上传失败");
			pw = resp.getWriter();
			pw.print("上传失败：" + e.getMessage());
			pw.close();
		}finally {
			if(pw != null) {
				pw.close();
			}
		}
	}

	private Map<String, String> getHeadersInfo(HttpServletRequest req) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration headerNames = req.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = req.getHeader(key);
			System.out.println(key + ": " + value);
			map.put(key, value);
		}
		return map;
	}
}
/*
 * 
 * 通过commom.io的IOUtils.toString(in,charset);
 * 
 * System.out.println("=====================================================");
 * req.getInputStream().close(); InputStream is= null; is =
 * req.getInputStream(); String bodyInfo = IOUtils.toString(is, "utf-8");
 * System.out.println("=====================================================");
 * System.out.println(" body is :\n"+ bodyInfo);
 * System.out.println("=====================================================");
 * is.close();
 * 
 * // 获取输入输出流 BufferedReader reade = new BufferedReader(new
 * InputStreamReader(req.getInputStream(),"UTF-8")); String msg = null;
 * StringBuffer strUrl=req.getRequestURL(); StringBuilder message= new
 * StringBuilder(); message.append(strUrl); while ((msg = reade.readLine()) !=
 * null) { message.append(msg); }
 * 
 * PrintWriter pw = resp.getWriter(); //返回json字符串，作为Token pw.print(message);
 */