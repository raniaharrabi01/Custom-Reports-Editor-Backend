package com.example.proxymit.Custom_Reports_Editor.service.ExternalDataBaseService;

public class ConnectionData {

    private  String base_name;
    private String host;
    private int port;
    private String username;
    private String password;

    public String getBase_name() {
        return base_name;
    }

    public void setBase_name(String name) {
        this.base_name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }


    public String getUsername() {
        return username;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public void setUsername(String username) {
        this.username = username;
    }
}
