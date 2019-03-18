package tictactoe;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static tictactoe.GameParameters.TCP_SERVER_PORT;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 14/03/19
 * Time: 20.27
 */
public class ConnectionsManager implements Runnable{
    MatchManager matchManager;

    public ConnectionsManager(){
        matchManager = new MatchManager();
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(TCP_SERVER_PORT);
            System.out.println("Server is up");

            while (true){
                Socket newPlayerSocket = serverSocket.accept();

                ConnectionHandler connectionHandler = new ConnectionHandler(newPlayerSocket);
                connectionHandler.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        Thread t = new Thread(this);
//        t.setDaemon(true);
        t.start();
    }

    private class ConnectionHandler implements Runnable{
        private Socket connection;

        public ConnectionHandler(Socket connection){
            this.connection = connection;
        }

        @Override
        public void run() {
            System.out.println("New player connected");
            matchManager.addPlayer(connection);

        }

        public void start(){
            Thread t = new Thread(this);
            t.setDaemon(true);
            t.start();
        }
    }
}