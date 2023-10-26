package com.tencent.shadow.sample.plugin;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class PluginOneBaseActivity extends AppCompatActivity {


    public Context context;
    public Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        activity=this;
    }

    /**
     * 退出整个app的标准写法
     */
    public void exitApp(){
        //先杀掉相关进程，最后在杀掉主进程
//        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningAppProcessInfo> processInfoS = manager.getRunningAppProcesses();
//        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : processInfoS) {
//            if (runningAppProcessInfo.pid != android.os.Process.myPid()) {
//                android.os.Process.killProcess(runningAppProcessInfo.pid);
//            }
//        }
        //android.os.Process.killProcess(android.os.Process.myPid());
        //正常退出程序，也就是结束当前正在运行的java虚拟机
        //System.exit(0);

        finish();
    }
}
