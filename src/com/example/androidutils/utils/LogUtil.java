
package com.example.androidutils.utils;

import android.util.Log;

public final class LogUtil{

	// log level
	public static final int LEVEL = Log.DEBUG;

	// tag
	private static final String TAG = "FCC";

	private LogUtil(){
		throw new RuntimeException("不能实例");
	}

	private static boolean isLogable(int level){
		return level >= LEVEL;
	}

	private static String createLogMsg(String message, StackTraceElement[] stackTrace){
		String filename = stackTrace[1].getFileName().substring(0, stackTrace[1].getFileName().indexOf("."));
		String methodname = stackTrace[1].getMethodName();
		String lineNumber = String.valueOf(stackTrace[1].getLineNumber());

		StringBuffer sb = new StringBuffer();
		sb.append("[ ");
		sb.append(filename);
		sb.append(", ");
		sb.append(methodname);
		sb.append(", ");
		sb.append(lineNumber);
		sb.append("] ");
		sb.append(message);

		return sb.toString();
	}

	public static void v(String message){
		if(isLogable(Log.VERBOSE)){
			Log.v(TAG, createLogMsg(message, new Throwable().getStackTrace()));
		}
	}

	public static void d(String message){
		if(isLogable(Log.DEBUG)){
			Log.d(TAG, createLogMsg(message, new Throwable().getStackTrace()));
		}
	}

	public static void i(String message){
		if(isLogable(Log.INFO)){
			Log.i(TAG, createLogMsg(message, new Throwable().getStackTrace()));
		}
	}

	public static void w(String message){
		if(isLogable(Log.WARN)){
			Log.w(TAG, createLogMsg(message, new Throwable().getStackTrace()));
		}
	}

	public static void e(String message){
		if(isLogable(Log.ERROR)){
			Log.e(TAG, createLogMsg(message, new Throwable().getStackTrace()));
		}
	}

	public static void wtf(String message){
		if(isLogable(Log.ASSERT)){
			Log.wtf(TAG, createLogMsg(message, new Throwable().getStackTrace()));
		}
	}

	public static void v(String tag, String message){
		if(isLogable(Log.VERBOSE)){
			Log.v(tag, createLogMsg(message, new Throwable().getStackTrace()));
		}
	}

	public static void d(String tag, String message){
		if(isLogable(Log.DEBUG)){
			Log.d(tag, createLogMsg(message, new Throwable().getStackTrace()));
		}
	}

	public static void i(String tag, String message){
		if(isLogable(Log.INFO)){
			Log.i(tag, createLogMsg(message, new Throwable().getStackTrace()));
		}
	}

	public static void w(String tag, String message){
		if(isLogable(Log.WARN)){
			Log.w(tag, createLogMsg(message, new Throwable().getStackTrace()));
		}
	}

	public static void e(String tag, String message){
		if(isLogable(Log.ERROR)){
			Log.e(tag, createLogMsg(message, new Throwable().getStackTrace()));
		}
	}

	public static void wtf(String tag, String message){
		if(isLogable(Log.ASSERT)){
			Log.wtf(tag, createLogMsg(message, new Throwable().getStackTrace()));
		}
	}

}
