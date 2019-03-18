package com.fuepfc.network.commands;

import com.fuepfc.minidriver.User;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 18/03/19
 * Time: 12.51
 */
public class MatchWinnerCommand extends Command {
    private User user;

    public MatchWinnerCommand(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}