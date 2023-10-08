package com.tencent.shadow.sample.plugin;

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

    private Button btn_send_gb_to_host;

    private ImageView img_back;

    private TextView tv_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_gb_to_host);

        img_back=findViewById(R.id.img_back);
        img_back.setOnClickListener(v -> finish());

        tv_title=findViewById(R.id.tv_title);
        tv_title.setText("向HOST发送广播");

        btn_send_gb_to_host = findViewById(R.id.btn_send_gb_to_host);
        btn_send_gb_to_host.setOnClickListener(v -> {

            Intent intent = new Intent();
            intent.setAction(C.BROADCAST_PLUGIN_HOST);
            Bundle bundle=new Bundle();
            bundle.putString("action","plugin向host发送信息");//发送广播的动机
            bundle.putString("pluginName","one-debug");//当前是那个插件
            intent.putExtra("bundle",bundle);
            sendBroadcast(intent);


            LogUtils.logD("插件向宿主发送广播");
        });
    }
}
