package com.tencent.shadow.sample.plugin;

public interface C {

    String logTag="TAG";

    /**
     * 广播类  action
     */
    String BROADCAST_LOGOUT = "com.host.logout";
    String BROADCAST_SCAN = "com.host.scan";
    String BROADCAST_PLUGIN_HOST = "com.host.msg";
    String BROADCAST_HOST_SEND_LOCATION_PLUGIN = "com.host.send.location.plugin";//宿主给插件发送定位信息
    String BROADCAST_HOST_SEND_LOCATION_PLUGIN2 = "com.host.send.location.plugin2";//宿主给插件发送定位信息
}
