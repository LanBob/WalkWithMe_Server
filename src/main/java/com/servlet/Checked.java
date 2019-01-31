package com.servlet;


import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.ContextUtil;
import org.apache.ibatis.session.SqlSession;

import com.domain.ViewShowDao;
import com.mapper.ViewShowMapper;
import com.util.JSONUtil;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet(urlPatterns = "/app/chat")
public class Checked extends HttpServlet {

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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("chat=============================");
		req.setCharacterEncoding("utf-8");
		String json = req.getParameter("json");
		System.out.println("req"+json);
		ViewShowDao dao = JSONUtil.toBean(json, ViewShowDao.class);
		System.out.println("===Json_Utile_eroor");
		System.out.println("dao is " + dao.toString());
		SqlSession session = null;
		try {
//			session = MyBatisUtil.getSession();
//			View_show_mapper mapper = session.getMapper(View_show_mapper.class);
			ViewShowMapper mapper = ContextUtil.getBean(ViewShowMapper.class);
			mapper.insert(dao);
			session.commit();
			Map<String ,String> map = getHeadersInfo(req);		
			System.out.println("chat=============================");
			
			req.setCharacterEncoding("utf-8");  //设置编码 
	        
	        String path = getServletContext().getContextPath();
	        System.out.println("context path is " + path + ".");
	        path += "/upload";
	        System.out.println("real path is " + path);
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback(true);
		}finally {
			if(session != null)
			session.close();
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
		
        System.out.println("=====================================================");
        req.getInputStream().close();
		InputStream is= null;
		is = req.getInputStream();
		String bodyInfo = IOUtils.toString(is, "utf-8");
		System.out.println("=====================================================");
		System.out.println(" body is :\n"+ bodyInfo);
		System.out.println("=====================================================");
		is.close();		
 * 
// 获取输入输出流		
				BufferedReader reade = new BufferedReader(new InputStreamReader(req.getInputStream(),"UTF-8"));	
				String msg = null;		
				StringBuffer strUrl=req.getRequestURL();
				StringBuilder message= new StringBuilder();		
				message.append(strUrl);
				while ((msg = reade.readLine()) != null) {		
					message.append(msg);	
				}

				PrintWriter pw = resp.getWriter();
				//返回json字符串，作为Token
				pw.print(message);
*/


/*


*/