package com.glhd.tb.app.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author chenliang
 * 
 */
public class MyDate {
	/*
	 * 格式化时间
	 */
	public static String format(long d, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date(d);
		return dateFormat.format(date);
	}

	/*
	 * 格式化时间
	 */
	public static String format(String d, String format) {
		if (d == null || "".equals(d.trim()))
			return "";
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date(Long.parseLong(d) );
		return dateFormat.format(date);
	}

	/*
	 * 格式化时间
	 */
	public static String format(Date d, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(d);
	}

	/*
	 * 是否过期
	 */
	public static String converTime(long timestamp) {
		long currentSeconds = System.currentTimeMillis() / 1000;
		long timeGap = currentSeconds - timestamp;// 与现在时间相差秒数
		String timeStr = null;
		if (timeGap > 24 * 60 * 60) {// 1天以上
			timeStr = timeGap / (24 * 60 * 60) + "天前";
		} else if (timeGap > 60 * 60) {// 1小时-24小时
			timeStr = timeGap / (60 * 60) + "小时前";
		} else if (timeGap > 60) {// 1分钟-59分钟
			timeStr = timeGap / 60 + "分钟前";
		} else {// 1秒钟-59秒钟
			timeStr = "刚刚";
		}
		return timeStr;
	}

	/*
	 * 是否过期
	 */
	public static String restTime(String timestamp) {
		if (timestamp == null || "".equals(timestamp.trim()))
			return "";
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;

		diff = Long.parseLong(timestamp) * 1000;

		day = diff / nd;// 计算差多少天
		hour = diff % nd / nh + day * 24;// 计算差多少小时
		min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
		sec = diff % nd % nh % nm / ns;// 计算差多少秒

		return day + "天" + hour + "时" + min + "分" + sec + "秒";
	}
}
