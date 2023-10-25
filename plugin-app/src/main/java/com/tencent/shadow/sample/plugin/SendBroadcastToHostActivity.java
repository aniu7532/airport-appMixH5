package com.tencent.shadow.sample.plugin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tencent.shadow.sample.plugin.utils.LogUtils;

/**
 * 插件向宿主中发送广播
 */
public class SendBroadcastToHostActivity extends AppCompatActivity {

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
                bundle.putString("action","notification");//发送广播的动机
                bundle.putString("canshu1","canshu1");//当前是那个插件
                bundle.putString("canshu2","canshu2");//当前是那个插件
                bundle.putString("canshu3","canshu3");//当前是那个插件
                bundle.putString("canshu4","canshu4");//当前是那个插件
                intent.putExtra("bundle",bundle);
                sendBroadcast(intent);
            }
        });
    }
}
