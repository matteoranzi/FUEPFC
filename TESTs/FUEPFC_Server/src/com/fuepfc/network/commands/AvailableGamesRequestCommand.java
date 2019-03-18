package com.fuepfc.network.commands;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 18/03/19
 * Time: 15.52
 */
public class AvailableGamesRequestCommand extends Command {
    private final String userSessionKey;

    public AvailableGamesRequestCommand(String userSessionKey){
        this.userSessionKey = userSessionKey;
    }

    public String getUserSessionKey() {
        return userSessionKey;
    }
}