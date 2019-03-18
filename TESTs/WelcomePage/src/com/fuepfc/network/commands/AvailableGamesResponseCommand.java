package com.fuepfc.network.commands;

import com.fuepfc.models.Game;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 18/03/19
 * Time: 16.46
 */
public class AvailableGamesResponseCommand extends Command {
    private ArrayList<Game> games;

    public AvailableGamesResponseCommand(){
        this.games = new ArrayList<>();
    }

    public ArrayList<Game> getGames() {
        return this.games;
    }
}