package com.caet.shadow.sample.host.lib;

public class UserInfoBean {

    private String id;
    private String name;
    private String depid;
    private String token;

    public UserInfoBean(String id, String name, String depid, String token) {
        this.id = id;
        this.name = name;
        this.depid = depid;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepid() {
        return depid;
    }

    public void setDepid(String depid) {
        this.depid = depid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
