package com.service.impl;


import com.service.IMessageCode;
import com.util.HttpUtils;
import com.util.StrUtil;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 重要提示如下:
 * HttpUtils请从
 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
 * 下载
 *
 * 相应的依赖请参照
 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
 *
 * Created by ownlove on 2019/2/15.
 */
@Component
public class MessageCodeImpl implements IMessageCode {

    @Override
    public String sendCodeToUser(String userPhone) {
        String host = "https://dxyzm.market.alicloudapi.com";
        String path = "/chuangxin/dxjk";
        String method = "POST";
        String appcode = "866575673a98434191d4c11d9d60a763";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();


        String content = "【walk旅游】 验证码为:"+ StrUtil.getMessageCode() + "，于3分钟内有效，请您牢记。";
        querys.put("content", content);
        querys.put("mobile", userPhone);
        Map<String, String> bodys = new HashMap<String, String>();

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            return response.toString();
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
