package com.caet.shadow.sample.host.lib;

public class UserInfoBean {

    private String name;
    private String icon;
    private String token;

    public UserInfoBean(String name, String icon, String token) {
        this.name = name;
        this.icon = icon;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
