package com.tencent.shadow.sample.plugin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tencent.shadow.sample.plugin.utils.LogUtils;

public class MainActivity1 extends AppCompatActivity implements View.OnClickListener {

    private Button btnStartActivity;
    private ImageView img_back;

    private TextView tv_submit;
    private TextView tv_title;

    private EditText edt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        initView();
        LogUtils.logD("plugin_1_MainActivity_onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.logD("plugin_1_MainActivity_onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.logD("plugin_1_MainActivity_onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.logD("plugin_1_MainActivity_onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.logD("plugin_1_MainActivity_onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.logD("plugin_1_MainActivity_onDestroy");
    }

    private void initView() {

        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        btnStartActivity = findViewById(R.id.btnStartActivity);
        btnStartActivity.setOnClickListener(this);

        tv_submit = findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);

        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("违章上报");

        edt1 = findViewById(R.id.edt1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btnStartActivity:
                startActivity(new Intent(MainActivity1.this, MainActivity2.class));
                break;
            case R.id.tv_submit:
                Toast.makeText(this, edt1.getText().toString().concat("上报成功"), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
    }
}