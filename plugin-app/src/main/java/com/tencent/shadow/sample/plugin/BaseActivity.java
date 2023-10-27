package com.tencent.shadow.sample.plugin;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {


    public Context context;
    public Activity activity;
    private View view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        activity=this;
        view = LayoutInflater.from(this).inflate(R.layout.layout_toast, new LinearLayout(this));
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

    public void showToast(String message) {
        Toast toast = new Toast(this);
        TextView tv = view.findViewById(R.id.tv_title);
        tv.setText(message);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

}
