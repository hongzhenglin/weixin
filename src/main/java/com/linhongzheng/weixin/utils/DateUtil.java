package com.linhongzheng.weixin.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {
	public static final String pattern19ToDate = "yyyy-MM-dd HH24:mm:ss";

	public static String getMondayOfWeek() {
		DateFormat df = new SimpleDateFormat(pattern19ToDate);
		Calendar c = Calendar.getInstance();
		// 得到今天是星期几
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		// 对星期天特殊处理
		if (dayOfWeek == 0) {
			dayOfWeek = 7;
		}
		c.add(Calendar.DATE, -(dayOfWeek - 1));

		return df.format(c.getTime());

	}

	/**
	 * 获取前/后n天日期(M月d日)
	 * 
	 * @return
	 */
	public static String getMonthDay(int diff) {
		DateFormat df = new SimpleDateFormat("M月d日");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, diff);
		return df.format(c.getTime());
	}

}
