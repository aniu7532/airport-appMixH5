package com.tencent.shadow.sample.plugin;

import android.app.Application;

import com.tencent.shadow.sample.plugin.utils.LogUtils;

public class PluginApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.logD("plugin_1_application_onCreate");
    }
}
