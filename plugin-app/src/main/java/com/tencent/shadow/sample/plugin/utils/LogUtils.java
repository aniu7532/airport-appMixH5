package com.tencent.shadow.sample.plugin.utils;

import android.util.Log;

public class LogUtils {

    private static final String TAG = "TAG";


    public static void logD(String str) {
        Log.d(TAG, "----------------------------------------[plugin-project]  :  ".concat(str));
    }

    public static void logE(String str) {
        Log.e(TAG, "----------------------------------------[plugin-project]  :  ".concat(str));
    }


}
