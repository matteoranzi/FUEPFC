package models.users;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 23/02/19
 * Time: 16.44
 */
public class User {
    public static final boolean VALID = true;

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private Timestamp registrationDate;

    public boolean validate() {
        if (!firstName.equals("") && !lastName.equals("") && !username.equals("") && !email.equals("") && !password.equals("") && registrationDate != null) {
            return VALID;
        }

        return !VALID;
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