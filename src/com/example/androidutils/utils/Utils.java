
package com.example.androidutils.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

public class Utils{

	public static final String TAG = "Utils";
	public static final String DATEPATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 将dp转换成px
	 *
	 * @param context
	 * @param dp
	 *            dp值
	 * @return 相应的px值
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
	 *
	 * @param context
	 * @param sp
	 *            sp值
	 * @return 相应的px值
	 * @author ljh
	 */
	public static int sp2px(Context context, int sp){
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);
		return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, metrics));
	}

	/**
	 * 转换时间成yyyy-MM-dd HH:mm:ss格式字符
	 *
	 * @param date
	 *            待转换的时间
	 * @return 相应输出格式的时间字符串
	 * @author ljh
	 */
	public static String convertDateToString(Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATEPATTERN, Locale.CHINA);
		return dateFormat.format(date);
	}

	/**
	 * 安装App
	 *
	 * @param filePath
	 *            apk文件的路径
	 * @author ljh
	 */
	public static void installApp(Context context, String filePath){
		if(TextUtils.isEmpty(filePath)){
			LogUtil.w(TAG, "路径为空");
			return;
		}

		File apkFile = new File(filePath);
		if(!apkFile.exists()){
			LogUtil.w(TAG, "文件不存在");
			return;
		}

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
		context.startActivity(intent);
	}
}
