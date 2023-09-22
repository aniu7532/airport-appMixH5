package com.tencent.shadow.sample.plugin;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import com.tencent.shadow.sample.plugin.utils.LogUtils;


public class BindService extends Service {

    public BindService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.logD("插件1_Service_onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.logD("插件1_Service_onStartCommand");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.logD("插件1_Service_onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.logD("插件1_Service_onBind");
        return new IMyAidlInterface.Stub() {
            @Override
            public String basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
                return Integer.toString(anInt) + aLong + aBoolean + aFloat + aDouble + aString;
            }
        };
    }
}
