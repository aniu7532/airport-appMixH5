package com.tencent.shadow.sample.plugin.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;


public class HostScanReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Handler handler=new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        //得到扫码的内容
        try {
            Bundle bundle=intent.getBundleExtra("bundle");
            String scanResult= bundle.getString("scanResult");
            Log.d("TAG","----------------------------plugin1广播事件：扫码事件："+scanResult);
        }catch (Exception e){
            Log.d("TAG","----------------------------plugin1广播事件：扫码事件："+e.getMessage());
        }


    }
}
