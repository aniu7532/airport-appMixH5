package com.tencent.shadow.sample.plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;


public class MyReceiver extends BroadcastReceiver {

    private String tag = "plugin.MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "插件接收到清理缓存广播,我是:" + tag, Toast.LENGTH_SHORT).show();
            }
        });
        Log.d("TAG", "----------------------------插件1接受到清理缓存广播,我是:" + tag);
    }
}
