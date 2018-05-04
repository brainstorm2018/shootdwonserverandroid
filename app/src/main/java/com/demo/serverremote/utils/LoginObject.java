package com.demo.serverremote.utils;

/**
 * Created by User on 05/02/2018.
 */

public class LoginObject {
    private String username,password;

    public LoginObject() {
        this.username = LoginCredencials.username;
        this.password = LoginCredencials.password;
    }

    public LoginObject(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
