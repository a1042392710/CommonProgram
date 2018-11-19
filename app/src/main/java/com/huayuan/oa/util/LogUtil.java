package com.huayuan.oa.util;

import android.util.Log;

/**
 * Created by chenhao on 2017/4/18.
 */
//日志工具
public class LogUtil {

    /**
     * 是否显示日志，发布正式版本时将 isShowing 设为 false
     */
    public static boolean isShowing = true;


    public static void v(String tag, String msg) {
        if (isShowing) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isShowing) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isShowing) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isShowing) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isShowing) {
            Log.e(tag, msg);
        }
    }


}
