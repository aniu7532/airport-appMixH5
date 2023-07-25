package com.tencent.shadow.sample.plugin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.caet.shadow.sample.host.lib.HostUserInfo;
import com.caet.shadow.sample.host.lib.UserInfoBean;

public class MainActivity3 extends Activity {

    private TextView tv_user_info;
    private TextView tv_get_user_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_main3);

        UserInfoBean userInfoBean = HostUserInfo.getInstance().getUserInfo();

        tv_user_info = findViewById(R.id.tv_user_info);
        tv_get_user_info = findViewById(R.id.tv_get_user_info);
        tv_get_user_info.setOnClickListener(v -> {
            tv_user_info.setText("id：".concat(userInfoBean.getId())
                    .concat("\n").concat("name：").concat(userInfoBean.getName())
                    .concat("\n").concat("depid：").concat(userInfoBean.getDepid())
                    .concat("\n").concat("token：").concat(userInfoBean.getToken()));
        });

    }
}
