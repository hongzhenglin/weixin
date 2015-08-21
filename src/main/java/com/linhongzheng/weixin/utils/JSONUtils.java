package com.linhongzheng.weixin.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 
 * json 帮助类
 * 
 * @author linhz
 * 
 */
public class JSONUtils {

	public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 通过URL获取json字符串
	 * 
	 * @param urlStr
	 *            url地址串
	 * @return json字符串
	 */
	public static String getJsonContent(String urlStr) {
		try {
			// 获取HttpURLConnection连接对象
			URL url = new URL(urlStr);
			HttpURLConnection httpConn = (HttpURLConnection) url
					.openConnection();
			// 设置连接属性
			httpConn.setConnectTimeout(3000);
			httpConn.setDoInput(true);
			httpConn.setRequestMethod("GET");
			// 获取相应码
			int respCode = httpConn.getResponseCode();
			if (respCode == 200) {
				return convertStreamToJson(httpConn.getInputStream());
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * json字符串转为对象
	 * 
	 * @param <T>
	 * @param jsonString
	 *            json字符串
	 * @param cls
	 *            java类
	 * @return java对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T jsonToObject(String jsonString, Class<?> cls) {
		T t = null;
		try {
			t = (T) JSON.parseObject(jsonString, cls);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return t;
	}

	/**
	 * 对象转为json字符串
	 * 
	 * @param obj
	 *            java对象
	 * @param quotesType
	 *            : 0:为双引号 1：单引号
	 * @return json字符串
	 */
	public static String objectToJson(Object obj, String quotesType) {
		String jsonString = null;
		try {
			if (quotesType == "0") {
				jsonString = JSON.toJSONString(obj);
			} else if (quotesType == "1") {
				jsonString = JSON.toJSONString(obj,
						SerializerFeature.UseSingleQuotes,
						SerializerFeature.DisableCircularReferenceDetect);
			} else {
				jsonString = JSON.toJSONString(obj, true);
			}

		} catch (Exception e) {
			e.getStackTrace();
		}
		return jsonString;
	}

	/**
	 * 对象转为json字符串
	 * 
	 * @param obj
	 *            java对象
	 * @return json字符串
	 */
	public static String ToJsonString(Object obj) {
		String jsonString = null;
		try {
			jsonString = JSON.toJSONString(obj,
					SerializerFeature.DisableCircularReferenceDetect);

		} catch (Exception e) {
			e.getStackTrace();
		}
		return jsonString;
	}

	public static String toJsonString(Object obj) {
		return ToJsonString(obj);
	}

	public static String toPrettyJasonString(Object obj) {
		String jsonString = null;
		try {
			jsonString = JSON.toJSONString(obj,
					SerializerFeature.DisableCircularReferenceDetect,
					SerializerFeature.PrettyFormat);

		} catch (Exception e) {
			e.getStackTrace();
		}
		return jsonString;
	}

	/**
	 * 对象转为json字符串 (dateFormat:"yyyy-MM-dd HH:mm:ss")
	 * 
	 * @param obj
	 *            java对象
	 * @return json字符串
	 */
	public static String objectToDateFormatJson(Object obj) {
		String jsonString = null;

		try {
			jsonString = JSON.toJSONStringWithDateFormat(obj, YYYYMMDDHHMMSS,
					SerializerFeature.WriteDateUseDateFormat,
					SerializerFeature.DisableCircularReferenceDetect);

		} catch (Exception e) {
			e.getStackTrace();
		}
		return jsonString;
	}

	/**
	 * 有日期的对象转为json字符串
	 * 
	 * @param obj
	 *            java对象
	 * @param dateFormatString
	 *            "yyyy-MM-dd HH:mm:ss.SSS","yyyy-MM-dd"等等
	 * @param quotesType
	 *            : 0:为双引号 1：单引号
	 * @return json字符串
	 */
	public static String objectToJson(Object obj, String dateFormatString,
			String quotesType) {
		String jsonString = null;
		try {
			if (quotesType == "0") {
				jsonString = JSON.toJSONStringWithDateFormat(obj,
						dateFormatString,
						SerializerFeature.WriteDateUseDateFormat,
						SerializerFeature.DisableCircularReferenceDetect);
			} else if (quotesType == "1") {
				jsonString = JSON.toJSONStringWithDateFormat(obj,
						dateFormatString,
						SerializerFeature.WriteDateUseDateFormat,
						SerializerFeature.UseSingleQuotes,
						SerializerFeature.DisableCircularReferenceDetect);
			} else {
				jsonString = JSON.toJSONStringWithDateFormat(obj,
						dateFormatString,
						SerializerFeature.WriteDateUseDateFormat,
						SerializerFeature.DisableCircularReferenceDetect);
			}

		} catch (Exception e) {
			e.getStackTrace();
		}
		return jsonString;
	}

	/**
	 * List转json字符串
	 * 
	 * @param list
	 *            list容器
	 * @param quotesType
	 *            : 0:为双引号 1：单引号
	 * @return json字符串
	 */
	public static String listToJsonString(List<?> list, String quotesType) {
		String jsonString = null;
		try {
			if (quotesType == "0") {
				jsonString = JSON.toJSONString(list);
			} else if (quotesType == "1") {
				jsonString = JSON.toJSONString(list,
						SerializerFeature.UseSingleQuotes,
						SerializerFeature.DisableCircularReferenceDetect);
			} else {
				jsonString = JSON.toJSONString(list, true);
			}

		} catch (Exception e) {
			e.getStackTrace();
		}
		return jsonString;
	}

	/**
	 * json字符串转对象集(List)
	 * 
	 * @param jsonString
	 *            json字符串
	 * @param cls
	 *            类
	 * @return 对象List集合
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> jsonToObjectList(String jsonString, Class<?> cls) {
		List<Object> list = new ArrayList<Object>();
		try {
			list = (List<Object>) JSON.parseArray(jsonString, cls);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return list;
	}

	/**
	 * json字符串转List字符串
	 * 
	 * @param jsonString
	 *            json字符串
	 * @return List字符串
	 */
	public static List<String> jsonToStringList(String jsonString) {
		List<String> list = new ArrayList<String>();
		try {
			list = JSON.parseObject(jsonString,
					new TypeReference<List<String>>() {
					});
		} catch (Exception e) {
			e.getStackTrace();
		}
		return list;
	}

	/**
	 * map转json字符串 ,默认使用双引号
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String mapToJsonString(Map map) {
		return mapToJsonString(map, "0");
	}

	/**
	 * map转json字符串 
	 * 
	 * @param map
	 *            Map容器
	 * @param quotesType
	 *            : 0:为双引号 1：单引号
	 * @return json字符串
	 */
	@SuppressWarnings("rawtypes")
	public static String mapToJsonString(Map map, String quotesType) {
		String jsonString = null;
		try {
			if (quotesType == "0") {
				jsonString = JSON.toJSONString(map);
			} else if (quotesType == "1") {
				jsonString = JSON.toJSONString(map,
						SerializerFeature.UseSingleQuotes);
			} else {
				jsonString = JSON.toJSONString(map, true);
			}

		} catch (Exception e) {
			e.getStackTrace();
		}
		return jsonString;
	}

	/**
	 * json字符串转map
	 * 
	 * @param jsonString
	 *            json字符串
	 * @return Map集合
	 */
	public static Map<String, Object> jsonToMap(String jsonString) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = JSON.parseObject(jsonString,
					new TypeReference<Map<String, Object>>() {
					});
		} catch (Exception e) {
			e.getStackTrace();
		}
		return map;
	}

	/**
	 * json字符串转map集
	 * 
	 * @param jsonString
	 *            json字符串
	 * @return Map集合
	 */
	public static List<Map<String, Object>> jsonToMapList(String jsonString) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = JSON.parseObject(jsonString,
					new TypeReference<List<Map<String, Object>>>() {
					});
		} catch (Exception e) {
			e.getStackTrace();
		}
		return list;
	}

