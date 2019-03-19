package com.fuepfc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 19/03/19
 * Time: 1.40
 */
public class DatabaseConnection {
    private Connection connection;

    private static DatabaseConnection ourInstance = new DatabaseConnection();

    public static DatabaseConnection getInstance() {
        return ourInstance;
    }

    private DatabaseConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(Config.URL, Config.USERNAME, Config.PASSWORD);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
