package com.tencent.shadow.sample.plugin;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.caet.shadow.sample.host.lib.HostAddPluginViewContainer;
import com.caet.shadow.sample.host.lib.HostAddPluginViewContainerHolder;

public class HostAddPluginViewService  extends IntentService {


    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    public HostAddPluginViewService() {
        super("HostAddPluginViewService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int id = intent.getIntExtra("id", 0);
        HostAddPluginViewContainer viewContainer = HostAddPluginViewContainerHolder.instances.remove(id);

        uiHandler.post(() -> {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_host_add_plugin_view, null, false);
            TextView tv=view.findViewById(R.id.tv);
            ImageView img=view.findViewById(R.id.img);
            tv.setText("我是插件的view");
            img.setImageResource(R.drawable.vip6);
            viewContainer.addView(view);
        });
    }
}
