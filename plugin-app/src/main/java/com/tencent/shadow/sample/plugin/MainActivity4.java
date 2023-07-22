package com.tencent.shadow.sample.plugin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.caet.shadow.sample.host.lib.HostUiLayerProvider;
import com.caet.shadow.sample.host.lib.UserInfoBean;

public class MainActivity4 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LinearLayout linearLayout = new LinearLayout(this);

        HostUiLayerProvider hostUiLayerProvider = HostUiLayerProvider.getInstance();
        View hostUiLayer = hostUiLayerProvider.buildHostUiLayer();

        UserInfoBean userInfoBean = hostUiLayerProvider.getUserInfo();
        Toast.makeText(this, userInfoBean.getDepid(), Toast.LENGTH_SHORT).show();

        linearLayout.addView(hostUiLayer);

        setContentView(linearLayout);

        //setContentView(R.layout.acticity_main4);
    }
}
