package com.tencent.shadow.sample.plugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.caet.shadow.sample.host.lib.HostUiLayerProvider;
import com.caet.shadow.sample.host.lib.UserInfoBean;

public class MainActivity4 extends Activity {

    public static void launch(Context context){
        Intent intent=new Intent(context,MainActivity4.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LinearLayout linearLayout = new LinearLayout(this);

        HostUiLayerProvider hostUiLayerProvider = HostUiLayerProvider.getInstance();
        View hostUiLayer = hostUiLayerProvider.buildHostUiLayer();



        linearLayout.addView(hostUiLayer);

        setContentView(linearLayout);

        //setContentView(R.layout.acticity_main4);
    }
}
