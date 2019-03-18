package com.fuepfc.minidriver.server;

import com.fuepfc.minidriver.User;
import com.fuepfc.network.commands.MatchScoresCommand;
import com.fuepfc.network.commands.MatchWinnerCommand;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 18/03/19
 * Time: 12.32
 */
public class Minidriver4Server {
    private InetAddress fuepfcServerAddress;
    private int fuefpcServerPort;
    private int gameServerPort;

    public Minidriver4Server(String[] parameters){
        try {
            this.fuepfcServerAddress = InetAddress.getByName(parameters[0]);
            this.fuefpcServerPort = Integer.valueOf(parameters[1]);

            this.gameServerPort = Integer.valueOf(parameters[2]);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public int getGameServerPort() {
        return this.gameServerPort;
    }

    public void setWinner(User user){
        try {
            Socket socket = new Socket(fuepfcServerAddress, fuefpcServerPort);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            oos.writeObject(new MatchWinnerCommand(user));
            oos.flush();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUserScore(User user, int score){
        try {
            Socket socket = new Socket(fuepfcServerAddress, fuefpcServerPort);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            oos.writeObject(new MatchScoresCommand(user, score));
            oos.flush();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}