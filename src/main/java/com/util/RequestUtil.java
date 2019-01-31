package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class RequestUtil {
	public static HashMap<String, Object>  reqToJSONHashMap(HttpServletRequest req) throws IOException{
		
		BufferedReader reader = req.getReader();
		
		// 接收用户端传来的JSON字符串(body体里的数据)
		StringBuilder readerStr = new StringBuilder();
		
		String line;
		while ((line = reader.readLine()) != null) {
			readerStr = readerStr.append(line);
		}
		
		// 使用阿里的fastjson jar包处理json数据(这里是用map进行接收的,你也可以定义vo层容器类接收)
		//得到map形式的JSON请求体数据
		return JSONObject.parseObject(readerStr.toString(), HashMap.class);
	}
}
