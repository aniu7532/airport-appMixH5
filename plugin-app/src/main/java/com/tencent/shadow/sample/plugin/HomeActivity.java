package com.tencent.shadow.sample.plugin;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;

public class HomeActivity extends PluginOneBaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tv_title;
    private TextView tvAcceptLocationActivity;
    private TextView tvCreateNotificationActivity;
    private TextView tvSendBroadcastToHostActivity;
    private TextView tvTestActivityOrientationActivity;
    private TextView tvPermissionsTestActivity;//权限相关的东西
    private TextView tvMainActivity1;
    private TextView tvMainActivity2;
    private TextView tvMainActivity3;
    private TextView tvMainActivity4;
    private TextView tvMainActivity5;
    private TextView tvMainActivity6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("plugin-one");

        tvAcceptLocationActivity = findViewById(R.id.tvAcceptLocationActivity);
        tvAcceptLocationActivity.setOnClickListener(this);
        tvCreateNotificationActivity = findViewById(R.id.tvCreateNotificationActivity);
        tvCreateNotificationActivity.setOnClickListener(this);

        tvSendBroadcastToHostActivity = findViewById(R.id.tvSendBroadcastToHostActivity);
        tvSendBroadcastToHostActivity.setOnClickListener(this);
        tvTestActivityOrientationActivity = findViewById(R.id.tvTestActivityOrientationActivity);
        tvTestActivityOrientationActivity.setOnClickListener(this);

        tvPermissionsTestActivity=findViewById(R.id.tvPermissionsTestActivity);
        tvPermissionsTestActivity.setOnClickListener(this);

        tvMainActivity1 = findViewById(R.id.tvMainActivity1);
        tvMainActivity1.setOnClickListener(this);
        tvMainActivity2 = findViewById(R.id.tvMainActivity2);
        tvMainActivity2.setOnClickListener(this);
        tvMainActivity3 = findViewById(R.id.tvMainActivity3);
        tvMainActivity3.setOnClickListener(this);
        tvMainActivity4 = findViewById(R.id.tvMainActivity4);
        tvMainActivity4.setOnClickListener(this);
        tvMainActivity5 = findViewById(R.id.tvMainActivity5);
        tvMainActivity5.setOnClickListener(this);
        tvMainActivity6 = findViewById(R.id.tvMainActivity6);
        tvMainActivity6.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                //exitApp();
                finish();
                break;
            case R.id.tvAcceptLocationActivity:
                AcceptLocationActivity.launch(this);
                break;
            case R.id.tvCreateNotificationActivity:
                CreateNotificationActivity.launch(this);
                break;
            case R.id.tvSendBroadcastToHostActivity:
                SendBroadcastToHostActivity.launch(this);
                break;
            case R.id.tvTestActivityOrientationActivity:
                TestActivityOrientationActivity.launch(this);
                break;
            case R.id.tvPermissionsTestActivity:
                PermissionsTestActivity.launch(this);
                break;
            case R.id.tvMainActivity1:
                MainActivity1.launch(this);
                break;
            case R.id.tvMainActivity2:
                MainActivity2.launch(this);
                break;
            case R.id.tvMainActivity3:
                MainActivity3.launch(this);
                break;
            case R.id.tvMainActivity4:
                MainActivity4.launch(this);
                break;
            case R.id.tvMainActivity5:
                MainActivity5.launch(this);
                break;
            case R.id.tvMainActivity6:
                MainActivity6.launch(this);
                break;
        }
    }
}
