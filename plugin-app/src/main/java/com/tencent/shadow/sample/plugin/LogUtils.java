package com.tencent.shadow.sample.plugin;

import android.util.Log;

public class LogUtils {

    private static final String TAG = "TAG";


    public static void logD(String str) {
        Log.d(TAG, "----------------------------------------".concat(str));
    }

    public static void logE(String str) {
        Log.e(TAG, "----------------------------------------".concat(str));
    }


}
