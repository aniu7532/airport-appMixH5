package com.tencent.shadow.sample.plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * 插件通过广播接收来自宿主的信息
 */
public class AcceptLocationActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tv_title;

    TextView tv1;
    TextView tv2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_location);

        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("接受到来自host的定位信息");

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);

        //动态注册广播接受插件
        IntentFilter intentFilter = new IntentFilter(C.BROADCAST_HOST_SEND_LOCATION_PLUGIN);
        registerReceiver(broadcastReceiver, intentFilter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            default:
                break;
        }
    }


    /**
     * 接收来自插件的广播信息
     */
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            String broadcastAction = intent.getAction();

            if (C.BROADCAST_HOST_SEND_LOCATION_PLUGIN.equals(broadcastAction)) {

                Bundle bundle = intent.getBundleExtra("bundle");
                double longitude = bundle.getDouble("longitude");
                double latitude = bundle.getDouble("latitude");

                if (tv1 != null) {
                    tv1.setText("经度：" + longitude);
                }

                if (tv2 != null) {
                    tv2.setText("纬度：" + latitude);
                }
            }
        }
    };


}
