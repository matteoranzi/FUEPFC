package com.fuepfc.network.commands;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 12/03/19
 * Time: 0.22
 */
public class RegistrationCommand extends Command {
    private String name;
    private String lastName;
    private String username;
    private String email;
    private String password;

    public RegistrationCommand(){}

    public RegistrationCommand(String name, String lastName, String username, String email, String password){
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}