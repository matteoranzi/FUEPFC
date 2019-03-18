package tictactoe;

import tictactoe.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static tictactoe.GameParameters.TABLE_SIZE;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 14/03/19
 * Time: 20.51
 */
public class Match implements Runnable {

    private static final boolean END_GAME = true;
    private static final int PLAYERS_COUNT = 2;
    private static final int PLAYER_1 = 0;
    private static final int PLAYER_2 = 1;
    private static final int MAX_MOVES = 9;

    private Socket[] playersConnection;

    private ObjectOutputStream[] oosPlayers;
    private ObjectInputStream[] oisPlayers;


    private int playerTurn;
    private CellStatus[][] tableStatus;



    public Match(Socket player1, Socket player2) {
        System.out.println("Setting up match");
        playersConnection = new Socket[PLAYERS_COUNT];

        playersConnection[PLAYER_1] = player1;
        playersConnection[PLAYER_2] = player2;

        setupMatch();

        //Open i/o streams
        try {
            System.out.println("Opening streams");
            oisPlayers = new ObjectInputStream[PLAYERS_COUNT];
            oosPlayers = new ObjectOutputStream[PLAYERS_COUNT];

            for(int i = 0; i < PLAYERS_COUNT; i++){
                System.out.println("OUTPUT");
                oosPlayers[i] = new ObjectOutputStream(playersConnection[i].getOutputStream());
                System.out.println("INPUT");
                oisPlayers[i] = new ObjectInputStream(playersConnection[i].getInputStream());
            }

            System.out.println("End opening streams");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done");
    }

    private void startMatch() {
        System.out.println("Staring the match");
        CellStatus winner = CellStatus.BLANK;

        try {
            oosPlayers[PLAYER_1].writeObject(new StartMatchMessage(StartMatchMessage.Player.PLAYER_1));
            oosPlayers[PLAYER_2].writeObject(new StartMatchMessage(StartMatchMessage.Player.PLAYER_2));

            for (int i = 0; i < MAX_MOVES; i++) {
                winner = checkWinning();

                MoveMessage moveMessage = null;
                boolean isValid;

                do{
                    oosPlayers[playerTurn].writeObject(new YourTurnMessage());

                    try {
                        moveMessage = (MoveMessage) oisPlayers[playerTurn].readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    isValid = validPlayerMove(moveMessage);

                    if(!isValid){
                        oosPlayers[i].writeObject(new InvalideMoveMessage());
                    }

                }while(!isValid);

                sendMatchStatus();

                playerTurn = 1 - playerTurn; //switch between player 1 [0] and player 2 [1]
            }

            winner = checkWinning();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMatchStatus(){
        for(int i = 0; i < PLAYERS_COUNT; i++){
            try {
                oosPlayers[i].writeObject(new MatchStatusMessage(tableStatus));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validPlayerMove(MoveMessage moveMessage){
        if(moveMessage == null){
            return false;
        }

        int i = moveMessage.getI();
        int j = moveMessage.getJ();

        if(tableStatus[i][j] == CellStatus.BLANK){
            if(playerTurn == PLAYER_1){
                tableStatus[i][j] = CellStatus.PLAYER_1;
            }else{
                tableStatus[i][j] = CellStatus.PLAYER_2;
            }
        }

        return false;
    }

    private void setupMatch() {
        playerTurn = PLAYER_1;
        tableStatus = new CellStatus[TABLE_SIZE][TABLE_SIZE];

        for (int i = 0; i < TABLE_SIZE; i++) {
            for (int j = 0; j < TABLE_SIZE; j++) {
                tableStatus[i][j] = CellStatus.BLANK;
            }
        }
    }

    private CellStatus checkWinning() {
        for (int i = 0; i < TABLE_SIZE; i++) {
            if (tableStatus[i][0] == tableStatus[i][1] && tableStatus[i][1] == tableStatus[i][2]) {
                return tableStatus[i][0];
            }
        }

        for (int i = 0; i < TABLE_SIZE; i++) {
            if (tableStatus[0][i] == tableStatus[1][i] && tableStatus[1][i] == tableStatus[2][i]) {
                return tableStatus[0][i];
            }
        }

        if (tableStatus[0][0] == tableStatus[1][1] && tableStatus[1][1] == tableStatus[2][2]) {
            return tableStatus[0][0];
        }

        if (tableStatus[2][0] == tableStatus[1][1] && tableStatus[1][1] == tableStatus[0][2]) {
            return tableStatus[2][0];
        }

        return CellStatus.BLANK;
    }

    private void debugTable() {
        System.out.println("\nStatus:");
        for (CellStatus[] rowStatus : tableStatus) {
            for (CellStatus cellStatus : rowStatus) {
                System.out.print("|" + cellStatus + "|");
            }
            System.out.println();
        }
    }

    @Override
    public void run() {
        startMatch();
    }

    public void start() {
        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }
}