package com.util;


import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JSONUtil {
	/**
	 * 默认json格式化方式
	 */
	public static final SerializerFeature[] DEFAULT_FORMAT = { SerializerFeature.WriteDateUseDateFormat,
			SerializerFeature.WriteEnumUsingToString, SerializerFeature.WriteNonStringKeyAsString,
			SerializerFeature.QuoteFieldNames, SerializerFeature.SkipTransientField, SerializerFeature.SortField,
			SerializerFeature.PrettyFormat };

	private JSONUtil() {
	}

	/**
	 * 
	 * @param jsonStr
	 * @param beanClass
	 * @return
	 */
	public static <T> List<T> parseArraytoBean(String jsonStr, Class<T> beanClass) {
		requireNonNull(jsonStr, "jsonStr is null");
		// JSONObject jo = JSON.parseObject(jsonStr);
		// List<Date_find_dao> list = JSON.parseArray(s, Date_find_dao.class);
		List<T> list = (List<T>) JSON.parseArray(jsonStr, beanClass);
		return list;
	}

	/**
	 * 从json获取指定key的字符串
	 *
	 * @param json
	 *            json字符串
	 * @param key
	 *            字符串的key
	 * @return 指定key的值
	 */
	public static Object getStringFromJSONObject(final String json, final String key) {
		requireNonNull(json, "json is null");
		return JSON.parseObject(json).getString(key);
	}

	/**
	 * 将字符串转换成JSON字符串
	 *
	 * @param jsonString
	 *            json字符串
	 * @return 转换成的json对象
	 */
	public static JSONObject getJSONFromString(final String jsonString) {
		if (isBlank(jsonString)) {
			return new JSONObject();
		}
		return JSON.parseObject(jsonString);
	}

	/**
	 * 将json字符串，转换成指定java bean
	 *
	 * @param jsonStr
	 *            json串对象
	 * @param beanClass
	 *            指定的bean
	 * @param <T>
	 *            任意bean的类型
	 * @return 转换后的java bean对象
	 */
	public static <T> T toBean(String jsonStr, Class<T> beanClass) {
		requireNonNull(jsonStr, "jsonStr is null");
		JSONObject jo = JSON.parseObject(jsonStr);
		jo.put(JSON.DEFAULT_TYPE_KEY, beanClass.getName());
		return JSON.parseObject(jo.toJSONString(), beanClass);
	}

	/**
	 * @param obj
	 *            需要转换的java bean
	 * @param <T>
	 *            入参对象类型泛型
	 * @return 对应的json字符串
	 */
	public static <T> String toJson(T obj) {
		requireNonNull(obj, "obj is null");
		return JSON.toJSONString(obj, DEFAULT_FORMAT);
	}

	/**
	 * 通过Map生成一个json字符串
	 *
	 * @param map
	 *            需要转换的map
	 * @return json串
	 */
	public static String toJson(Map<String, Object> map) {
		requireNonNull(map, "map is null");
		return JSON.toJSONString(map, DEFAULT_FORMAT);
	}

	/**
	 * 美化传入的json,使得该json字符串容易查看
	 *
	 * @param jsonString
	 *            需要处理的json串
	 * @return 美化后的json串
	 */
	public static String prettyFormatJson(String jsonString) {
		requireNonNull(jsonString, "jsonString is null");
		return JSON.toJSONString(getJSONFromString(jsonString), true);
	}

	/**
	 * 将传入的json字符串转换成Map
	 *
	 * @param jsonString
	 *            需要处理的json串
	 * @return 对应的map
	 */
	public static Map<String, Object> toMap(String jsonString) {
		requireNonNull(jsonString, "jsonString is null");
		return getJSONFromString(jsonString);
	}

	/**
	 * 判断对象是否为空，如果为空，直接抛出异常
	 *
	 * @param object
	 *            需要检查的对象
	 * @param errorMessage
	 *            异常信息
	 * @return 非空的对象
	 */
	public static Object requireNonNull(Object object, String errorMessage) {
		if (null == object) {
			throw new NullPointerException(errorMessage);
		}
		return object;
	}

	/**
	 * 判断一个字符串是否为空，null也会返回true
	 *
	 * @param str
	 *            需要判断的字符串
	 * @return 是否为空，null也会返回true
	 */
	public static boolean isBlank(String str) {
		return null == str || "".equals(str.trim());
	}
}

/*
 * JSON知识：JSON 是轻量级的文本数据交换格式 JSON 
 * 是纯文本 
 * 数据在名称/值对中 
 * 数据由逗号分隔 
 * 花括号保存对象 
 * 方括号保存数组
 * 
 * "firstName" : "John" 相当于firstName = "John"
 * 数字（整数或浮点数） 字符串（在双引号中）逻辑值（true 或 false） 数组（在方括号中）对象（在花括号中） null（最好不是null）
 *
 *
 *JSON 对象在花括号中书写：{ "firstName":"John" , "lastName":"Doe" }
 *相当于：
 *firstName = "John"
 *lastName = "Doe"
 *
 *JSON 数组
 *JSON 数组在方括号中书写：
 *{
 *	"employees": [
 *		{ "firstName":"John" , "lastName":"Doe" },
 *		{ "firstName":"Anna" , "lastName":"Smith" },
 *		{ "firstName":"Peter" , "lastName":"Jones" }
 *	]
 *}
 *
 *数组a=[1,2,3,4]，还有一个对象a={0:1,1:2,2:3,3:4}
 *数组是有序的集合，对象是无序的集合
 */
