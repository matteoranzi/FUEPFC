package tictactoe;

import scenes.lobby.LobbyController;
import tictactoe.messages.Message;
import tictactoe.messages.StartMatchMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static tictactoe.GameParameters.TCP_SERVER_PORT;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 14/03/19
 * Time: 23.13
 */
public class TicTacToeClientManager implements Runnable {
    LobbyController lobbyController;

    public TicTacToeClientManager(LobbyController lobbyController){
        this.lobbyController = lobbyController;
    }

    @Override
    public void run() {
        boolean failed = false;
        Socket socket = null;

        do {
            try {
                socket = new Socket("localhost", TCP_SERVER_PORT);
                System.out.println("connected to the server");

                failed = false;
            } catch (IOException e) {
                failed = true;
                System.out.println("Failed connecting to the server, trying again... (5seconds)");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        } while (failed);

        lobbyController.setLoadingText(LobbyController.WAITING_NEW_OPPONENT_MESSAGE);

        //waiting the match to start
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message message = (Message)ois.readObject();

            if(message instanceof StartMatchMessage){
                System.out.println("Starting the match");
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }
}