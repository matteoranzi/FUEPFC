package com.fuepfc.database.users;

import com.fuepfc.network.commands.RegistrationResponseCommand.Registration;
import com.fuepfc.utils.AppParameters;
import com.mysql.jdbc.MysqlDataTruncation;
import com.fuepfc.database.Config;
import com.fuepfc.models.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 25/02/19
 * Time: 15.40
 */
public class UsersTableHelper {

    private Connection connection;

    public UsersTableHelper() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(Config.URL, Config.USERNAME, Config.PASSWORD);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                User user = new User(UUID.fromString(resultSet.getString(FuepfcDbSchema.UsersDataTable.Cols.UUID)));
                user.setFirstName(resultSet.getString(FuepfcDbSchema.UsersDataTable.Cols.FIRST_NAME));
                user.setLastName(resultSet.getString(FuepfcDbSchema.UsersDataTable.Cols.LAST_NAME));
                user.setUsername(resultSet.getString(FuepfcDbSchema.UsersDataTable.Cols.USERNAME));
                user.setEmail(resultSet.getString(FuepfcDbSchema.UsersDataTable.Cols.EMAIL));
                user.setPassword(resultSet.getString(FuepfcDbSchema.UsersDataTable.Cols.CRYPTED_PASSWORD));
                user.setRegistrationDate(resultSet.getTimestamp(FuepfcDbSchema.UsersDataTable.Cols.REGISTRATION_DATE));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public Registration registerUser(String name, String lastName, String username, String email, String password) {
        if (name.equals("") || lastName.equals("") || username.equals("") || email.equals("") || password.equals("")) {
            return Registration.EMPTY_FIELDS;
        }

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO " + FuepfcDbSchema.UsersDataTable.NAME + "(" +
                            FuepfcDbSchema.UsersDataTable.Cols.UUID + "," +
                            FuepfcDbSchema.UsersDataTable.Cols.FIRST_NAME + "," +
                            FuepfcDbSchema.UsersDataTable.Cols.LAST_NAME + "," +
                            FuepfcDbSchema.UsersDataTable.Cols.USERNAME + "," +
                            FuepfcDbSchema.UsersDataTable.Cols.EMAIL + "," +
                            FuepfcDbSchema.UsersDataTable.Cols.CRYPTED_PASSWORD + "," +
                            FuepfcDbSchema.UsersDataTable.Cols.PASSWORD_SALT + "," +
                            FuepfcDbSchema.UsersDataTable.Cols.REGISTRATION_DATE + ")" +
                            " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            String salt = BCrypt.gensalt();
            Timestamp registrationDate = new Timestamp(Calendar.getInstance().getTime().getTime());

            preparedStatement.setString(1, UUID.randomUUID().toString());
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, username);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, BCrypt.hashpw(password, salt));
            preparedStatement.setString(7, salt);
            preparedStatement.setTimestamp(8, registrationDate);

            if (preparedStatement.executeUpdate() != 0) {
                return Registration.SUCCESSFUL;
            }


        } catch (SQLIntegrityConstraintViolationException e) {
            return Registration.DUPLICATED_ENTRY;

        } catch (MysqlDataTruncation e) {
            return Registration.STRING_TOO_LONG;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Registration.ERROR;
    }

    /**
     * @param username
     * @param password
     * @return all user data, including session key
     */
    public User authenticateUser(String username, String password) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT " +
                    FuepfcDbSchema.UsersDataTable.Cols.CRYPTED_PASSWORD + ", " +
                    FuepfcDbSchema.UsersDataTable.Cols.PASSWORD_SALT +
                    " FROM " + FuepfcDbSchema.UsersDataTable.NAME + " WHERE " + FuepfcDbSchema.UsersDataTable.Cols.USERNAME + " = ?");

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedHashedPassword = resultSet.getString(FuepfcDbSchema.UsersDataTable.Cols.CRYPTED_PASSWORD);
                String storedSalt = resultSet.getString(FuepfcDbSchema.UsersDataTable.Cols.PASSWORD_SALT);

                String hashedPassword = BCrypt.hashpw(password, storedSalt);

                if (storedHashedPassword.equals(hashedPassword)) {
                    if (createSessionKey(username)) {
                        return fillUserData(username);
                    }
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean createSessionKey(String username) {
        try {
            UUID uuid = UUID.randomUUID();
            Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime() + AppParameters.SESSION_KEY_EXPIRATION_DURATION);

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + FuepfcDbSchema.UsersDataTable.NAME +
                    " SET " + FuepfcDbSchema.UsersDataTable.Cols.SESSION_KEY + " = ?, " +
                    FuepfcDbSchema.UsersDataTable.Cols.SESSION_EXPIRATION_DATE + " = ? WHERE " +
                    FuepfcDbSchema.UsersDataTable.Cols.USERNAME + " = ?");

            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setTimestamp(2, timestamp);
            preparedStatement.setString(3, username);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }

        return true;
    }

    private User fillUserData(String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " +
                    FuepfcDbSchema.UsersDataTable.NAME + " WHERE " + FuepfcDbSchema.UsersDataTable.Cols.USERNAME + " = ?");

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User(UUID.fromString(resultSet.getString(FuepfcDbSchema.UsersDataTable.Cols.UUID)));
                user.setFirstName(resultSet.getString(FuepfcDbSchema.UsersDataTable.Cols.FIRST_NAME));
                user.setLastName(resultSet.getString(FuepfcDbSchema.UsersDataTable.Cols.LAST_NAME));
                user.setUsername(resultSet.getString(FuepfcDbSchema.UsersDataTable.Cols.USERNAME));
                user.setEmail(resultSet.getString(FuepfcDbSchema.UsersDataTable.Cols.EMAIL));
                user.setSessionKey(resultSet.getString(FuepfcDbSchema.UsersDataTable.Cols.SESSION_KEY));
                user.setRegistrationDate(resultSet.getTimestamp(FuepfcDbSchema.UsersDataTable.Cols.REGISTRATION_DATE));

                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}