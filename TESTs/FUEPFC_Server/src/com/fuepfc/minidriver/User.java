package com.fuepfc.minidriver;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 18/03/19
 * Time: 12.42
 */
public class User implements Serializable {
    private String username;
    private String sessionKey;

    public User(String username, String sessionKey){
        this.username = username;
        this.sessionKey = sessionKey;
    }

    public String getUsername() {
        return username;
    }

    public String getSessionKey() {
        return sessionKey;
    }
}