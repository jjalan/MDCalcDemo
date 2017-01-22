package com.yoaccess.mdcalcdemo;

/**
 * Created by jjalan on 1/22/17.
 * Copyright (C) 2016 Binary Meter Technologies Pvt. Ltd. - All Rights Reserved
 */

public class Log {

    public static boolean ENABLE_LOG = false;

    public static void v(String tag, String msg) {
        if (ENABLE_LOG) {
            android.util.Log.v(tag, getThreadName() + msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (ENABLE_LOG) {
            android.util.Log.v(tag, getThreadName() + msg, tr);
        }
    }

    public static void d(String tag, String msg) {
        if (ENABLE_LOG) {
            android.util.Log.d(tag, getThreadName() + msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (ENABLE_LOG) {
            android.util.Log.d(tag, getThreadName() + msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (ENABLE_LOG) {
            android.util.Log.i(tag, getThreadName() + msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (ENABLE_LOG) {
            android.util.Log.i(tag, getThreadName() + msg, tr);
        }
    }

    public static void w(String tag, String msg) {
        if (ENABLE_LOG) {
            android.util.Log.w(tag, getThreadName() + msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (ENABLE_LOG) {
            android.util.Log.w(tag, getThreadName() + msg, tr);
        }
    }

    public static void w(String tag, Throwable tr) {
        if (ENABLE_LOG) {
            android.util.Log.w(tag, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (ENABLE_LOG) {
            android.util.Log.e(tag, getThreadName() + msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (ENABLE_LOG) {
            android.util.Log.e(tag, getThreadName() + msg, tr);
        }
    }

    private static String getThreadName() {
        return "[" + Thread.currentThread().getName() + "] ";
    }
}