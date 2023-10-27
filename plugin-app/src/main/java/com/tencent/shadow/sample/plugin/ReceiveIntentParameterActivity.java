package com.tencent.shadow.sample.plugin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 接受host 传递过来的参数
 */
public class ReceiveIntentParameterActivity extends BaseActivity {

    private ImageView img_back;
    private TextView tv_title;

    public static void launch(Context context){
        Intent intent=new Intent(context, ReceiveIntentParameterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_receive_intent_paramater);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(v -> {
            finish();
        });
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("作业接收");

        TextView tv_canshu_info = findViewById(R.id.tv_canshu_info);
        Intent intent = getIntent();

        try {
            String id = intent.getStringExtra("id");
            String name = intent.getStringExtra("name");
            String depid = intent.getStringExtra("depid");
            String token = intent.getStringExtra("token");
            tv_canshu_info.setText("id：".concat(id)
                    .concat("\n").concat("name：").concat(name)
                    .concat("\n").concat("depid：").concat(depid)
                    .concat("\n").concat("token").concat(token));
        }catch (Exception e){
            Toast.makeText(ReceiveIntentParameterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
