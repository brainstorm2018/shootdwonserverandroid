package com.demo.serverremote.utils;

/**
 * Created by User on 05/02/2018.
 */

public class Constants {

    public static String URL = "10.7.2.45";
    public static String LOGIN_URL = "http://" + URL + ":8080/auth";
    public static String LOCK_URL = "http://" + URL + ":8080/admin/lock";
    public static String SLEEP_URL = "http://" + URL + ":8080/admin/sleep";
    public static String DOWN_URL = "http://" + URL + ":8080/admin/shutdown";
    public static String SCREEN_URL = "http://" + URL + ":8080/admin/screenshot";

    public static void refactor(String newIp) {
        URL = newIp;
        LOGIN_URL = "http://" + URL + ":8080/auth";
        LOCK_URL = "http://" + URL + ":8080/admin/lock";
        SLEEP_URL = "http://" + URL + ":8080/admin/sleep";
        DOWN_URL = "http://" + URL + ":8080/admin/shutdown";
        SCREEN_URL = "http://" + URL + ":8080/admin/screenshot";
    }
}
