package com.tencent.shadow.sample.plugin.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.caet.shadow.sample.host.lib.HostAddPluginViewContainer;
import com.caet.shadow.sample.host.lib.HostAddPluginViewContainerHolder;
import com.tencent.shadow.sample.plugin.R;
import com.tencent.shadow.sample.plugin.utils.LogUtils;

public class HostAddPluginViewService  extends IntentService {


    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    public HostAddPluginViewService() {
        super("HostAddPluginViewService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        int id = intent.getIntExtra("id", 0);

        LogUtils.logD("id：".concat(String.valueOf(id)));

        HostAddPluginViewContainer viewContainer = HostAddPluginViewContainerHolder.instances.remove(id);

        uiHandler.post(() -> {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_host_add_plugin_view, null, false);

            TextView tv=view.findViewById(R.id.tv);
            //ImageView img=view.findViewById(R.id.img);
            tv.setText("我是插件的view");
            tv.setTextColor(Color.parseColor("#FFFFFF"));
            //img.setImageResource(R.drawable.vip6);

            if(viewContainer==null){
                LogUtils.logD("viewContainer对象为空");
            }else{
                LogUtils.logD("viewContainer正常的加载视图");
                viewContainer.addView(view);
            }

        });
    }
}
