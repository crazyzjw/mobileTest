package com.demo.myapplication.utils;

import android.util.Log;

import com.google.gson.Gson;

/**
 * Log统一管理类
 */
public class LogUtil {

    private LogUtil() { 
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = true;
    private static final String TAG = "zjw";

    // 下面四个是默认tag的函数 
    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG,""+ msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG,""+ msg);
    }
    public static void d(Object o) {
        if (isDebug)
            Log.d(TAG,""+ new Gson().toJson(o));
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG,""+ msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG,""+ msg);
    }


    // 下面是传入自定义tag的函数 
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag,""+ msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag,""+ msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag,""+ msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag,""+ msg);
    }
    //规定每段显示的长度
    private static int LOG_MAXLENGTH = 2000;

    public static void dd(String msg) {
        if (isDebug){
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                //剩下的文本还是大于规定长度则继续重复截取并输出
                if (strLength > end) {
                    Log.e(TAG + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.d(TAG, msg.substring(start, strLength));
                    break;
                }
            }
        }

    }
}