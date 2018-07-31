package com.nj.zddemo.bean;


import com.haoge.easyandroid.easy.PreferenceSupport;

/**
 * Created by Administrator on 2018-07-28.
 */

public class LoginServerInfo extends PreferenceSupport {
    private String server;
    private String number;
    private String pass;
    private String port;
    private String suffix;
    private String ip;
    private int kind;

    // 默认就有，如果有其他构造方法，就必须添加
    //    public LoginServer() {
    //    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }
}