	/**
	 * json字符串对象数组
	 * 
	 * @param jsonString
	 *            json字符串
	 * @return 对象数组
	 */
	public static Object[] jsonToObjectArray(String jsonString) {
		Object[] arry = null;
		try {

			arry = JSON.parseArray(jsonString).toArray();
		} catch (Exception e) {
			e.getStackTrace();
		}
		return arry;
	}

	/**
	 * json字符串json数组
	 * 
	 * @param jsonString
	 *            json字符串
	 * @return json数组对象
	 */
	public static JSONArray jsonToJSONArray(String jsonString) {
		JSONArray jsonArr = null;
		try {

			jsonArr = JSON.parseArray(jsonString);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return jsonArr;
	}

	/**
	 * 数组转json字符串
	 * 
	 * @param strs
	 *            字符串数组
	 * @param quotesType
	 *            : 0:为双引号 1：单引号
	 * @return json字符串
	 */
	public static String arryToJsonString(String[] strs, String quotesType) {
		String jsonString = null;
		try {
			if (quotesType == "0") {
				jsonString = JSON.toJSONString(strs);
			} else if (quotesType == "1") {
				jsonString = JSON.toJSONString(strs,
						SerializerFeature.UseSingleQuotes);
			} else {
				jsonString = JSON.toJSONString(strs, true);
			}

		} catch (Exception e) {
			e.getStackTrace();
		}
		return jsonString;
	}

	/**
	 * 数组转json字符串
	 * 
	 * @param ary
	 *            数组
	 * @param quotesType
	 *            : 0:为双引号 1：单引号
	 * @return json字符串
	 */
	public static String arryToJsonString(Array ary, String quotesType) {
		String jsonString = null;
		try {
			if (quotesType == "0") {
				jsonString = JSON.toJSONString(ary);
			} else if (quotesType == "1") {
				jsonString = JSON.toJSONString(ary,
						SerializerFeature.UseSingleQuotes);
			} else {
				jsonString = JSON.toJSONString(ary, true);
			}

		} catch (Exception e) {
			e.getStackTrace();
		}
		return jsonString;
	}

	/**
	 * 数据流转json字符串
	 * 
	 * @param inputStream
	 *            数据流
	 * @return json字符串
	 */
	private static String convertStreamToJson(InputStream inputStream) {
		String jsonStr = "";
		// ByteArrayOutputStream相当于内存输出流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		// 将输入流转移到内存输出流中
		try {
			while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, len);
			}
			// 将内存流转换为字符串
			jsonStr = new String(out.toByteArray());
		} catch (Exception e) {

			e.printStackTrace();
		}
		return jsonStr;
	}

}
