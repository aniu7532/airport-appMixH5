package com.tencent.shadow.sample.plugin.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tencent.shadow.sample.plugin.LogUtils;
import com.tencent.shadow.sample.plugin.R;
import com.tencent.shadow.sample.plugin.entity.PersonnelInfo;

import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AppBasicService extends Service {

    public static final String TAG = "TAG";

    public JWebSocketClient client;

    // 发送消息线程
    private MessageThread messageThread = null;

    @Override
    public IBinder onBind(Intent intent) {
        return new SrvBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtils.logD("插件1_Service_onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.logD("插件1_Service_onStartCommand");

        mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//开启心跳检测
        if (client == null) {
            Log.e(TAG, "onResume");
            initWebSocket();
        } else if (!client.isOpen()) {
            reconnectWs();//进入页面发现断开开启重连
        }

//        initSocketClient(getWebsocketMessage());
//
//        // send();
////
//        try {
//            messageThread = new MessageThread();
//            // messageThread.isRunning = true;
//            messageThread.start();
//        } catch (Exception e) {
//            Log.e(TAG, e.toString());
//        }

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 向服务端发送消息
     */
    private void send() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    sendMsg(getWebsocketMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Looper.loop();
            }
        }) {
            public void run() {
                super.run();
            }
        }.start();
    }

    /**
     * 初始化websocket连接
     */
    private void initSocketClient(String data) {
        LogUtils.logD("初始化websocket连接");

        URI uri = URI.create(WebSocketConfig.ws + "123");
        client = new JWebSocketClient(uri) {
            @Override
            public void onMessage(String message) {
                LogUtils.logD("收到的消息：" + message);
                sendMessage(message);
            }

            @Override
            public void onOpen(ServerHandshake handshakedata) {
                super.onOpen(handshakedata);
                LogUtils.logD("websocket连接成功");
                sendMsg(data);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                super.onClose(code, reason, remote);
                if (code != 1000) {
                    reconnectWs();//意外断开马上重连
                }
                Log.i(TAG, "websocket断开连接：·code:" + code + "·reason:" + reason + "·remote:" + remote);
            }
        };
        connect();
    }

    /**
     * 初始化websocket
     */
    public void initWebSocket() {
        Log.e(TAG, "websocket的地址是：" + WebSocketConfig.ws + "123");
        URI uri = URI.create(WebSocketConfig.ws + "123");
        //TODO 创建websocket
        client = new JWebSocketClient(uri) {
            @Override
            public void onMessage(String message) {
                super.onMessage(message);
                if (!message.equals("Heartbeat")) {
                    Log.i(TAG, "websocket收到消息：" + message);
                }
            }

            @Override
            public void onOpen(ServerHandshake handshakedata) {
                super.onOpen(handshakedata);
                Log.i(TAG, "websocket连接成功");
            }

            @Override
            public void onError(Exception ex) {
                super.onError(ex);
                Log.i(TAG, "websocket连接错误：" + ex);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                super.onClose(code, reason, remote);
                if (code != 1000) {
                    reconnectWs();//意外断开马上重连
                }
                Log.i(TAG, "websocket断开连接：·code:" + code + "·reason:" + reason + "·remote:" + remote);
            }
        };
        //TODO 设置超时时间
        client.setConnectionLostTimeout(100 * 1000);
        //TODO 连接websocket
        new Thread() {
            @Override
            public void run() {
                try {
                    //connectBlocking多出一个等待操作，会先连接再发送，否则未连接发送会报错
                    client.connectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 发送通知消息
     */
    private void sendMessage(String content) {
        Intent intent;
        NotificationChannel notificationChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel("important", "Important", NotificationManager.IMPORTANCE_LOW);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Notification notification = new NotificationCompat.Builder(this, "important")
                .setContentTitle("巡检定位信息")
                .setAutoCancel(false)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        startForeground(16, notification);
    }

    private String getWebsocketMessage() {
        String jsonStr = "";

        List<PersonnelInfo> listPerson = new ArrayList<PersonnelInfo>();
        PersonnelInfo info = new PersonnelInfo();
        info.wgs84_lat = "23.01";
        info.wgs84_lon = "116.98";
        info.person_name = "admin";
        info.data_source = "道面巡检";
        info.task_title = "道面巡检";
        info.dept_id = "1001";
        info.dept_name = "场务";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String now = sdf.format(new Date());
        info.record_time = now;

        listPerson.add(info);

        jsonStr = toJson(listPerson);

        return jsonStr;
    }

    /**
     * 发送消息
     *
     * @param msg
     */
    public void sendMsg(String msg) {
        if (client == null) {
            return;
        }
        if (client.isOpen()) {
            LogUtils.logD("发送的消息：" + new Date().toString() + msg);
            client.send(msg);
        } else {
            if (client.getReadyState().equals(ReadyState.NOT_YET_CONNECTED)) {
                try {
                    client.connect();
                } catch (IllegalStateException e) {
                }
            } else if (client.getReadyState().equals(ReadyState.CLOSING) || client.getReadyState().equals(ReadyState.CLOSED)) {
                client.reconnect();
            }
        }
    }

    public boolean isMessageRunning = true;

    class MessageThread extends Thread {

        public void run() {
            while (isMessageRunning) {
                try {
                    // Looper.prepare();


                    LogUtils.logD("开始获取定位信息");
//                    if (locSer == null) {
//                        locSer = new LocationServer();
//                        locSer.addListener(getApplicationContext(), LocationManager.GPS_PROVIDER
//                                , new LocationServer.ILocationListener() {
//                                    @Override
//                                    public void onSuccess(Location location) {
//                                        AppUser.getUser().lon = location.getLongitude();
//                                        AppUser.getUser().lat = location.getLatitude();
//                                    }
//                                }
//                        );
//                    }
//
//                    Location l = locSer.getLocation();
//                    if (l != null) {
//                        AppUser.getUser().lon = l.getLongitude();
//                        AppUser.getUser().lat = l.getLatitude();
//                        Log.i("PDALog", "成功获取定位信息！");
//                        // Toast.makeText(context, "Located : Lat: " + location.getLatitude()+ " Lng: " + location.getLongitude(), Toast.LENGTH_SHORT).show();
//                    }

                    // Looper.loop();

                    LogUtils.logD("发送前：" + new Date().toString());

                    send();

                    // 10秒更新
                    Thread.sleep(1000 * 20);

                    LogUtils.logD("发送后：" + new Date().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 连接websocket
     */
    private void connect() {
        LogUtils.logD("连接websocket");

        new Thread() {
            @Override
            public void run() {
                try {
                    //connectBlocking多出一个等待操作，会先连接再发送，否则未连接发送会报错
                    client.connectBlocking();
                    int i = 1 + 1;
                    int j = i + 11;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 开启重连
     */
    private void reconnectWs() {
        mHandler.removeCallbacks(heartBeatRunnable);
        new Thread() {
            @Override
            public void run() {
                try {
                    Log.e("开启重连", "");
                    client.reconnectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    /**
     * 断开连接
     */
    private void closeConnect() {
        try {
            if (null != client) {
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client = null;
        }
    }

    @Override
    public void onDestroy() {
        closeConnect();

        if (messageThread != null) {
            isMessageRunning = false;
        }
    }

    private static final long HEART_BEAT_RATE = 10 * 1000;
    private Handler mHandler = new Handler();
    private Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            while (isMessageRunning) {
                try {
                    LogUtils.logD("发送前：" + new Date().toString());

                    LogUtils.logD("webSocket检测！");

                    if (client != null) {
                        if (client.isClosed()) {
                            Log.e("心跳包检测websocket连接状态1", client.isOpen() + "/" + WebSocketConfig.ws + "123");
                            reconnectWs();//心跳机制发现断开开启重连
                        } else {
                            Log.e("心跳包检测websocket连接状态2", client.isOpen() + "/" + WebSocketConfig.ws + "123");
                            sendMsg("Heartbeat");
                        }
                    } else {
                        Log.e("心跳包检测websocket连接状态重新连接", "");
                        //如果client已为空，重新初始化连接
                        client = null;
                        initWebSocket();
                    }

                    // 10秒更新
                    Thread.sleep(1000 * 20);

                    LogUtils.logD("发送后：" + new Date().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public class SrvBinder extends Binder {
        /**
         * 获取当前Service的实例
         *
         * @return
         */
        public AppBasicService getService() {
            return AppBasicService.this;
        }
    }

    public static Gson Gson() {
        return new GsonBuilder().serializeNulls().create();
    }

    public static String toJson(Object data) {
        String json = Gson().toJson(data);
        return json;
    }

}

