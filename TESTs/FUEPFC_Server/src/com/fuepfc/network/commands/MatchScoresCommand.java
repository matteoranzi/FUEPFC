package com.fuepfc.network.commands;

import com.fuepfc.minidriver.User;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 18/03/19
 * Time: 12.53
 */
public class MatchScoresCommand extends Command {
    private User user;
    private int score;

    public MatchScoresCommand(User user, int score){
        this.user = user;
        this.score = score;
    }

    public User getUser() {
        return user;
    }

    public int getScore() {
        return score;
    }
}