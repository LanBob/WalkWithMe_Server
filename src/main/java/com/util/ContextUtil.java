package com.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by ownlove on 2019/1/24.
 */
@Component
public class ContextUtil{


    public static ApplicationContext ctx;

    static {
        ctx = new ClassPathXmlApplicationContext("beans.xml");
    }
    /**
     * 根据类型获得bean
     */
    public static <T> T getBean(Class<T> clazz){
        return ctx.getBean(clazz);
    }
    /**
     * 根据名称名称获得bean
     */
    public static Object getBean(String name){
        return ctx.getBean(name);
    }

}
