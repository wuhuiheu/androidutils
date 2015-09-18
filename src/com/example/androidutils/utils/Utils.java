package com.example.androidutils.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;


public class Utils{

	public static final String DATEPATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 将dp转换成px
	 * @param context
	 * @param dp dp�?
	 * @return 相应的px�?
	 * @author ljh
	 */
	public static int dp2px(Context context, int dp){
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);
		return Math.round(metrics.density * dp);
	}

	/**
	 * 将sp转换成px
	 * @param context
	 * @param sp sp�?
	 * @return 相应的px�?
	 * @author ljh
	 */
	public static int sp2px(Context context, int sp){
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);
		return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, metrics));
	}

	/**
	 * 转换时间成yyyy-MM-dd HH:mm:ss格式字符�?
	 * @param date 待转换的时间
	 * @return 相应输出格式的时间字符串
	 * @author ljh
	 */
	public static String convertDateToString(Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATEPATTERN, Locale.CHINA);
		return dateFormat.format(date);
	}
}
