package com.fuepfc.minidriver.client;

import com.fuepfc.minidriver.User;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 18/03/19
 * Time: 12.32
 */
public class Minidriver4Client {
    private User user;
    private InetAddress gameServerAddress;
    private int gameServerPort;

    public Minidriver4Client(String[] parameters) {
        user = new User(parameters[0], parameters[1]);

        try {
            gameServerAddress = InetAddress.getByName(parameters[2]);
            gameServerPort = Integer.valueOf(parameters[3]);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public User getUser(){
        return this.user;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public String getSessionKey() {
        return user.getSessionKey();
    }

    public InetAddress getGameServerAddress() {
        return gameServerAddress;
    }

    public int getGameServerPort() {
        return gameServerPort;
    }
}