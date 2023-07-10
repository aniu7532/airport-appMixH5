package com.tencent.shadow.sample.plugin;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(C.logTag,"----------------插件1_Service_onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(C.logTag,"----------------插件1_Service_onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new IMyAidlInterface.Stub() {
            @Override
            public String basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
                return Integer.toString(anInt) + aLong + aBoolean + aFloat + aDouble + aString;
            }
        };
    }
}
