package tictactoe;

import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 14/03/19
 * Time: 20.37
 */
public class MatchManager {
    Queue<Socket> connectedPlayers;

    public MatchManager(){
        connectedPlayers = new LinkedList<>();
    }

    public void addPlayer(Socket user){
        connectedPlayers.add(user);

        //There are enough player to start a match
        if(connectedPlayers.size() >= 2){
            System.out.println("Enough players to start a match");
            Match match = new Match(connectedPlayers.poll(), connectedPlayers.poll());
            match.start();
        }
    }
}