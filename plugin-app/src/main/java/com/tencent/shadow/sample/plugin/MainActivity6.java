package com.tencent.shadow.sample.plugin;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tencent.shadow.sample.plugin.service.AppBasicService;

public class MainActivity6 extends AppCompatActivity implements View.OnClickListener {

    private Button loginBtn = null;
    private TextView label = null;

    public static void launch(Context context){
        Intent intent=new Intent(context,MainActivity6.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_main6);

        loginBtn = (Button) findViewById(R.id.login_btn_ok);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //label.setText("模拟报错Label");
                Intent myIntent = new Intent(getApplicationContext(), AppBasicService.class);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(myIntent);
                } else {
                    startService(myIntent);
                }
            }
        });
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
}

