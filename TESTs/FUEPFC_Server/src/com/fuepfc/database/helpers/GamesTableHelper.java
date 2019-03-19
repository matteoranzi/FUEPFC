package com.fuepfc.database.helpers;

import com.fuepfc.database.FuepfcDbSchema;
import com.fuepfc.models.Game;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

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

    public ArrayList<Game> getGames() {
        ArrayList<Game> games = new ArrayList<>();

        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM " + FuepfcDbSchema.GamesDataTable.NAME);

            while (resultSet.next()) {
                Game game = new Game(UUID.fromString(resultSet.getString(FuepfcDbSchema.GamesDataTable.Cols.VERSION_UUID)));

                game.setGameUUID(UUID.fromString(resultSet.getString(FuepfcDbSchema.GamesDataTable.Cols.GAME_UUID)));
                game.setName(resultSet.getString(FuepfcDbSchema.GamesDataTable.Cols.GAME_NAME));
                game.setVersion(resultSet.getString(FuepfcDbSchema.GamesDataTable.Cols.VERSION));

                games.add(game);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return games;
    }
}