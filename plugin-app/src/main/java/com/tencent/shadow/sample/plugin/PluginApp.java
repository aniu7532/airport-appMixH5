package com.tencent.shadow.sample.plugin;

import android.app.Application;
import android.content.Intent;

import com.tencent.shadow.sample.plugin.utils.LogUtils;

public class PluginApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.logD("plugin_1_application_onCreate");

        //当manager中启动插件的application的是时候，可以通过一下方式在启动主界面

//        //启动activity
//        Intent intent=new Intent(this,MainActivity1.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
    }


}
