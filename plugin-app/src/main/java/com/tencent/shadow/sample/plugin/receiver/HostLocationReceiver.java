package com.tencent.shadow.sample.plugin.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.shadow.sample.plugin.utils.LogUtils;

public class HostLocationReceiver  extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle= intent.getBundleExtra("bundle");
        double longitude=bundle.getDouble("longitude");
        double latitude=bundle.getDouble("latitude");

        LogUtils.logD("经纬度："+longitude+"|"+latitude);
    }
}
