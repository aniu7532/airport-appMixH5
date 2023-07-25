package com.tencent.shadow.sample.plugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends Activity {

    private ImageView img_back;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_main2);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(v -> {
            finish();
        });
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("作业接收");

        TextView tv_canshu_info = findViewById(R.id.tv_canshu_info);
        Intent intent = getIntent();

        try {
            String canshu1 = intent.getStringExtra("canshu1");
            String canshu2 = intent.getStringExtra("canshu2");
            String canshu3 = intent.getStringExtra("canshu3");
            tv_canshu_info.setText("canshu1：".concat(canshu1)
                    .concat("\n").concat("canshu2：").concat(canshu2)
                    .concat("\n").concat("canshu3").concat(canshu3));
        }catch (Exception e){
            Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
