package com.example.libraryapi.library_management_api.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.util.Properties;
@Component
public class DBCredentials {
    @Value("${db.user}")
    private String username;
    @Value("${db.password}")
    private String password;
    @Value("${db.url}")
    private String url;
    @Value("${db.internal_logon}")
    private String internal_logon;
    Properties props;
    public DBCredentials(){
        props=new Properties();
    }
    @PostConstruct
    public void init() {
        props.setProperty("user", username);
        props.setProperty("password", password);
        props.setProperty("internal_logon", internal_logon);
    }
    public Properties getProperties(){
        return props;
    }
    public String getUrl(){
        return url;
    }
}
