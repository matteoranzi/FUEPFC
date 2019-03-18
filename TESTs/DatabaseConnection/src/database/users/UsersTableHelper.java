package database.users;

import com.mysql.jdbc.MysqlDataTruncation;
import database.Config;
import models.users.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 25/02/19
 * Time: 15.40
 */
public class UsersTableHelper {
    public static final boolean AUTHENTICATED = true;

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
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM " + FuepfcDbSchema.UsersTable.NAME);

            while (resultSet.next()) {
                User user = new User(UUID.fromString(resultSet.getString(FuepfcDbSchema.UsersTable.Cols.UUID)));
                user.setFirstName(resultSet.getString(FuepfcDbSchema.UsersTable.Cols.FIRST_NAME));
                user.setLastName(resultSet.getString(FuepfcDbSchema.UsersTable.Cols.LAST_NAME));
                user.setUsername(resultSet.getString(FuepfcDbSchema.UsersTable.Cols.USERNAME));
                user.setEmail(resultSet.getString(FuepfcDbSchema.UsersTable.Cols.EMAIL));
                user.setPassword(resultSet.getString(FuepfcDbSchema.UsersTable.Cols.CRYPTED_PASSWORD));
                user.setRegistrationDate(resultSet.getTimestamp(FuepfcDbSchema.UsersTable.Cols.REGISTRATION_DATE));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public Registration registerUser(User user) {
        if (user == null || user.validate() == !User.VALID) {
            return Registration.EMPTY_FIELDS;
        }

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO " + FuepfcDbSchema.UsersTable.NAME + "(" +
                            FuepfcDbSchema.UsersTable.Cols.UUID + "," +
                            FuepfcDbSchema.UsersTable.Cols.FIRST_NAME + "," +
                            FuepfcDbSchema.UsersTable.Cols.LAST_NAME + "," +
                            FuepfcDbSchema.UsersTable.Cols.USERNAME + "," +
                            FuepfcDbSchema.UsersTable.Cols.EMAIL + "," +
                            FuepfcDbSchema.UsersTable.Cols.CRYPTED_PASSWORD + "," +
                            FuepfcDbSchema.UsersTable.Cols.PASSWORD_SALT + "," +
                            FuepfcDbSchema.UsersTable.Cols.REGISTRATION_DATE + ")" +
                            " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            String salt = BCrypt.gensalt();

            preparedStatement.setString(1, user.getUuid().toString());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getUsername());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, BCrypt.hashpw(user.getPassword(), salt));
            preparedStatement.setString(7, salt);
            preparedStatement.setTimestamp(8, user.getRegistrationDate());

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

    public boolean authenticateUser(String username, String password) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT " +
                    FuepfcDbSchema.UsersTable.Cols.CRYPTED_PASSWORD + ", " +
                    FuepfcDbSchema.UsersTable.Cols.PASSWORD_SALT +
                    " FROM " + FuepfcDbSchema.UsersTable.NAME + " WHERE " + FuepfcDbSchema.UsersTable.Cols.USERNAME + " = ?");

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedHashedPassword = resultSet.getString(FuepfcDbSchema.UsersTable.Cols.CRYPTED_PASSWORD);
                String storedSalt = resultSet.getString(FuepfcDbSchema.UsersTable.Cols.PASSWORD_SALT);

                String hashedPassword = BCrypt.hashpw(password, storedSalt);

                if(storedHashedPassword.equals(hashedPassword)){
                    return AUTHENTICATED;
                }else{
                    return !AUTHENTICATED;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return !AUTHENTICATED;
    }

    public enum Registration {
        ERROR,
        SUCCESSFUL,
        DUPLICATED_ENTRY,
        EMPTY_FIELDS,
        STRING_TOO_LONG
    }
}