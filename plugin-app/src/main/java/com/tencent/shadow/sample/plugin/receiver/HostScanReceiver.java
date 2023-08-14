package com.tencent.shadow.sample.plugin.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.tencent.shadow.sample.plugin.MainActivity5;

/**
 * 二维码信息格式为5位系统缩略+5位操作类型+3位分类+10位唯一标识+8位校验码，
 * 如FOD系统信息查询行李拖车，拖车唯一标识为0000000018，二维码信息为00FOD000010010000000018WcgZiw1k。
 */
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


            //这里去启动一个activity
//            Intent in= new Intent(context, MainActivity5.class);
//            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
//            context.startActivity(in);


        }catch (Exception e){
            Log.d("TAG","----------------------------plugin1广播事件：扫码事件："+e.getMessage());
        }


    }
}
