package com.fuepfc.database.games_data;

import com.fuepfc.database.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 19/03/19
 * Time: 1.36
 */
public class GamesTableHelper {
    private Connection connection;

    public GamesTableHelper(Connection connection) {
        this.connection = connection;
    }
}