package com.tencent.shadow.sample.plugin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tencent.shadow.sample.plugin.utils.LogUtils;

/**
 * 插件向宿主中发送广播
 */
public class SendBroadcastToHostActivity extends BaseActivity {

    public static void launch(Context context){
        Intent intent=new Intent(context,SendBroadcastToHostActivity.class);
        context.startActivity(intent);
    }

    private TextView tv_send_gb_to_host;

    private ImageView img_back;

    private TextView tv_title;

    private TextView tv_send_gb_to_host_notification;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_gb_to_host);

        img_back=findViewById(R.id.img_back);
        img_back.setOnClickListener(v -> finish());

        tv_title=findViewById(R.id.tv_title);
        tv_title.setText("向HOST发送广播");

        /**
         * 向host发送普通信息
         */
        tv_send_gb_to_host = findViewById(R.id.tv_send_gb_to_host);
        tv_send_gb_to_host.setOnClickListener(v -> {

            Intent intent = new Intent();
            intent.setAction(C.BROADCAST_PLUGIN_HOST);
            Bundle bundle=new Bundle();
            bundle.putString("action","ordinary_msg");//发送广播的动机
            bundle.putString("pluginName","one-debug");//当前是那个插件
            intent.putExtra("bundle",bundle);
            sendBroadcast(intent);


            LogUtils.logD("插件向宿主发送广播");
        });

        /**
         * 插件向host通过广播发送构建通知栏的指令
         */
        tv_send_gb_to_host_notification=findViewById(R.id.tv_send_gb_to_host_notification);
        tv_send_gb_to_host_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(C.BROADCAST_PLUGIN_HOST);
                Bundle bundle=new Bundle();
                bundle.putString("action","PLUG_CREATE_NOTIFICATION");//发送广播的动机
                //跟menu.json中配置的href  参考/plugin-one/com.tencent.shadow.sample.plugin.LoadAssetsWebActivity
                bundle.putString("href","plugin-one/com.tencent.shadow.sample.plugin.SendBroadcastToHostActivity");
                bundle.putString("target","3");//一般固定写3
                bundle.putString("notificationContentTitle","未读消息");//通知栏标题
                bundle.putString("notificationContentText","你的微信朋友发来了未读信息");//通知栏内容
                intent.putExtra("bundle",bundle);
                sendBroadcast(intent);
            }
        });
    }
}
