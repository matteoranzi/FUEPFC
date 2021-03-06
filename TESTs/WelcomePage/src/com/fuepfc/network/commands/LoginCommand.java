package com.fuepfc.network.commands;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 11/03/19
 * Time: 21.23
 */
public class LoginCommand extends Command{
    private String username;
    private String password;

    public LoginCommand(){}

    public LoginCommand(String username, String password){
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