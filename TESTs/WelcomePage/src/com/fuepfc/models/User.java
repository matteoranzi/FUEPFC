package com.fuepfc.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 23/02/19
 * Time: 16.44
 */
public class User implements Serializable {
    public static final boolean VALID = true;

    private final UUID uuid;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private Timestamp registrationDate;
    private String sessionKey;

    public User(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean validate() {
        if (!firstName.equals("") && !lastName.equals("") && !username.equals("") && !email.equals("") && !password.equals("") && registrationDate != null) {
            return VALID;
        }

        return !VALID;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }



    @Override
    public String toString() {
        return "User{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", registrationDate=" + registrationDate +
                '}';
    }
}